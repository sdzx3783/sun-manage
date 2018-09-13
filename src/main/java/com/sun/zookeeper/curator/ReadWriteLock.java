package com.sun.zookeeper.curator;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs.Ids;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @Title: ReadWriteLock.java
 * @Description: zookeeper实现读写锁(待改进：顺序节点并不是每次累加1，原因不明。需要考虑顺序节点的节点数溢出问题)
 * @author: sunzhao
 * @date: 2018年8月29日 上午11:05:25
 */
@Slf4j
@Component
public class ReadWriteLock {
	@Autowired
	private CuratorClient curatorClient;
	private static final String LOCKPATH = "/lock1";
	private static final String READKEYPRE = "r-";
	private static final String WRITEKEYPRE = "w-";

	private static ThreadLocal<String> lockPath = new ThreadLocal<>();

	/**
	 * 获取共享锁-读锁（阻塞等待)
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public boolean getReadLock(String key) throws Exception {
		return getReadLock(key, 0);
	}

	/**
	 * 获取共享锁-写锁（阻塞等待)
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public boolean getWriteLock(String key) throws Exception {
		return getWriteLock(key, 0);
	}

	/**
	 * 获取共享锁-读锁
	 * 
	 * @param key
	 * @param timeout
	 * @return
	 * @throws Exception
	 */
	public boolean getReadLock(String key, int timeout) throws Exception {
		CuratorFramework client = curatorClient.get_client();

		String parentPath = LOCKPATH + "/" + key;
		String currentPath = client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
				.withACL(Ids.OPEN_ACL_UNSAFE).forPath(parentPath + "/" + READKEYPRE + key + "-", "r".getBytes());
		log.debug("尝试获取写锁开始,key:{}",currentPath);
		lockPath.set(currentPath);
		long begin = System.currentTimeMillis();
		boolean lock = false;

		do {
			List<String> lockChildrens = client.getChildren().forPath(parentPath);
			log.debug("获取parentPath:{}的所有子节点:lockChildrens:{}]",parentPath,lockChildrens);
			boolean r = canGetReadLock(lockChildrens, currentPath, key);
			if (r) {
				log.debug("获取读锁成功，key:{},currentPath:{}", key, currentPath);
				lock = true;
				break;
			} else {
				log.debug("获取读锁失败，等待重试，key:{},currentPath:{}", key, currentPath);
				Thread.sleep(300);
			}
		} while (timeout > 0 && (System.currentTimeMillis() - begin) < timeout * 1000);
		if (timeout > 0 && lock == false) {
			log.error("获取读锁失败(超时)，key:{},currentPath:{}", key, currentPath);
		}
		return lock;
	}

	/**
	 * 获取共享锁-写锁
	 * 
	 * @param key
	 * @param timeout
	 * @return
	 * @throws Exception
	 */
	public boolean getWriteLock(String key, int timeout) throws Exception {
		CuratorFramework client = curatorClient.get_client();

		String parentPath = LOCKPATH + "/" + key;
		String currentPath = client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
				.withACL(Ids.OPEN_ACL_UNSAFE).forPath(parentPath + "/" + WRITEKEYPRE + key + "-", "w".getBytes());
		lockPath.set(currentPath);
		log.debug("尝试获取写锁开始,key:{}",currentPath);
		long begin = System.currentTimeMillis();
		boolean lock = false;

		do {
			List<String> lockChildrens = client.getChildren().forPath(parentPath);
			log.debug("获取parentPath:{}的所有子节点:lockChildrens:{}]",parentPath,lockChildrens);
			boolean r = canGetWriteLock(lockChildrens, currentPath, key);
			if (r) {
				log.debug("获取写锁成功，key:{},currentPath:{}", key, currentPath);
				lock = true;
				break;
			} else {
				log.debug("获取写锁失败，等待重试，key:{},currentPath:{}", key, currentPath);
				Thread.sleep(300);
			}
		} while (timeout <= 0 || (System.currentTimeMillis() - begin) < timeout * 1000);
		if (timeout > 0 && lock == false) {
			log.error("获取写锁失败(超时)，key:{},currentPath:{}", key, currentPath);
		}
		return lock;
	}

