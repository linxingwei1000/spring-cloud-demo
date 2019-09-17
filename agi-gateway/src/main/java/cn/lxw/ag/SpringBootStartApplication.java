package cn.lxw.ag;

/**
 * @author linxingwei
 * @date 2019/9/11
 */

import cn.lxw.ag.filter.AuthorityFilter;
import cn.lxw.ag.filter.RateLimitFilter;
import cn.lxw.ag.filter.RouteRateLimitFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

/**
 * 排除原生Druid的快速配置类:跟进入可以看到，这个数据源会在这个spring.datasource.druid.*配置路径下找配置
 */
@SpringBootApplication
@EnableZuulProxy
public class SpringBootStartApplication {

    @Bean
    public RateLimitFilter rateLimitFilter(){
        return new RateLimitFilter();
    }

    @Bean
    public RouteRateLimitFilter routeRateLimitFilter(){
        return new RouteRateLimitFilter();
    }

    @Bean
    public AuthorityFilter accessFilter(){
        return new AuthorityFilter();
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootStartApplication.class, args);
    }
}
