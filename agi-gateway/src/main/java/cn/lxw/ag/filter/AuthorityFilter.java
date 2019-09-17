package cn.lxw.ag.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * @author linxingwei
 * @date 2019/9/16
 *
 * 校验网关统一处理
 */
public class AuthorityFilter extends ZuulFilter {

    private static Logger log = LoggerFactory.getLogger(AuthorityFilter.class);


    @Override
    public String filterType() {
        return FilterConstant.PRE;
    }

    /**
     * filter顺序，数值越大越靠后执行，越小就越先执行
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 建议配置中心配置，灵活开启是否过滤此条件
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return false;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        log.info(String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));

        String token = request.getHeader("token");
        //这边可以进行解压解密，用户token校验之类操作，这边作为示例，简单token直接校验
        if(token == null) {
            log.warn("token is empty");
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(HttpStatus.SC_UNAUTHORIZED);
            ctx.setResponseBody("unAuthrized");
            return null;
        }
        log.info("access token ok");
        return null;
    }
}
