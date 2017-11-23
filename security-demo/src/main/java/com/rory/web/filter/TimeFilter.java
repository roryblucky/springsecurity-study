package com.rory.web.filter;

import javax.servlet.*;
import java.io.IOException;
import java.util.Date;

public class TimeFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("Filter init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        long start = new Date().getTime();
        System.out.println("Time filter start");
        filterChain.doFilter(servletRequest, servletResponse);
        long end = new Date().getTime() - start;
        System.out.println("Time filter 耗时 " + end);
    }

    @Override
    public void destroy() {

    }
}
