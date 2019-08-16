package com.abupdate.cacheclient.timetask;

import com.abupdate.cacheclient.mapper.CronMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * 〈〉
 *
 * @author wangjuhua
 * @create 2019/8/14
 * @since V2.0
 */
//@Configuration
//@EnableScheduling
public class ScheduleConnfig implements SchedulingConfigurer {

    private Logger logger = LoggerFactory.getLogger(SchedulingConfigurer.class);

    @Autowired
    private CronMapper cronMapper;

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {

       Runnable task = new Runnable(){
           @Override
           public void run() {
               System.out.println("开始执行任务.........");
           }
       };

        Trigger trigger = new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                // 从数据库获取执行周期
                String cron = cronMapper.getCron();
                // 合法性校验.
                if (StringUtils.isEmpty(cron)) {
                  logger.info("从数据库获取任务执行周期失败！");
                }
                CronTrigger cronTrigger = new CronTrigger(cron);
                return cronTrigger.nextExecutionTime(triggerContext);
            }
        };
       scheduledTaskRegistrar.addTriggerTask(task,trigger);
    }
}
