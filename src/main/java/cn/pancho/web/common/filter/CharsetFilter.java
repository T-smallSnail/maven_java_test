package cn.pancho.web.common.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;


//@WebFilter(filterName = "CharsetFilter",
//        urlPatterns = "/*",/*通配符（*）表示对所有的web资源进行拦截*/
//        initParams = {
//                @WebInitParam(name = "charset", value = "utf-8")/*这里可以放一些初始化的参数*/
//        })
public class CharsetFilter implements Filter {
    private String filterName;
    private String charset;


    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        /*过滤方法 主要是对request和response进行一些处理，然后交给下一个过滤器或Servlet处理*/
        System.out.println(filterName + "_doFilter()");
        req.setCharacterEncoding(charset);
        resp.setCharacterEncoding(charset);

        chain.doFilter(req, resp);
    }

    @Override
    public void init(FilterConfig config) throws ServletException {

        /*初始化方法  接收一个FilterConfig类型的参数 该参数是对Filter的一些配置*/

        filterName = config.getFilterName();
        charset = config.getInitParameter("charset");

        System.out.println("过滤器名称：" + filterName);
        System.out.println("字符集编码：" + charset);

    }

}
