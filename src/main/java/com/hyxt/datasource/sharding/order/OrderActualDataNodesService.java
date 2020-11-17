package com.hyxt.datasource.sharding.order;

import com.google.common.collect.Sets;
import com.hyxt.datasource.sharding.dynamic.actualdata.AbstractActualDataNodesService;
import com.hyxt.datasource.sharding.dynamic.actualdata.ActualDataNodesService;
import java.util.List;
import java.util.Set;
import org.apache.shardingsphere.core.rule.DataNode;
import org.apache.shardingsphere.shardingjdbc.jdbc.core.datasource.ShardingDataSource;

/**
 * @description:
 * @author: lrh
 * @date: 2020/11/17 11:11
 */
public class OrderActualDataNodesService extends AbstractActualDataNodesService implements
    ActualDataNodesService {

  private OrderActualDataNodesRepository orderActualDataNodesRepository;

  public OrderActualDataNodesService(
      ShardingDataSource shardingDataSource, String logicTableName,
      OrderActualDataNodesRepository orderActualDataNodesRepository) {
    super(shardingDataSource, logicTableName);
    this.orderActualDataNodesRepository = orderActualDataNodesRepository;
  }

  @Override
  protected Set<DataNode> getNewNodes(final String dataSourceName) {
    Set<DataNode> newDataNodes = Sets.newHashSet();
    List<String> actualDataInfo = orderActualDataNodesRepository.getActualDataInfo();

    for (String tableName : actualDataInfo) {
      final String dataNodeInfo = new StringBuilder().append(dataSourceName).append(".").append(tableName).toString();
      DataNode dataNode = new DataNode(dataNodeInfo);
      newDataNodes.add(dataNode);
    }
    return newDataNodes;
  }
}
