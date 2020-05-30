package com.tinder.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String key = "auth";

        String loginURI = request.getContextPath() + "/login";

        String registrationURI = request.getContextPath() + "/registration";

        boolean loginRequest = request.getRequestURI().equals(loginURI);
        boolean registrationRequest = request.getRequestURI().equals(registrationURI);

        Optional<Cookie[]> cookies = Optional.ofNullable(request.getCookies());
        String isAuth = cookies.map(value -> Arrays.stream(value)
                .filter(c -> key.equals(c.getName()))
                .map(Cookie::getValue)
                .findAny()
                .orElse("false")).orElse("false");

        if (isAuth.equals("true") || loginRequest || registrationRequest) {
            filterChain.doFilter(request, response);
        } else {
            response.sendRedirect(loginURI);
        }
    }

    @Override
    public void destroy() {

    }
}
