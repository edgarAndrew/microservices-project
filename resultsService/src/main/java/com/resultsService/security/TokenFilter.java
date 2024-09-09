package com.resultsService.security;


import com.resultsService.exceptions.UnauthorizedException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class TokenFilter extends OncePerRequestFilter {

    private final String adminKey;
    private final String teacherKey;
    private final String studentKey;

    public TokenFilter(String adminKey, String teacherKey,String studentKey) {
        this.adminKey = adminKey;
        this.teacherKey = teacherKey;
        this.studentKey = studentKey;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        String method = request.getMethod();

        String path = request.getRequestURI();
        String[] pathSegments = path.split("/");
        String lastSegment = pathSegments[pathSegments.length - 1];

        if(method.equals("GET")){
            SecurityContextHolder.getContext().setAuthentication(
                    new TokenAuthentication(adminKey, true));
        }
        else{
            if (token != null && (token.equals(studentKey))) {
                SecurityContextHolder.getContext().setAuthentication(
                        new TokenAuthentication(token, true));
            } else {
                throw new UnauthorizedException("Invalid token provided");
            }
        }
        filterChain.doFilter(request, response);
    }
}

