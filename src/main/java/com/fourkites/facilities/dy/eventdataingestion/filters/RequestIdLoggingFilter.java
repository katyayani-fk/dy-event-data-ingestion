package com.fourkites.facilities.dy.eventdataingestion.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RequestIdLoggingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        MDC.put("request_id",((HttpServletRequest)servletRequest).getHeader("request_id"));
        filterChain.doFilter(servletRequest,servletResponse);
        MDC.remove("request_id");
    }
}
