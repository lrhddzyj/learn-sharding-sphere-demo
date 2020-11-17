package com.hyxt.datasource;

import com.github.pagehelper.PageInterceptor;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Properties;
import javax.sql.DataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @description:
 * @author: lrh
 * @date: 2020/11/13 15:55
 */
@Configuration
@MapperScan(basePackages ="com.hyxt.mapper",sqlSessionFactoryRef = "dynamicDataSourceSqlSessionFactoryBean")
@EnableTransactionManagement
public class DatasourceConfiguration {

  @Primary
  @Bean(name = "dynamicDataSource")
  public DataSource dynamicDataSource(@Qualifier(DynamicDataSourceConstants.STANDARD_DATA_SOURCE) DataSource standardDataSource,
      @Qualifier(DynamicDataSourceConstants.SHARDING_DATA_SOURCE) DataSource shardingDataSource) {
    DynamicRoutingDataSource dynamicRoutingDataSource = new DynamicRoutingDataSource();
    Map<Object, Object> dataSourceMap = Maps.newHashMapWithExpectedSize(2);
    dataSourceMap.put(DynamicDataSourceConstants.STANDARD_DATA_SOURCE, standardDataSource);
    dataSourceMap.put(DynamicDataSourceConstants.SHARDING_DATA_SOURCE, shardingDataSource);
    dynamicRoutingDataSource.setDefaultTargetDataSource(standardDataSource);
    dynamicRoutingDataSource.setTargetDataSources(dataSourceMap);
    DynamicDataSourceHolder.dataSourceKeys.addAll(dataSourceMap.keySet());
    return dynamicRoutingDataSource;
  }


  @Bean(name = "dynamicDataSourceSqlSessionFactoryBean")
  public SqlSessionFactoryBean sqlSessionFactory(DataSource dynamicDataSource,PageInterceptor pageHelper) {
    SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
    sqlSessionFactoryBean.setDataSource(dynamicDataSource);
    sqlSessionFactoryBean.setPlugins(new Interceptor[]{pageHelper});
    return sqlSessionFactoryBean;
  }


  @Bean(name = "pageHelper")
  public PageInterceptor pageHelper() {
    PageInterceptor pageHelper = new PageInterceptor();
    Properties properties = new Properties();
    properties.setProperty("helperDialect", "mysql");
    properties.setProperty("reasonable", "true");
    properties.setProperty("supportMethodsArguments", "true");
    properties.setProperty("params", "count=countSql");
    properties.setProperty("autoRuntimeDialect", "true");

    pageHelper.setProperties(properties);
    return pageHelper;
  }


  @Bean
  public DataSourceTransactionManager transactionManager(DataSource dynamicDataSource) {
    return new DataSourceTransactionManager(dynamicDataSource);
  }


}
