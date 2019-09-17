package cn.lxw.ag.filter;

import com.google.common.collect.Maps;
import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.ReflectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.net.URL;
import java.util.Map;

/**
 * @author linxingwei
 * @date 2019/9/16
 */
public class RouteRateLimitFilter extends ZuulFilter {

    private Map<String, RateLimiter> map = Maps.newConcurrentMap();


    @Override
    public String filterType() {
        return FilterConstant.PRE;
    }

    /**
     * * 要运行在PreDecorationFilter之后，即order要大于5（要运行在PreDecorationFilter之后的order），这样才能拿到配置的serviceId，实现按业务进行限流
     *
     * @return
     */
    @Override
    public int filterOrder() {
        return 6;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        try {
            RequestContext currentContext = RequestContext.getCurrentContext();
            HttpServletResponse response = currentContext.getResponse();
            String key = null;

            String serviceId = (String) currentContext.get("serviceId");
            if (serviceId != null) {
                // 对于service格式的路由，走RibbonRoutingFilter
                key = serviceId;
                map.putIfAbsent(serviceId, RateLimiter.create(1000.0));
            } else {
                URL routeHost = currentContext.getRouteHost();
                if (routeHost != null) {
                    String url = routeHost.toString();
                    key = url;
                    map.putIfAbsent(url, RateLimiter.create(1000.0));
                }
            }

            RateLimiter rateLimiter = map.get(key);
            if (!rateLimiter.tryAcquire()) {
                response.setContentType(MediaType.TEXT_PLAIN_VALUE);
                response.setStatus(HttpStatus.SC_FORBIDDEN);
                response.getWriter().append("too many request");
                currentContext.setSendZuulResponse(false);
                throw new ZuulException("too many request", HttpStatus.SC_FORBIDDEN, "too many request");
            }
        } catch (Exception e) {
            ReflectionUtils.rethrowRuntimeException(e);
        }
        return null;
    }


    public static void main(String[] args) throws InterruptedException {
        RateLimiter rl = RateLimiter.create(2);
        while (true) {
            System.out.println(String.format("time %b", rl.tryAcquire(5)));
            Thread.sleep(1000);
        }
    }
}
