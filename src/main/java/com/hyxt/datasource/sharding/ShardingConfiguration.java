package com.hyxt.datasource.sharding;

import com.alibaba.druid.pool.DruidDataSource;
import com.hyxt.datasource.DruidSettings;
import com.hyxt.datasource.DynamicDataSourceConstants;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.sql.DataSource;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.ShardingStrategyConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.StandardShardingStrategyConfiguration;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: lrh
 * @date: 2020/11/13 15:56
 */
@Configuration
@EnableConfigurationProperties(DruidSettings.class)
public class ShardingConfiguration {

  @Qualifier(DynamicDataSourceConstants.SHARDING_DATA_SOURCE)
  @Bean
  public DataSource shardingDataSource(DruidSettings ds,TableRuleConfigurationFactory tableRuleConfigurationFactory) throws SQLException {
    Map<String, DataSource> dataSourceMap = new HashMap<>();
    DataSource dataSource = dataSource(DynamicDataSourceConstants.SHARDING_DATA_SOURCE, ds);
    dataSourceMap.put(DynamicDataSourceConstants.SHARDING_DATA_SOURCE, dataSource);

    // 配置分片规则
    ShardingRuleConfiguration shardingRuleConfig = shardingRuleConfig(tableRuleConfigurationFactory);
    // 获取数据源对象
    return ShardingDataSourceFactory
        .createDataSource(dataSourceMap, shardingRuleConfig, new Properties());
  }

  /**
   * 分库分表配置
   *
   * @return
   */
  private ShardingRuleConfiguration shardingRuleConfig(TableRuleConfigurationFactory tableRuleConfigurationFactory) {
//    ShardingStrategyConfiguration shardingStrategyConfiguration = orderShardingStrategyConfiguration();
    ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
    Collection<TableRuleConfiguration> tableRuleConfigs = shardingRuleConfig.getTableRuleConfigs();
    tableRuleConfigs.clear();
    tableRuleConfigs.addAll(tableRuleConfigurationFactory.all());
//    shardingRuleConfig.setDefaultDatabaseShardingStrategyConfig(shardingStrategyConfiguration);
    return shardingRuleConfig;
  }

//  /**
//   * 分库
//   *
//   * @return
//   */
//  private ShardingStrategyConfiguration orderShardingStrategyConfiguration() {
//    return new StandardShardingStrategyConfiguration("-", new PreciseShardingAlgorithm() {
//      @Override
//      public String doSharding(Collection availableTargetNames, PreciseShardingValue shardingValue) {
//        return DynamicDataSourceConstants.SHARDING_DATA_SOURCE;
//      }
//    });
//  }

  private DataSource dataSource(String dsName, DruidSettings ds) throws SQLException {
    DruidDataSource druidDataSource = new DruidDataSource(false);
    druidDataSource.setName(dsName);
    druidDataSource.setUsername(ds.getUsername());
    druidDataSource.setUrl(ds.getUrl());
    druidDataSource.setPassword(ds.getPassword());
    druidDataSource.setFilters(ds.getFilters());
    druidDataSource.setMaxActive(ds.getMaxActive());
    druidDataSource.setInitialSize(ds.getInitialSize());
    druidDataSource.setMaxWait(ds.getMaxWait());
    druidDataSource.setMinIdle(ds.getMinIdle());
    druidDataSource.setTimeBetweenEvictionRunsMillis(ds.getTimeBetweenEvictionRunsMillis());
    druidDataSource.setMinEvictableIdleTimeMillis(ds.getMinEvictableIdleTimeMillis());
    druidDataSource.setValidationQuery(ds.getValidationQuery());
    druidDataSource.setTestWhileIdle(ds.isTestWhileIdle());
    druidDataSource.setTestOnBorrow(ds.isTestOnBorrow());
    druidDataSource.setTestOnReturn(ds.isTestOnReturn());
    druidDataSource.setPoolPreparedStatements(ds.isPoolPreparedStatements());
    druidDataSource.setMaxOpenPreparedStatements(ds.getMaxOpenPreparedStatements());
    return druidDataSource;
  }



}
