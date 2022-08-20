package com.telegram.coinbot.model.mapper;

import com.telegram.coinbot.model.dto.AlarmInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CoinBotMapper {
    List<AlarmInfo> selectAllAlarmInfo();
}
