package com.sun.manage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sun.zookeeper.curator.ReadWriteLock;

/**  
 * @Title:  ZkReadWriteLockTest.java   
 * @Description:    zk读写锁测试
 * @author: sunzhao  
 * @date:   2018年8月29日 下午5:23:03   
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ZkReadWriteLockTest {
	@Autowired
	ReadWriteLock readWriteLock;
	
	private static final String LOCK_KEY="stock-key";
	private int data=50;
	
	ThreadPoolExecutor executor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),Runtime.getRuntime().availableProcessors()*2,60,
			TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>());
	@Test
	public void test() throws InterruptedException {
		while(true) {
			executor.execute(()->{
				increaseData();
			});
			executor.execute(()->{
				decreaseData();
			});
			Thread.sleep(1000);
		}
	}
	
	public void increaseData() {
		try {
			readWriteLock.getWriteLock(LOCK_KEY);
			data++;
			System.out.println("加操作："+data);
			Thread.sleep(300);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			readWriteLock.unLock();
		}
	}
	public void decreaseData() {
		try {
			readWriteLock.getWriteLock(LOCK_KEY);
			if(data>0) {
				data--;
				System.out.println("减操作："+data);
			}
			Thread.sleep(300);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			readWriteLock.unLock();
		}
	}
}
