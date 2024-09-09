package quiz_service.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import quiz_service.exceptions.UnauthorizedException;

import java.io.IOException;
import java.util.Objects;

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
            if (token != null && (token.equals(adminKey) || token.equals(teacherKey)) || (lastSegment.equals("registration") && Objects.equals(token, studentKey))) {
                SecurityContextHolder.getContext().setAuthentication(
                        new TokenAuthentication(token, true));
            } else {
                throw new UnauthorizedException("Invalid token provided");
            }
        }
        filterChain.doFilter(request, response);
    }
}
