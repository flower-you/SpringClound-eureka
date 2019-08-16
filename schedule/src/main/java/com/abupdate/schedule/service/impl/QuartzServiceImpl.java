package com.abupdate.schedule.service.impl;

import com.abupdate.schedule.service.IQuartzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

/**
 * 〈〉
 *
 * @author wangjuhua
 * @create 2019/8/15
 * @since V2.0
 */
@Service
public class QuartzServiceImpl implements IQuartzService {
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

}
