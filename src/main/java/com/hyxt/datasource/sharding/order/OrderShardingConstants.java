package com.hyxt.datasource.sharding.order;

/**
 * @description:
 * @author: lrh
 * @date: 2020/11/13 17:26
 */
public interface OrderShardingConstants {

  String BEHAVIOR_LOGIC_TABLE_NAME = "order";

  String TABLE_SHARDING_COLUMN = "create_time";

  String DEFAULT_LOWER_DATE_STR = "202010";

  String DEFAULT_TABLE_NAME = "order_original";


}
