package com.chaonengquan.filter;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebFilter(filterName = "myFilter1", urlPatterns = {"/*"}, dispatcherTypes = {DispatcherType.REQUEST})
public class MyFilter100 implements Filter {
    private final Logger logger = LoggerFactory.getLogger(getClass());

//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//
//    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse)servletResponse;

        logger.info("111111111111 pre action from myFilter1, remoteHost={}", req.getRemoteHost());
        logger.info("111111111111 pre action from myFilter1, RequestURI={}", req.getRequestURI());
        logger.info("111111111111 pre action from myFilter1, Header={}", req.getHeader("testHeader"));

        filterChain.doFilter(req, resp);

        logger.info("111111111111 post action from myFilter1, status={}", resp.getStatus());
        logger.info("111111111111 post action from myFilter1, HeaderNames{}", req.getHeaderNames());


    }

//    @Override
//    public void destroy() {
//
//    }


}
