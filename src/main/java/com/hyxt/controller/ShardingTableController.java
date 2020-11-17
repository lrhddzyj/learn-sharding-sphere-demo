package com.hyxt.controller;

import com.hyxt.datasource.sharding.order.OrderActualDataNodesRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: lrh
 * @date: 2020/11/17 10:13
 */
@RestController
@RequestMapping
public class ShardingTableController {

  @Autowired
  private OrderActualDataNodesRepository orderActualDataNodesRepository;

  @RequestMapping("/order/table")
  public List<String> findTableList() {
    return orderActualDataNodesRepository.getActualDataInfo();
  }

  @RequestMapping("/order/table/create")
  public void createTable(@RequestParam("tableName") String tableName) {
    orderActualDataNodesRepository.createActualDataInfo(tableName);
  }

}
