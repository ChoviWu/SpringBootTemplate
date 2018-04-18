package org.choviwu.example.mapper;

import org.apache.ibatis.annotations.Param;
import org.choviwu.example.common.model.BusMessageLog;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface BusMessageLogMapper extends Mapper<BusMessageLog> {

    //获取当天未消费的队列
    List<BusMessageLog> getListByToday(Map map);

    BusMessageLog getLogByRoutKeyAndMessage(@Param("routKey")String routKey, @Param("body")String body);
    BusMessageLog getLogByCrc32Code(String crc32Code);

}