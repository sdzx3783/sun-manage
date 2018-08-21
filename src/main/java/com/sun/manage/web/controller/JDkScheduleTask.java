package com.sun.manage.web.controller;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**  
 * @Title:  JDkScheduleTask.java   
 * @Description:    TODO
 * @author: sunzhao  
 * @date:   2018年8月21日 下午5:39:15   
 */
public class JDkScheduleTask {
	public static void main(String[] args) {
		ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(100);
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("123");
            }
        }, 2,2, TimeUnit.SECONDS);
	}
}