	/**
	 * @param lockChildrens
	 * @param currentPath
	 * @param key
	 * @return
	 */
	private boolean canGetWriteLock(List<String> lockChildrens, String currentPath, String key) {
		if (lockChildrens == null || lockChildrens.size() <= 0) {
			return true;
		}
		Optional<String> optional = lockChildrens.stream()
				.sorted(Comparator.comparing(s -> StringUtils.substringAfter(s, WRITEKEYPRE))).findFirst();
		if (optional.isPresent() && currentPath.equals(LOCKPATH + "/" + key + "/" + optional.get())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @param lockChildrens
	 * @param currentPath
	 * @param key
	 * @return
	 */
	private boolean canGetReadLock(List<String> lockChildrens, String currentPath, String key) {
		if (lockChildrens == null || lockChildrens.size() <= 0) {
			return true;
		}
		List<String> sortList = lockChildrens.stream()
				.sorted(Comparator.comparing(s -> StringUtils.substringAfter(s, READKEYPRE)))
				.collect(Collectors.toList());
		boolean isExistWrite = false;
		for (String s : sortList) {
			if ((LOCKPATH + "/" + key + "/" + s).equals(currentPath)) {
				break;
			}
			if (s.startsWith(READKEYPRE)) {
				isExistWrite = true;
				break;
			}
		}
		if (isExistWrite) {
			return false;
		} else {
			return true;
		}
	}

	public void unLock() {
		String currentPath = null;
		try {
			currentPath = lockPath.get();
			if (StringUtils.isNotEmpty(currentPath)) {
				CuratorFramework client = curatorClient.get_client();
				if (client.checkExists().forPath(currentPath) != null) {
					log.debug("释放锁开始:currentPath:{}", currentPath);
					client.delete().forPath(currentPath);
					log.debug("释放锁结束:currentPath:{}", currentPath);
					
				}
			}
		} catch (Exception e) {
			log.error("释放锁失败:currentPath:{},error:{}", currentPath, e);
		}
	}

	/**
	 * 创建节点
	 * 
	 * @param client
	 *            客户端
	 * @param path
	 *            路径
	 * @param createMode
	 *            节点类型
	 * @param data
	 *            节点数据
	 * @return 是否创建成功
	 *//*
		 * public static boolean createNode(CuratorFramework client, String path,
		 * CreateMode createMode, String data) { boolean isSucc = false; try {
		 * CreateMode nodeType = getNodeType(client, path); if (nodeType == null) {
		 * client.create().withMode(createMode).forPath(path, data.getBytes()); isSucc =
		 * true; } else if (createMode != nodeType) { isSucc = false;
		 * log.error("已存在该节点，节点类型不匹配. expect:{},but actual:{}", createMode, nodeType);
		 * throw new RuntimeException("已存在该节点，节点类型不匹配. expect:" + createMode +
		 * "but actual:" + nodeType); } else { isSucc = true; } } catch (Exception e) {
		 * log.info("创建节点失败，path：{}，data:{},error:{}", path, data, e); CreateMode
		 * nodeType = getNodeType(client, path); if (nodeType == null) { isSucc = false;
		 * } else if (createMode != nodeType) { isSucc = false; throw new
		 * RuntimeException("已存在该节点，节点类型不匹配. expect:" + createMode + "but actual:" +
		 * nodeType); } isSucc = true; }
		 * 
		 * return isSucc; }
		 */

	/**
	 * 判断节点是否是持久化节点
	 * 
	 * @param client
	 *            客户端
	 * @param path
	 *            路径
	 * @return null-节点不存在 | CreateMode.PERSISTENT-是持久化 | CreateMode.EPHEMERAL-临时节点
	 *//*
		 * public static CreateMode getNodeType(CuratorFramework client, String path) {
		 * try { Stat stat = client.checkExists().forPath(path);
		 * 
		 * if (stat == null) { return null; }
		 * 
		 * if (stat.getEphemeralOwner() > 0) { return CreateMode.EPHEMERAL; }
		 * 
		 * return CreateMode.PERSISTENT; } catch (Exception e) { e.printStackTrace();
		 * 
		 * return null; } }
		 */

}
