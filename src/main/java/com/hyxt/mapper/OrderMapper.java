package com.hyxt.mapper;

import com.hyxt.entity.Order;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @description:
 * @author: lrh
 * @date: 2020/11/12 18:40
 */
public interface OrderMapper {

  int save(Order order);

  List<Order> findAll();

  List<Order> findByAppCodeAndTime(@Param("appCode") String appCode,
      @Param("beginDate") Date beginDate,@Param("endDate") Date endDate);

  List<Order> findPage(@Param("appCode") String appCode,
      @Param("beginDate") Date beginDate, @Param("endDate") Date endDate);

}
