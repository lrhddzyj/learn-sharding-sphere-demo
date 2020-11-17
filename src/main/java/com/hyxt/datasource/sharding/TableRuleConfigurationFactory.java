package com.hyxt.datasource.sharding;

import com.google.common.collect.Lists;
import java.util.List;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 分表策略工厂
 * @description:
 * @author: lrh
 * @date: 2020/11/16 14:55
 */
@Component
public class TableRuleConfigurationFactory {

  @Autowired
  private List<TableRuleConfigurationSource> tableRuleConfigurationSourceList;

  public  List<TableRuleConfiguration> all() {
    List<TableRuleConfiguration> configurationList = Lists
        .newArrayListWithCapacity(tableRuleConfigurationSourceList.size());
    for (TableRuleConfigurationSource tableRuleConfigurationSource : tableRuleConfigurationSourceList) {
      configurationList.add(tableRuleConfigurationSource.get());
    }
    return configurationList;
  }




}
