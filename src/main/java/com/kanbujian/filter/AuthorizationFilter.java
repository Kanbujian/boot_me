package com.kanbujian.filter;

import com.kanbujian.dao.AppDao;
import com.kanbujian.entity.App;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@WebFilter("/*")
public class AuthorizationFilter implements Filter {
    private final Logger logger = LoggerFactory.getLogger(AuthorizationFilter.class);

    private String[] ignorePaths = {"/transactions/\\d*/notify/paid"};

    @Autowired
    private AppDao appDao;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        Map<String, String> headers = Collections.list(httpServletRequest.getHeaderNames())
                .stream()
                .collect(Collectors.toMap(h -> h , httpServletRequest::getHeader));
        String authorization = headers.get("authorization");
        String requestUrl = httpServletRequest.getRequestURI();
        boolean skip_auth = Arrays.stream(ignorePaths).anyMatch((path) -> {
            return requestUrl.matches(path);
        });

        if (skip_auth || (authorization!=null && appDao.findByToken(authorization).isPresent())){
            filterChain.doFilter(servletRequest, servletResponse);
        }else{
            httpServletResponse.sendError(403);
            return ;
        }
    }

    @Override
    public void destroy() {

    }
}
