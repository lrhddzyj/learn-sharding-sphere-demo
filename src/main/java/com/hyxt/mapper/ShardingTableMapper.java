package com.hyxt.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @description:
 * @author: lrh
 * @date: 2020/11/17 10:07
 */
public interface ShardingTableMapper {

  List<String> findTables(@Param("tablePrefix") String tablePrefix);

  void createOrderTable(@Param("tableName") String tableName);


}
