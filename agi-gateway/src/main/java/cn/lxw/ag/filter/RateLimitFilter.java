package cn.lxw.ag.filter;

import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.http.HttpStatus;
import org.springframework.util.ReflectionUtils;

import javax.servlet.http.HttpServletResponse;

/**
 * @author linxingwei
 * @date 2019/9/16
 */
public class RateLimitFilter extends ZuulFilter {

    private final RateLimiter rateLimiter = RateLimiter.create(1000.0);


    @Override
    public String filterType() {
        return FilterConstant.PRE;
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return false;
    }

    @Override
    public Object run() {
        try{
            RequestContext currentContext = RequestContext.getCurrentContext();
            HttpServletResponse response = currentContext.getResponse();

            if(!rateLimiter.tryAcquire()){
                response.setStatus( HttpStatus.SC_FORBIDDEN);
                response.getWriter().append("too many request");
                currentContext.setSendZuulResponse(false);
                throw new ZuulException("too many request", HttpStatus.SC_FORBIDDEN, "too many request");
            }
        }catch (Exception e){
            ReflectionUtils.rethrowRuntimeException(e);
        }
        return null;
    }
}
