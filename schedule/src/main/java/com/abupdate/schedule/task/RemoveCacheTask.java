package com.abupdate.schedule.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 〈〉
 *
 * @author wangjuhua
 * @create 2019/8/16
 * @since V2.0
 */
public class RemoveCacheTask implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("清除book缓存");
    }
}
