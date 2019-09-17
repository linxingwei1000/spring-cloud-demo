package cn.lxw.ag.filter;

/**
 * @author linxingwei
 * @date 2019/7/29
 */
public class FilterConstant {

    /**
     * 在请求被路由之前调用
     */
    public static final String PRE = "pre";

    /**
     * 在路由请求时调用
     */
    public static final String ROUTING = "routing";

    /**
     * 在routing和error过滤器之后被调用
     */
    public static final String POST = "post";

    /**
     * 处理请求发生错误时被调用
     */
    public static final String ERROR = "error";
}
