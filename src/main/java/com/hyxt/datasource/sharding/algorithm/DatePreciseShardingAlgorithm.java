package com.hyxt.datasource.sharding.algorithm;

import com.hyxt.datasource.DynamicDataSourceConstants;
import com.hyxt.util.YearMonthUtils;
import java.util.Collection;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

/**
 * 精确的时间分片算法
 * @description:
 * @author: lrh
 * @date: 2020/11/16 16:54
 */
@Slf4j
public class DatePreciseShardingAlgorithm implements PreciseShardingAlgorithm<Date> {

  /**
   * 最小时间，在这个之前的时间的数据全部进入默认表
   */
  private final Date lowerDate;

  /**
   * 默认表
   */
  private final String defaultTableName;

  public DatePreciseShardingAlgorithm(Date defaultLowerDate,String defaultTableName) {
    this.lowerDate = defaultLowerDate;
    this.defaultTableName = defaultTableName;
  }

  @Override
  public String doSharding(Collection<String> availableTargetNames,
      PreciseShardingValue<Date> shardingValue) {
    String loginTableName = shardingValue.getLogicTableName();
    Date createTime = shardingValue.getValue();

    if(createTime == null || createTime.before(lowerDate) ){
      log.info("创建时间为空，或者当前时间:{} 小于最低时间 {} ，进入默认表", createTime, YearMonthUtils.formatYearMonthDate(lowerDate));
      return defaultTableName;
    }

    String  yyyyMM = YearMonthUtils.formatYearMonthDate(createTime);
    String tableName = loginTableName + DynamicDataSourceConstants.SEPERATOR + yyyyMM;
    return tableName;
  }
}
