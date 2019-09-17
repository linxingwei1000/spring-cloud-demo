package cn.lxw.ps.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

/**
 * @author linxingwei
 * @date 2019/9/16
 */
@RestController
@RequestMapping(value = "hello")
public class HelloApi {

    private final Logger logger = Logger.getLogger(ComputeApi.class.toString());

    @Value("${server.port}")
    String port;

    @RequestMapping(value = "/port", method = RequestMethod.GET)
    public String add() {
        return port;
    }
}
