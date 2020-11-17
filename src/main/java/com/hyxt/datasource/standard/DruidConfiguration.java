package com.hyxt.datasource.standard;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.hyxt.datasource.DruidSettings;
import com.hyxt.datasource.DynamicDataSourceConstants;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

/**
 * @description:
 * @author: lrh
 * @date: 2020/11/13 15:55
 */
@Configuration
@EnableConfigurationProperties(DruidSettings.class)
public class DruidConfiguration {


  @Qualifier(DynamicDataSourceConstants.STANDARD_DATA_SOURCE)
  @Bean(destroyMethod = "close", initMethod = "init")
  public DataSource dataSource(DruidSettings ds) throws SQLException {
    DruidDataSource druidDataSource = new DruidDataSource(false);
    if (ds.getId() != null) {
      druidDataSource.setName(ds.getId());
    }
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


  @Bean(name = "stat-filter")
  public StatFilter statFilter(DruidSettings ds) {
    StatFilter statFilter = new StatFilter();
    statFilter.setSlowSqlMillis(ds.getSlowSqlMillis());
    statFilter.setLogSlowSql(ds.isLogSlowSql());
    return statFilter;
  }


//  @Bean
//  @Qualifier(DynamicDataSourceConstants.STANDARD_SQL_SESSION_FACTORYBEAN)
//  public SqlSessionFactoryBean sqlSessionFactory(
//      @Qualifier(DynamicDataSourceConstants.STANDARD_DATA_SOURCE) DataSource dataSource) {
//    SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
//    sqlSessionFactoryBean.setDataSource(dataSource);
//    return sqlSessionFactoryBean;
//  }
//
//  @Bean
//  @Qualifier(DynamicDataSourceConstants.STANDARD_DATA_SOURCE_TRANSACTION_MANAGER)
//  public DataSourceTransactionManager transactionManager(
//      @Qualifier(DynamicDataSourceConstants.STANDARD_DATA_SOURCE) DataSource dataSource) {
//    return new DataSourceTransactionManager(dataSource);
//  }

}
