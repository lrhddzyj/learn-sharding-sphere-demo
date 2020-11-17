package com.hyxt.datasource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @description:
 * @author: lrh
 * @date: 2020/11/13 17:17
 */
@Component
@Aspect
public class DynamicDataSourceAspect {

  private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceAspect.class);

  @Pointcut("@annotation(com.hyxt.datasource.DynamicDataSource)")
  private void chooseDataSource() {

  }

  @Before("chooseDataSource()&&@annotation(dynamicDataSource)")
  public void autoSetDataSource(JoinPoint joinPoint, DynamicDataSource dynamicDataSource) {
    String dataSourceName = dynamicDataSource.value();
    if (StringUtils.hasLength(dataSourceName)) {
      if (!DynamicDataSourceHolder.containDataSourceKey(dataSourceName)) {
        logger.warn("DataSource {}  doesn't has exist,use default dataSource", dataSourceName);
      } else {
        DynamicDataSourceHolder.setDataSourceKey(dataSourceName);
      }
    }
  }

  @After("chooseDataSource()")
  public void clear(){
    DynamicDataSourceHolder.clearDataSourceKey();

  }
}
