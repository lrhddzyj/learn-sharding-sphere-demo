package com.hyxt;

import com.hyxt.component.SnowFlakeIdComponent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @description:
 * @author: lrh
 * @date: 2020/11/12 17:46
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,
    DataSourceTransactionManagerAutoConfiguration.class
})
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class TestApplication {

  public static void main(String[] args) {
    SpringApplication.run(TestApplication.class, args);
  }


  @Bean
  public SnowFlakeIdComponent snowFlakeIdComponent(){
    return new SnowFlakeIdComponent(1, 1);
  }

}
