package com.hyxt.datasource.sharding.dynamic.actualdata;

import java.util.List;

/**
 * @description:
 * @author: lrh
 * @date: 2020/11/17 11:20
 */
public interface ActualDataNodesRepository {

  List<String> getActualDataInfo();

  void createActualDataInfo(String tableName);

}
