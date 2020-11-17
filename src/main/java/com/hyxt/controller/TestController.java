package com.hyxt.controller;

import com.github.pagehelper.Page;
import com.hyxt.entity.Order;
import com.hyxt.service.OrderService;
import java.util.Date;
import java.util.List;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: lrh
 * @date: 2020/11/13 11:21
 */
@RestController
@RequestMapping
public class TestController {


  @Autowired
  OrderService orderService;

  @RequestMapping("/save")
  public void save() {
    orderService.save();
  }


  @RequestMapping("/all")
  public List<Order> all() {

    return orderService.getAll();
  }

  @RequestMapping("/part")
  public List<Order> part() {
    String appCode = "appCode";
    DateTime now = DateTime.now();

//    Date beginDate = now.toDate();
    Date beginDate = now.minusMonths(1).toDate();
    Date endDate = now.plusMonths(1).toDate();

    return orderService.findByAppCodeAndTime(appCode, beginDate, endDate);
  }


  @RequestMapping("/page/{pageNum}")
  public Page<Order> page(@PathVariable("pageNum") int pageNum) {
    String appCode = "appCode";
    DateTime now = DateTime.now();
    Date beginDate = now.minusMonths(1).toDate();
    Date endDate = now.plusMonths(1).toDate();

    return orderService.findPage(appCode, beginDate, endDate, pageNum, 2);

  }


}
