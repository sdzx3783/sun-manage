package com.sun.zookeeper.curator;

import java.util.concurrent.CountDownLatch;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.ZKPaths;

import lombok.extern.slf4j.Slf4j;

/**
 * @Title: CuratorClient.java
 * @Description: zookeeper客户端
 * @author: sunzhao
 * @date: 2018年8月16日 下午2:57:49
 */
@Slf4j
public class CuratorClient {

	private CuratorFramework _client;
	private CountDownLatch countDownLatch = new CountDownLatch(1);
	/**
	 * 客户端的监听
	 */
	private ConnectionStateListener clientListener = new ConnectionStateListener() {

		public void stateChanged(CuratorFramework client, ConnectionState newState) {
			if (newState == ConnectionState.CONNECTED) {
				log.debug("connected established");
				countDownLatch.countDown();
			} else if (newState == ConnectionState.LOST) {
				log.debug("connection lost,waiting for reconection");
				try {
					log.debug("reConnect---");
					reConnect();
					log.debug("connected---");
				} catch (Exception e) {
					log.debug("re-connected failed");
				}
			}else {
				log.debug("客户端其他监听状态：{}",newState);
			}

		}
	};
	/**
	 * 子节点的监听
	 */
	private PathChildrenCacheListener plis = new PathChildrenCacheListener() {

		public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
			switch (event.getType()) {
				case CHILD_ADDED: {
					System.out.println("Node added: " + ZKPaths.getNodeFromPath(event.getData().getPath()));
					break;
				}
	
				case CHILD_UPDATED: {
					System.out.println("Node changed: " + ZKPaths.getNodeFromPath(event.getData().getPath()));
					break;
				}
	
				case CHILD_REMOVED: {
					System.out.println("Node removed: " + ZKPaths.getNodeFromPath(event.getData().getPath()));
					break;
				}
				default:
					System.out.println("Node 未知: " + ZKPaths.getNodeFromPath(event.getData().getPath()));
					break;
			}

		}
	};

	/**
	 * 客户端重新注册
	 */
	public void reConnect() {
		disConnectServer();
		connectServer();
	}

	
	public void connectServer() {
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
		_client = CuratorFrameworkFactory.newClient("192.168.10.107:2181", retryPolicy);
		// 客户端注册监听，进行连接配置
		_client.getConnectionStateListenable().addListener(clientListener);
		_client.start();
		// 连接成功后，才进行下一步的操作
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		log.debug("----------zookeeper连接成功--------");
		try {
			watcherPath("/zk_lock1");
		} catch (Exception e) {
			log.error("zk_lock监听失败，{}",e);
		}
	}

	public void disConnectServer() {
		if (_client != null) {
			_client.close();
		}
		log.debug("----------zookeeper 断开连接成功--------");
	}

	
	private void watcherPath(String path) throws Exception {
		//子节点的监听
		PathChildrenCache cache = new PathChildrenCache(_client, path, true);
		cache.start();
		//注册监听
		cache.getListenable().addListener(plis);

	}


	public CuratorFramework get_client() {
		return _client;
	}

}
