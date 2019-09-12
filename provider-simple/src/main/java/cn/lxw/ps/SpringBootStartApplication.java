package cn.lxw.ps;

/**
 * @author linxingwei
 * @date 2019/9/11
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 排除原生Druid的快速配置类:跟进入可以看到，这个数据源会在这个spring.datasource.druid.*配置路径下找配置
 */
@SpringBootApplication
@EnableEurekaClient
public class SpringBootStartApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootStartApplication.class, args);
    }
}
