package com.sun.manage;

import java.util.concurrent.atomic.AtomicInteger;

/**  
 * @Title:  IdGenerator.java   
 * @Description:    TODO
 * @author: sunzhao  
 * @date:   2018年8月28日 下午3:16:09   
 */
public class IdGenerator {
	private static AtomicInteger count=new AtomicInteger(0);
	public static String getId(int strSize) {
		while(true) {
			int current = count.get();
			int next=current>=Integer.MAX_VALUE?0:current+1;
			if(count.compareAndSet(current, next)) {
				return String.format("%0"+strSize+"d", next);
			}
		}
		
	}
}
