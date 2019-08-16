package com.abupdate.schedule.task;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * 〈〉
 *
 * @author wangjuhua
 * @create 2019/8/16
 * @since V2.0
 */
public class TaskTest {
    public static void main(String[] args) throws SchedulerException {
        // define the job and tie it to our MyJob class
        JobDetail job = JobBuilder.newJob(RemoveCacheTask.class)
                .withIdentity("job1", "group1")
                .build();

        // Trigger the job to run now, and then repeat every 5 seconds
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(5)
                        .repeatForever())
                .build();

        // Grab the Scheduler instance from the Factory
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        // and start it off
        scheduler.start();
        // Tell quartz to schedule the job using our trigger
        scheduler.scheduleJob(job, trigger);
    }

}
