package com.abupdate.cacheclient.timetask;

import com.abupdate.cacheclient.config.Constants;
import com.abupdate.cacheclient.dao.IRedisDao;
import com.abupdate.cacheclient.mapper.QuartzJobMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ScheduledFuture;

/**
 * 利用线程池实现任务调度
 * Task任务调度器可以实现任务的调度和删除
 * 原理:
 * 实现一个类：ThreadPoolTaskScheduler线程池任务调度器，能够开启线程池进行任务调度
 * ThreadPoolTaskScheduler.schedule（）方法会创建一个定时计划ScheduleFuture,
 * 在这个方法中添加两个参数一个是Runable:线程接口类，和CronTrigger(定时任务触发器)
 * 在ScheduleFuture中有一个cancel可以停止定时任务
 * @author Admin
 *
 * Scheduled Task是一种轻量级的任务定时调度器，相比于Quartz,减少了很多的配置信息，但是Scheduled Task
 * 不适用于服务器集群，引文在服务器集群下会出现任务被多次调度执行的情况，因为集群的节点之间是不会共享任务信息的
 * 每个节点的定时任务都会定时执行
 *
 *
 * @author wangjuhua
 * @create 2019/8/14
 * @since V2.0
 */
@RestController
@EnableScheduling
public class ClearBookCacheTask {
     private final String jobName = "removeBookCache";
     private Logger logger = LoggerFactory.getLogger(ClearBookCacheTask.class);
     @Autowired
     private ThreadPoolTaskScheduler threadPoolTaskScheduler;
     @Autowired
     private IRedisDao redisDao;

     @Autowired
     private QuartzJobMapper quartzJobMapper;

     private ScheduledFuture<?> future;

    private String cronStr;

     @Bean
     public ThreadPoolTaskScheduler trPoolTaskScheduler(){
         return new ThreadPoolTaskScheduler();
     }
     /**
      * 1，定义一个方法实现定时任务的启动
      * 2，定义一个方法实现用于终止定时任务
      * 3，修改定时任务时间：changeCron
      */
     /**
      * 启动定时器
      * @return
      */
     @RequestMapping("startTest")
     public String startTest(){
         /**
          * task:定时任务要执行的方法
          * trigger:定时任务执行的时间
          */
         String result = "";
         cronStr = quartzJobMapper.getCron(jobName);
         int state = quartzJobMapper.getState(jobName);
         if (state == Constants.JOB_STARTED){
             result = "任务已经启动！无需再次启动";
         }
         else{
             future=threadPoolTaskScheduler.schedule(new ClearBookCache(),new CronTrigger(cronStr) );
             quartzJobMapper.updateQuartzJobState(Constants.JOB_STARTED,jobName);
             result = "任务启动成功！";
         }
        return result;
     }

     /**
      * 停止定时任务
      * @return
      */
     @RequestMapping("endTask")
     public String endTask(){
         String result = "";
         if (quartzJobMapper.getState(jobName) == Constants.JOB_STARTED){
             if(future!=null){
                 future.cancel(true);
                 quartzJobMapper.updateQuartzJobState(Constants.JOB_END,jobName);
                 result = "任务成功终止";
             }
         }
         else{
             result = "任务未开始，无需停止！";
         }
         logger.info("< ======= endTask ==============>");
         return result;
     }

     /**
      * 改变调度的时间
      * 步骤：
      * 1,先停止定时器
      * 2,在启动定时器
      */
     @RequestMapping(value = "changeCron",method = RequestMethod.POST)
     public String changeCron(String cronExpression){
         String result = "";
         if (quartzJobMapper.getState(jobName) == Constants.JOB_STARTED){
             result = "任务执行中，请先停止任务!";
         }
         else {
             logger.info("======传入的cron 表达式 ====>{}",cronExpression);
             //定义新的执行时间
             future=threadPoolTaskScheduler.schedule(new ClearBookCache(),new CronTrigger(cronExpression) );

             //更新数据库中的cron
             quartzJobMapper.updateQuartzJobCron(cronExpression,jobName);
             //启动定时器
             startTest();
             result = "cron 已成功更改为"+ cronExpression;
         }
         return result;
     }

     /**
      * 定期清空 redis 中缓存的book
      * @author wangjuhua
      *
      */
     public class ClearBookCache implements Runnable{
         @Override
         public void run() {
             System.out.println("定时任务要执行的方法"+new Date());
             try {
                 redisDao.removePattern("book*",10);
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }
     }

}
