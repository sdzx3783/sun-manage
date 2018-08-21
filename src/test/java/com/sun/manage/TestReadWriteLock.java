package com.sun.manage;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import lombok.extern.slf4j.Slf4j;


/**  
 * @Title:  TestReadWriteLock.java   
 * @Description:    TODO
 * @author: sunzhao  
 * @date:   2018年8月21日 下午2:20:09   
 */
/*==============总结==================
	 * ReentrantReadWriteLock会使用两把锁来解决问题，一个读锁，一个写锁
	线程进入读锁的前提条件：
	    没有其他线程的写锁，
	    没有写请求或者有写请求，但调用线程和持有锁的线程是同一个(*)
	
	线程进入写锁的前提条件：
	    没有其他线程的读锁
	    没有其他线程的写锁
	 (b).WriteLock可以降级为ReadLock，顺序是：先获得WriteLock再获得ReadLock，然后释放WriteLock，这时候线程将保持Readlock的持有。反过来ReadLock想要升级为WriteLock则不可能(*)
     (c).ReadLock可以被多个线程持有并且在作用时排斥任何的WriteLock，而WriteLock则是完全的互斥。这一特性最为重要，因为对于高读取频率而相对较低写入的数据结构，使用此类锁同步机制则可以提高并发量。
*/
@Slf4j
public class TestReadWriteLock {
	private static ReadWriteLock readWriteLock;
	private static Map<String,Integer> data=new HashMap<>();
	private static AtomicInteger count=new AtomicInteger();
	private static Random random=new Random();
	static {
		data.put("a", 0);
		data.put("b", -1);
	}
	
	public static void incr(String key) {
		log.debug("incr....start "+Thread.currentThread().getName());
		acquireWriteLock();
		log.debug("incr....getLock成功 "+Thread.currentThread().getName());
		try {
			/*if(random.nextInt(5)==2) {
				log.debug("[incr很不幸....被阻塞了] "+Thread.currentThread().getName());
				Thread.sleep(10000);
			}*/
			Integer integer = data.get(key);
			if(integer!=null) {
				data.put(key, integer+1);
			}else {
				data.put(key, 1);
			}
			count.getAndIncrement();
		}catch (Exception e) {
			log.error("incry异常：{}",e);
		}finally {
			releaseWriteLock();
		}
		
		log.debug("incr....end "+Thread.currentThread().getName());
	}
	public static Integer get(String key) {
		acquireReadLock();
		Integer integer=null;
		try {
			integer = data.get(key);
			if(random.nextInt(3)==2 && Thread.currentThread().getName().contains("thread-1")) {
				log.debug("------------[[get很不幸....被阻塞了]]---------- "+Thread.currentThread().getName());
				Thread.sleep(10000);
			}
			log.debug("【线程{}】get方法返回----->   a:{},count:{}",Thread.currentThread().getName(),integer,count.get());
		}catch (Exception e) {
			log.error("get异常：{}",e);
		}finally {
			releaseReadLock();
		}
		return integer;
	}
	protected static void acquireReadLock() {
	    getReadWriteLock().readLock().lock();
	  }

	protected static void releaseReadLock() {
	    getReadWriteLock().readLock().unlock();
	  }

	protected static void acquireWriteLock() {
		getReadWriteLock().writeLock().lock();
	}

	protected static void releaseWriteLock() {
 		getReadWriteLock().writeLock().unlock();
 	}
	
	private static ReadWriteLock getReadWriteLock() {
		if(readWriteLock==null) {
			synchronized (TestReadWriteLock.class) {
				if(readWriteLock==null) {
					readWriteLock=new ReentrantReadWriteLock();
				}
			}
		}
		return readWriteLock;
	}
	
	public static void main(String[] args) {
//		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
		int corePoolSize = Runtime.getRuntime().availableProcessors(); //核心线程数据
		int maxPoolSize = corePoolSize * 2; //最多线程数
		long keepAliveTime = 1; //空闲线程回收时间
		ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize,maxPoolSize,keepAliveTime,
		TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>());
		
		Runnable task=()->{
			TestReadWriteLock.get("a");
		};
		for(int i=0;i<100000;i++) {
			executor.submit(task);
			executor.submit(()->{TestReadWriteLock.incr("a");});
		}
//		testGet();
	}
	
	public static void testGet() {
		new Thread() {
			@Override
			public void run() {
				while(true) {
					log.debug("【线程{}】 get()->{}",Thread.currentThread().getName(),TestReadWriteLock.get("a"));
				}
			}}.start();
		new Thread() {
			@Override
			public void run() {
				while(true) {
					log.debug("【线程{}】 get()->{}",Thread.currentThread().getName(),TestReadWriteLock.get("a"));
				}
			}}.start();
	}
}
