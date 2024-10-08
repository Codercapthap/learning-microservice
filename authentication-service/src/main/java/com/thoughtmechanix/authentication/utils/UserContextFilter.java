package com.thoughtmechanix.authentication.utils;

import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class UserContextFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {


        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        System.out.println("****** I am entering the licensing service id with auth token: " + httpServletRequest.getHeader("Authorization"));


        UserContextHolder.getContext().setCorrelationId(  httpServletRequest.getHeader(UserContext.CORRELATION_ID) );
        UserContextHolder.getContext().setUserId( httpServletRequest.getHeader(UserContext.USER_ID) );
        UserContextHolder.getContext().setAuthToken( httpServletRequest.getHeader(UserContext.AUTH_TOKEN) );
        UserContextHolder.getContext().setOrgId( httpServletRequest.getHeader(UserContext.ORG_ID) );

        filterChain.doFilter(httpServletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}
}