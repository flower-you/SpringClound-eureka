package com.abupdate.cacheclient.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 〈〉
 *
 * @author wangjuhua
 * @create 2019/8/14
 * @since V2.0
 */
@Mapper
public interface CronMapper {
    @Select("select cron from cron limit 1")
    String getCron();

    @Update(("update cron set cron = #{cron} where cron_id = 1"))
    void updateCron(String cron);
}
