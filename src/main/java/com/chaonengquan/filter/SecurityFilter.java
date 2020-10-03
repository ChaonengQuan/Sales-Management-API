package com.chaonengquan.filter;

import com.chaonengquan.init.AppInitializer;
import com.chaonengquan.service.JWTService;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebFilter(filterName = "securityFilter", urlPatterns = {"/*"}, dispatcherTypes = {DispatcherType.REQUEST})
public class SecurityFilter implements Filter {
    private final Logger logger = LoggerFactory.getLogger(AppInitializer.class);

    @Autowired
    JWTService jwtService;

    private static String AUTH_URI = "/auth";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        //int statusCode = authorization_firstVersion(req);
        int statusCode = authorization(req);

        if (statusCode == HttpServletResponse.SC_ACCEPTED) {
            filterChain.doFilter(req, resp);
        } else {
            resp.sendError(statusCode);
        }

    }

//    private int authorization_firstVersion(HttpServletRequest req) {
//        int statusCode = HttpServletResponse.SC_UNAUTHORIZED;
//        String uri = req.getRequestURI();
//
//        if (uri.equalsIgnoreCase(AUTH_URI)) {
//            statusCode = HttpServletResponse.SC_ACCEPTED;
//        }
//        return statusCode;
//    }

    private int authorization(HttpServletRequest req){
        int statusCode = HttpServletResponse.SC_UNAUTHORIZED;

        String uri = req.getRequestURI();

        if (uri.equalsIgnoreCase(AUTH_URI)) {
            statusCode = HttpServletResponse.SC_ACCEPTED;
        }

        String httpMethodValue = req.getMethod();
        try{
            String wholeTokenString = req.getHeader("Authorization");
            /*
             * ^ start of a line
             * . -> any character
             * * -> zero or more
             * ? -> once or more
             * *? zero or many times
             * Examples:
             * str.replaceAll("^(.*?) ", "") -->  It is looking for the first space from the beginning of a string and remove it.
             * 									"12 123 2345678" -> "12123 2345678 "
             * str.replaceAll(".$", "")  --> means remove the last character (except line terminators) of a string
             *                               line terminators are carriage return "\r" and new line "\n"
             */
            String token = wholeTokenString.replaceAll("^(.*?) ", "");


            if(token == null || token.trim().isEmpty()){
                return statusCode;
            }

            Claims claims = jwtService.decryptJwtToken(token);
            String allowedResource = "";
            switch (httpMethodValue){
                case "GET": allowedResource = (String)claims.get("allowedReadResource"); break;
                case "POST": allowedResource = (String)claims.get("allowedCreateResource"); break;
                case "PUT": allowedResource = (String)claims.get("allowedUpdateResource"); break;
                case "PATCH": allowedResource = (String)claims.get("allowedUpdateResource"); break;
                case "DELETE":  allowedResource = (String)claims.get("allowedDeleteResource"); break;
            }

            String[] allowedResourceArray = allowedResource.split(",");
            for(String s :allowedResourceArray){
                //if incoming request(/path) matches one of the allowed resources
                if (uri.trim().toLowerCase().startsWith(s.trim().toLowerCase())) {
                    statusCode = HttpServletResponse.SC_ACCEPTED;
                    break;
                }
            }

            logger.debug("httpMethodValue: {}, allowed resources: {}", httpMethodValue, allowedResource);
        }catch (Exception e){
            logger.error(e.getMessage());
        }

        return statusCode;
    }


}
