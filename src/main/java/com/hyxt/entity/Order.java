package com.hyxt.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @description:
 * @author: lrh
 * @date: 2020/11/12 18:38
 */
@Data
public class Order implements Serializable {

  private static final long serialVersionUID = 3083576862798047555L;

  private Long orderId;

  private String appCode;

  private String orderName;

  private Date createTime;

  @Override
  public String toString() {
    return "Order{" +
        "orderId=" + orderId +
        ", appCode='" + appCode + '\'' +
        ", orderName='" + orderName + '\'' +
        ", createTime=" + createTime +
        '}';
  }
}
