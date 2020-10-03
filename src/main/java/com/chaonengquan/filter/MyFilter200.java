package com.chaonengquan.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebFilter(filterName = "myFilter2", urlPatterns = {"/allCustomer"}, dispatcherTypes = {DispatcherType.REQUEST})
public class MyFilter200 implements Filter {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        logger.info("222222222222 pre action from myFilter2, remoteHost={}", req.getRemoteHost());
        logger.info("222222222222 pre action from myFilter2, RequestURI={}", req.getRequestURI());
        logger.info("222222222222 pre action from myFilter2, Header={}", req.getHeader("testHeader"));

        filterChain.doFilter(req, resp);

        logger.info("222222222222 post action from myFilter2, status={}", resp.getStatus());
        logger.info("222222222222 post action from myFilter2, HeaderNames{}", req.getHeaderNames());

    }


}
