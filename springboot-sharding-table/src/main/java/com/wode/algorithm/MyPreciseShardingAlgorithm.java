package com.wode.algorithm;

import com.google.common.collect.Range;
import com.wode.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * 自定义分片算法
 *
 * @author yinjihuan
 */
@Slf4j
public class MyPreciseShardingAlgorithm implements PreciseShardingAlgorithm<Date> {

    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");


    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Date> shardingValue) {
        Date value = shardingValue.getValue();
        String format = dateFormat.format(value);
        for (String tableName : availableTargetNames) {
            if (tableName.endsWith(format)) {
                return tableName;
            }
        }
        throw new IllegalArgumentException(String.format("在数据库未找到该月份%s的子表", format));
    }


}

