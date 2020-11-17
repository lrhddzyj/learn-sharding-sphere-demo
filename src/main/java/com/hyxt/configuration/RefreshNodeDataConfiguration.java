package com.hyxt.configuration;

import com.hyxt.datasource.DynamicDataSourceConstants;
import com.hyxt.datasource.sharding.order.OrderActualDataNodesRepository;
import com.hyxt.datasource.sharding.order.OrderActualDataNodesService;
import com.hyxt.datasource.sharding.order.OrderShardingConstants;
import javax.sql.DataSource;
import org.apache.shardingsphere.shardingjdbc.jdbc.core.datasource.ShardingDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: lrh
 * @date: 2020/11/17 11:46
 */
@Configuration
public class RefreshNodeDataConfiguration {

  @Autowired
  @Qualifier(DynamicDataSourceConstants.SHARDING_DATA_SOURCE)
  private DataSource shardingDataSource;

  @Bean
  public OrderActualDataNodesService orderActualDataNodesService(OrderActualDataNodesRepository orderActualDataNodesRepository) {
    return new OrderActualDataNodesService((ShardingDataSource) shardingDataSource,
        OrderShardingConstants.BEHAVIOR_LOGIC_TABLE_NAME,
        orderActualDataNodesRepository);
  }






}
