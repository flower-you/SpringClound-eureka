package com.abupdate.cacheclient.mapper;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface QuartzJobMapper {
    @Select("select cron_expression from quartz_job where job_name = #{jobName} limit 1")
    String getCron(String jobName);

    @Select("select job_status from quartz_job where job_name = #{jobName} limit 1")
    int getState(String jobName);

    @Update(("update quartz_job set cron_expression = #{cron} where job_name = #{jobName}"))
    void updateQuartzJobCron(String cron,String jobName);

    @Update(("update quartz_job set job_status = #{state} where job_name = #{jobName}"))
    void updateQuartzJobState(int state,String jobName);

}