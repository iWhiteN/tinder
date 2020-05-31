package com.tinder.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.OptionalInt;

import static com.tinder.utils.CookieReader.readCookie;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String key = "userId";

        String loginURI = request.getContextPath() + "/login";

        String registrationURI = request.getContextPath() + "/registration";

        boolean loginRequest = request.getRequestURI().equals(loginURI);
        boolean registrationRequest = request.getRequestURI().equals(registrationURI);

        OptionalInt id = readCookie(request, key);

        if (id.getAsInt() != -1 || loginRequest || registrationRequest) {
            filterChain.doFilter(request, response);
        } else {
            response.sendRedirect(loginURI);
        }
    }

    @Override
    public void destroy() {

    }
}
