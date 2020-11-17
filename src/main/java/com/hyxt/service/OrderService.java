package com.hyxt.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hyxt.component.SnowFlakeIdComponent;
import com.hyxt.datasource.DynamicDataSource;
import com.hyxt.datasource.DynamicDataSourceConstants;
import com.hyxt.entity.Order;
import com.hyxt.mapper.OrderMapper;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: lrh
 * @date: 2020/11/12 18:53
 */
@Service
public class OrderService {

  @Autowired
  private OrderMapper orderMapper;

  @Autowired
  private SnowFlakeIdComponent snowFlakeIdComponent;

  @DynamicDataSource(DynamicDataSourceConstants.SHARDING_DATA_SOURCE)
  public void save() {
    Order order = new Order();
    order.setOrderId(snowFlakeIdComponent.nextId());
    order.setOrderName(UUID.randomUUID().toString());
    order.setAppCode("appCode");
    DateTime now = DateTime.now();
    Date d1 = now.toDate();
    order.setCreateTime(d1);
    orderMapper.save(order);


    Order order2 = new Order();
    order2.setOrderId(snowFlakeIdComponent.nextId());
    order2.setOrderName(UUID.randomUUID().toString());
    order2.setAppCode("appCode");
    DateTime tmpDate = now.plusMonths(1);
    Date d2 = tmpDate.toDate();
    order2.setCreateTime(d2);
    orderMapper.save(order2);


    Order order3 = new Order();
    order3.setOrderId(snowFlakeIdComponent.nextId());
    order3.setOrderName(UUID.randomUUID().toString());
    order3.setAppCode("appCode");
    DateTime tmpDate2 = now.minusMonths(5);
    Date d3 = tmpDate2.toDate();
    order3.setCreateTime(d3);
    orderMapper.save(order3);

  }


  @DynamicDataSource(DynamicDataSourceConstants.SHARDING_DATA_SOURCE)
  public List<Order> getAll() {
    return orderMapper.findAll();
  }


  @DynamicDataSource(DynamicDataSourceConstants.SHARDING_DATA_SOURCE)
  public List<Order> findByAppCodeAndTime(String appCode,Date beginDate,Date endDate) {
    return orderMapper.findByAppCodeAndTime(appCode, beginDate, endDate);
  }

  @DynamicDataSource(DynamicDataSourceConstants.SHARDING_DATA_SOURCE)
  public Page<Order> findPage(String appCode,Date beginDate,Date endDate,int pageNum,int pageSize) {
    PageHelper.startPage(pageNum, pageSize);
    Page<Order> page = (Page)orderMapper.findPage(appCode, beginDate, endDate);
    return page;
  }





}
