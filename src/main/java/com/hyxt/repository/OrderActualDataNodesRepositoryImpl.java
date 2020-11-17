package com.hyxt.repository;

import com.hyxt.datasource.sharding.order.OrderActualDataNodesRepository;
import com.hyxt.mapper.ShardingTableMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: lrh
 * @date: 2020/11/17 11:42
 */
@Component
public class OrderActualDataNodesRepositoryImpl implements OrderActualDataNodesRepository {

  @Autowired
  ShardingTableMapper shardingTableMapper;

  private static final String ORDER_TABLE_PREFIX = "order_";

  private static final String CREATE_ORDER_TABLE_SQL_TEMPLATE = "CREATE TABLE ${tableName} (`order_name` VARCHAR (128) DEFAULT NULL,`app_code` VARCHAR (255) DEFAULT NULL,`order_id` BIGINT (30) NOT NULL,`create_time` datetime DEFAULT NULL,PRIMARY KEY (`order_id`) USING BTREE) ENGINE=INNODB DEFAULT CHARSET=utf8;";

  @Override
  public List<String> getActualDataInfo() {
    return shardingTableMapper.findTables(ORDER_TABLE_PREFIX + "%");
  }

  @Override
  public void createActualDataInfo(String tableName) {
    shardingTableMapper.createOrderTable(tableName);
  }


}
