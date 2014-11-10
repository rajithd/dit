package com.dit.security;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CorsFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.addHeader("Access-Control-Allow-Origin", "*");
        String requestedHeaders = request.getHeader("Access-Control-Request-Headers");
        if (requestedHeaders != null)
            response.addHeader("Access-Control-Allow-Headers", requestedHeaders);

        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, PATCH"); //Need to have this on ALL requests coz FF is ...FF

        if (!"options".equalsIgnoreCase(request.getMethod())) {
            chain.doFilter(servletRequest, servletResponse);
        }
        if (response.getStatus() > 226) {
            //Retry adding headers as the "ResponseBuilder" for WebApplicationException nuked them:
            response.addHeader("Access-Control-Allow-Origin", "*");
            if (requestedHeaders != null) {
                response.addHeader("Access-Control-Allow-Headers", requestedHeaders);
            }
            //Need to have this on ALL requests coz FF is ...FF
            response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, PATCH");
        }
    }

    @Override
    public void destroy() {

    }
}