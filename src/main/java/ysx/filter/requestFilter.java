package ysx.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class requestFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {
        // TODO Auto-generated method stub
        System.out.print("this is requestFilter.init");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // TODO Auto-generated method stub
        HttpServletRequest hsReq = (HttpServletRequest) request;
        System.out.print("this is requestFilter.doFilter");
        String urlString = hsReq.getRequestURL().toString();
        System.out.print("this is requestFilter.doFilter request: " + urlString);
        try {
            chain.doFilter(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void destroy() {
        // TODO Auto-generated method stub
        System.out.print("this is requestFilter.destroy");
    }

}
