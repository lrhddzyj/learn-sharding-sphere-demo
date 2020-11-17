package com.hyxt.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @description:
 * @author: lrh
 * @date: 2020/11/13 16:54
 */
public class DynamicRoutingDataSource extends AbstractRoutingDataSource {

  @Override
  protected Object determineCurrentLookupKey() {
    return DynamicDataSourceHolder.getDataSourceKey();
  }
}
