package com.hyxt.datasource.sharding.algorithm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Range;
import com.google.common.collect.Sets;
import com.hyxt.datasource.DynamicDataSourceConstants;
import com.hyxt.util.YearMonthUtils;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

/**
 * 区间区间的分表算法
 * @description:
 * @author: lrh
 * @date: 2020/11/16 17:12
 */
@Slf4j
public class DateRangeShardingAlgorithm implements RangeShardingAlgorithm<Date> {

  /**
   * 最小时间，在这个之前的时间的数据全部进入默认表
   */
  private final Date lowerDate;

  /**
   * 默认表
   */
  private final String defaultTableName;

  private static ObjectMapper objectMapper = new ObjectMapper();

  public DateRangeShardingAlgorithm(Date lowerDate, String defaultTableName) {
    this.lowerDate = lowerDate;
    this.defaultTableName = defaultTableName;
  }

  @Override
  public Collection<String> doSharding(Collection<String> availableTargetNames,
      RangeShardingValue<Date> shardingValue) {
    Collection<String> tableSet = Sets.newConcurrentHashSet();
    String logicTableName = shardingValue.getLogicTableName();
    Range<Date> dates = shardingValue.getValueRange();
    Date beginPoint = dates.lowerEndpoint();
    Date endpoint = dates.upperEndpoint();

    String beginYearMonth = YearMonthUtils.formatYearMonthDate(beginPoint);
    String endYearMonth = YearMonthUtils.formatYearMonthDate(endpoint);

    List<String> yearMonthNodes = YearMonthUtils.getYearMonthNodes(beginYearMonth, endYearMonth);
    for (String yearMonthNode : yearMonthNodes) {
      Date yearMonthDate = YearMonthUtils.parseYearMonthDate(yearMonthNode);
      if (yearMonthDate.before(lowerDate)) {
        tableSet.add(defaultTableName);
      }else{
        String tableName = logicTableName + DynamicDataSourceConstants.SEPERATOR + yearMonthNode;
        tableSet.add(tableName);
      }
    }
    try {
      log.info("要查询的表集合:{}",objectMapper.writeValueAsString(tableSet));
    } catch (JsonProcessingException e) {

    }
    return tableSet;

  }
}
