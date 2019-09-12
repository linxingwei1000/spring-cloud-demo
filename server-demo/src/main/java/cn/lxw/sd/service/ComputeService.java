package cn.lxw.sd.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author linxingwei
 * @date 2019/7/29
 */
@Service
public class ComputeService {

    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "addServiceFallback")
    public String add() {
        return restTemplate.getForEntity("http://provider-simple/add?a=10&b=20", String.class).getBody();
    }

    public String addServiceFallback(){
        return "add error";
    }
}
