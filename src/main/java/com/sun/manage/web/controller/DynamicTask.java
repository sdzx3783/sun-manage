package com.sun.manage.web.controller;

import java.util.Date;
import java.util.concurrent.ScheduledFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**  
 * @Title:  DynamicTask.java   
 * @Description:    任务调度controller（可参考eat-platform-scheduler）
 * @author: sunzhao  
 * @date:   2018年8月6日 下午1:26:58   
 */
@RestController
@EnableScheduling
@RequestMapping("/task")
public class DynamicTask {

    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;    
    
    private ScheduledFuture<?> future;
    
    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler(){
    	ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
    	scheduler.setPoolSize(5);
		scheduler.initialize();
        return scheduler;
    }
    
    
    /**
     * 1、定一个方法：startTask-启动定时任务;
     * 2、定义一个方法：stopTask - 停止定时任务;
     * 3、定义一个方法：changeCron  - 修改定时任务时间：
     */
    
    //1、定一个方法：startTask-启动定时任务;(真实项目，这里应该使用一个缓存来管理这些future，每次启动先判断future是否已经存在，如果已存在先调用future.cancel(true)。然后再创建，并放入缓存！)
    @RequestMapping("/startTask")
    public String startTask(){
        //秒，分钟，小时，日期，月份，星期，年（可选）.
        //每5秒执行一次定时任务.(具体任务间隔时间还需要根据任务的具体执行时间来定)
        future = threadPoolTaskScheduler.schedule(new MyRunnable("1111"), new CronTrigger("0/1 * * * * *"));
        
        future = threadPoolTaskScheduler.schedule(new MyRunnable("2222"), new CronTrigger("0/1 * * * * *"));
        System.out.println("start Task");
        return "startTask";
    }
    
    //2、定义一个方法：stopTask - 停止定时任务;
    @RequestMapping("/stopTask")
    public String stopTask(){
        if(future != null){
            future.cancel(true);
        }
        System.out.println("stop task");
        return "stopTask";
    }
    
    // 3、定义一个方法：changeCron  - 修改定时任务时间：
    @RequestMapping("/changeCron")
    public String changeCron(){
        //(1) 先停止定时器； （2）在启动定时器.
        stopTask();
        //每10秒执行一次定时任务.
        future = threadPoolTaskScheduler.schedule(new MyRunnable("1111"), new CronTrigger("0/10 * * * * *"));
        System.out.println("change Cron");
        return "changeCron";
    }
    
    
    private class MyRunnable implements Runnable{
    	private String name;
        /**
		 * @param name
		 */
		public MyRunnable(String name) {
			super();
			this.name = name;
		}
		@Override
        public void run() {
        	System.out.println("任务开始：当前线程："+Thread.currentThread().getName());
        	try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
            System.out.println("MyRunable.run,"+"name："+name+new Date()+"当前线程："+Thread.currentThread().getName());
            System.out.println("任务结束：当前线程："+Thread.currentThread().getName());
        }
        
    }
}
