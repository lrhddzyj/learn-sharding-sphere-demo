package com.hyxt.datasource.sharding.dynamic.actualdata;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.core.rule.DataNode;
import org.apache.shardingsphere.core.rule.ShardingRule;
import org.apache.shardingsphere.core.rule.TableRule;
import org.apache.shardingsphere.shardingjdbc.jdbc.core.context.ShardingRuntimeContext;
import org.apache.shardingsphere.shardingjdbc.jdbc.core.datasource.ShardingDataSource;
import org.springframework.util.CollectionUtils;

/**
 * @description:
 * @author: lrh
 * @date: 2020/11/17 10:35
 */
@Slf4j
public abstract class AbstractActualDataNodesService implements ActualDataNodesService {

  protected ShardingDataSource shardingDataSource;

  protected String logicTableName;

  public AbstractActualDataNodesService(
      ShardingDataSource shardingDataSource, String logicTableName) {
    this.shardingDataSource = shardingDataSource;
    this.logicTableName = logicTableName;
  }

  @Override
  public void refresh() {
    ShardingRuntimeContext runtimeContext = shardingDataSource.getRuntimeContext();
    ShardingRule shardingRule = runtimeContext.getRule();
    TableRule tableRule = shardingRule.getTableRule(logicTableName);
    List<DataNode> oldActualDataNodes = tableRule.getActualDataNodes();
    String dataSourceName = oldActualDataNodes.get(0).getDataSourceName();
    Set<DataNode> newNodes = getNewNodes(dataSourceName);
    if (CollectionUtils.isEmpty(newNodes)) {
      log.error(String.format("未找到逻辑表 %s 的表实例数据", logicTableName));
      return;
    }
    try {
      doRefresh(dataSourceName, tableRule, Lists.newArrayList(newNodes));
    } catch (NoSuchFieldException | IllegalAccessException e) {
      throw new RuntimeException("刷新data nodes 失败");
    }
  }

  /**
   * 执行动态刷新
   */
  protected void doRefresh(String dataSourceName, TableRule tableRule, List<DataNode> newDataNodes)
      throws NoSuchFieldException, IllegalAccessException {
    Set<String> actualTables = Sets.newHashSet();
    Map<DataNode, Integer> dataNodeIndexMap = Maps.newHashMap();
    AtomicInteger index = new AtomicInteger(0);

    for (DataNode dataNode : newDataNodes) {
      actualTables.add(dataNode.getTableName());
      if (index.intValue() == 0) {
        dataNodeIndexMap.put(dataNode, 0);
      } else {
        dataNodeIndexMap.put(dataNode, index.intValue());
      }
      index.incrementAndGet();
    }
    Field modifiersField = Field.class.getDeclaredField("modifiers");
    modifiersField.setAccessible(true);

    // 动态刷新：actualDataNodesField
    Field actualDataNodesField = TableRule.class.getDeclaredField("actualDataNodes");
    modifiersField.setInt(actualDataNodesField, actualDataNodesField.getModifiers() & ~Modifier.FINAL);
    actualDataNodesField.setAccessible(true);
    actualDataNodesField.set(tableRule, newDataNodes);

    // 动态刷新：actualTablesField
    Field actualTablesField = TableRule.class.getDeclaredField("actualTables");
    actualTablesField.setAccessible(true);
    actualTablesField.set(tableRule, actualTables);

    // 动态刷新：dataNodeIndexMapField
    Field dataNodeIndexMapField = TableRule.class.getDeclaredField("dataNodeIndexMap");
    dataNodeIndexMapField.setAccessible(true);
    dataNodeIndexMapField.set(tableRule, dataNodeIndexMap);

    // 动态刷新：datasourceToTablesMapField
    Map<String, Collection<String>> datasourceToTablesMap = Maps.newHashMap();
    datasourceToTablesMap.put(dataSourceName, actualTables);
    Field datasourceToTablesMapField = TableRule.class.getDeclaredField("datasourceToTablesMap");
    datasourceToTablesMapField.setAccessible(true);
    datasourceToTablesMapField.set(tableRule, datasourceToTablesMap);
  }


  /**
   * 动态获取节点数据源
   *
   * @param dataSourceName
   * @return
   */
  protected abstract Set<DataNode> getNewNodes(final String dataSourceName);


}
