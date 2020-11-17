package com.hyxt.datasource.sharding;

import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;

/**
 * 分表配置获取
 * @description:
 * @author: lrh
 * @date: 2020/11/16 14:57
 */
public interface TableRuleConfigurationSource {

  /**
   * 返回分表配置
   * @return
   */
  TableRuleConfiguration get();

}
