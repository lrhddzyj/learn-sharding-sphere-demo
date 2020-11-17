package com.hyxt.datasource;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: lrh
 * @date: 2020/11/13 16:55
 */
public class DynamicDataSourceHolder {

  private static final ThreadLocal<String> contextHolder = new ThreadLocal() {
    @Override
    protected Object initialValue() {
      return DynamicDataSourceConstants.STANDARD_DATA_SOURCE;
    }
  };

  /**
   * All DataSource List
   */
  public static List<Object> dataSourceKeys = new ArrayList<>();

  /**
   * To switch DataSource
   *
   * @param key the key
   */
  public static void setDataSourceKey(String key) {
    contextHolder.set(key);
  }

  /**
   * Get current DataSource
   *
   * @return data source key
   */
  public static String getDataSourceKey() {
    return contextHolder.get();
  }

  /**
   * To set DataSource as default
   */
  public static void clearDataSourceKey() {
    contextHolder.remove();
  }

  /**
   * Check if give DataSource is in current DataSource list
   *
   * @param key the key
   * @return boolean boolean
   */
  public static boolean containDataSourceKey(String key) {
    return dataSourceKeys.contains(key);
  }



}
