package com.hyxt.controller;

import com.hyxt.datasource.sharding.order.OrderActualDataNodesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: lrh
 * @date: 2020/11/17 11:44
 */
@RestController
@RequestMapping
public class TestRefreshNodeDataController {


  @Autowired
  private OrderActualDataNodesService orderActualDataNodesService;


  @RequestMapping("/refresh/order")
  public void refresh() {
    orderActualDataNodesService.refresh();
  }


}
