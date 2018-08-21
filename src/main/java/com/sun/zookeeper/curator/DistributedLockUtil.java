package com.sun.zookeeper.curator;

import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.recipes.locks.InterProcessMutex;

/**  
 * @Title:  DistributedLockUtil.java   
 * @Description:    TODO
 * @author: sunzhao  
 * @date:   2018年8月16日 下午5:56:00   
 */
public class DistributedLockUtil {
	private CuratorClient curatorClient;
	
	public InterProcessMutex  getLock(String key) throws Exception {
		InterProcessMutex lock = new InterProcessMutex(curatorClient.get_client(), "key");
		lock.acquire();
		return lock;
	}
	
	public void releaseLock() {
		
	}
	
	public InterProcessMutex  getLockWithTimeout(String key) throws Exception {
		InterProcessMutex lock = new InterProcessMutex(curatorClient.get_client(), "key");
		lock.acquire(6, TimeUnit.SECONDS);
		return lock;
	}
}
