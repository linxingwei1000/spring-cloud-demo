package cn.lxw.ps.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

/**
 * @author linxingwei
 * @date 2019/7/26
 */
@RestController
public class ComputeApi {

    private final Logger logger = Logger.getLogger(ComputeApi.class.toString());

    @Value("${server.port}")
    String port;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public Integer add(@RequestParam Integer a, @RequestParam Integer b) {
        Integer r = a + b;
        logger.info("/add, port:" + port);
        return r;
    }
}
