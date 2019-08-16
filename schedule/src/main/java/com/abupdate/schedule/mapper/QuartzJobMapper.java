package com.abupdate.schedule.mapper;

import com.abupdate.schedule.bean.QuartzJob;
import java.util.List;

public interface QuartzJobMapper {
    int deleteByPrimaryKey(Long id);

    int insert(QuartzJob record);

    QuartzJob selectByPrimaryKey(Long id);

    List<QuartzJob> selectAll();

    int updateByPrimaryKey(QuartzJob record);
}