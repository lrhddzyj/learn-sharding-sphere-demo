package com.hyxt.datasource.sharding.order;

import com.hyxt.datasource.sharding.TableRuleConfigurationSource;
import com.hyxt.datasource.sharding.algorithm.DatePreciseShardingAlgorithm;
import com.hyxt.datasource.sharding.algorithm.DateRangeShardingAlgorithm;
import com.hyxt.util.YearMonthUtils;
import java.util.Date;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.NoneShardingStrategyConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.StandardShardingStrategyConfiguration;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: lrh
 * @date: 2020/11/16 14:58
 */
@Component
public class OrderTableRuleConfigurationSource implements TableRuleConfigurationSource {

  @Override
  public TableRuleConfiguration get() {
    return build();
  }

  /**
   * order 分表规则
   *
   * @return
   */
  private TableRuleConfiguration build() {
    String actualDataNodes = buildActualDataNodes();
    TableRuleConfiguration orderTableRuleConfig = new TableRuleConfiguration(
        OrderShardingConstants.BEHAVIOR_LOGIC_TABLE_NAME, actualDataNodes);
//    orderTableRuleConfig.setKeyGeneratorConfig(new KeyGeneratorConfiguration("SNOWFLAKE", "order_id",getProperties()));
    StandardShardingStrategyConfiguration standardShardingStrategyConfiguration = buildStandardShardingStrategyConfiguration();
    orderTableRuleConfig.setTableShardingStrategyConfig(standardShardingStrategyConfiguration);
    orderTableRuleConfig.setDatabaseShardingStrategyConfig(new NoneShardingStrategyConfiguration());
    return orderTableRuleConfig;
  }

  /**
   * 构建数据实例
   *
   * @return
   */
  private String buildActualDataNodes() {
    // 配置Order表规则
//    final String actualDataNodes = YearMonthUtils
//        .createActualDataNodes(DynamicDataSourceConstants.SHARDING_DATA_SOURCE + "." + OrderShardingConstants.BEHAVIOR_LOGIC_TABLE_NAME, 2020, 2030);

    String actualDataNodes = "${['shardingDataSource.order_original',"
        + "'shardingDataSource.order_202010']}";
//    String actualDataNodes = "${['shardingDataSource.order_original',"
//        + "'shardingDataSource.order_202010','shardingDataSource.order_202011','shardingDataSource.order_202012']}";
    return actualDataNodes;
  }

  private static Date DEFAULT_LOWER_DATE = YearMonthUtils.parseYearMonthDate(
      OrderShardingConstants.DEFAULT_LOWER_DATE_STR);

  private StandardShardingStrategyConfiguration buildStandardShardingStrategyConfiguration() {
    DatePreciseShardingAlgorithm datePreciseShardingAlgorithm = new DatePreciseShardingAlgorithm(
        DEFAULT_LOWER_DATE, OrderShardingConstants.DEFAULT_TABLE_NAME);

    DateRangeShardingAlgorithm dateRangeShardingAlgorithm = new DateRangeShardingAlgorithm(
        DEFAULT_LOWER_DATE, OrderShardingConstants.DEFAULT_TABLE_NAME);

    StandardShardingStrategyConfiguration standardShardingStrategyConfiguration = new StandardShardingStrategyConfiguration(
        OrderShardingConstants.TABLE_SHARDING_COLUMN, datePreciseShardingAlgorithm,
        dateRangeShardingAlgorithm);
    return standardShardingStrategyConfiguration;
  }

//  /**
//   * 分表算法
//   *
//   * @description:
//   * @author: lrh
//   * @date: 2020/11/13 14:34
//   */
//   class OrderTablePreciseShardingAlgorithm implements PreciseShardingAlgorithm<Date> {
//
//    @Override
//    public String doSharding(Collection<String> availableTargetNames,
//        PreciseShardingValue<Date> shardingValue) {
//      String loginTableName = shardingValue.getLogicTableName();
//
//
//
//      return null;
//    }
//
////    @Override
////    public String doSharding(Collection<String> availableTargetNames,
////        PreciseShardingValue<String> shardingValue) {
////      DateTime dateTime = DateTime.now();
////      DateTimeFormatter yyyyMMFormatter = DateTimeFormat.forPattern("yyyyMM");
////      String yyyyMM = yyyyMMFormatter.print(dateTime);
////      String tableName = OrderShardingConstants.BEHAVIOR_LOGIC_TABLE_NAME + "_" + yyyyMM;
////      return tableName;
////    }
//  }
//
//  /**
//   * 分表算法
//   *
//   * @description:
//   * @author: lrh
//   * @date: 2020/11/13 14:34
//   */
//  class OrderTablePreciseShardingAlgorithm2 implements ComplexKeysShardingAlgorithm<String> {
//
//    @Override
//    public Collection<String> doSharding(Collection<String> availableTargetNames,
//        ComplexKeysShardingValue<String> shardingValue) {
//
//
//      return null;
//    }
//  }
}
