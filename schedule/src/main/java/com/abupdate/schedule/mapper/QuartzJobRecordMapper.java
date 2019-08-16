package com.abupdate.schedule.mapper;

import com.abupdate.schedule.bean.QuartzJobRecord;
import java.util.List;

public interface QuartzJobRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(QuartzJobRecord record);

    QuartzJobRecord selectByPrimaryKey(Long id);

    List<QuartzJobRecord> selectAll();

    int updateByPrimaryKey(QuartzJobRecord record);
}