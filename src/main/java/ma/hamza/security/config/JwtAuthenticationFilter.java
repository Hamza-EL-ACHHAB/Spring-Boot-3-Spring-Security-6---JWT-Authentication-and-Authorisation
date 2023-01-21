package ma.hamza.security.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
@RequiredArgsConstructor /*create constructor using any final field*/
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    /*Cheking and extracting the jwt token from the request header*/
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request
            ,@NonNull HttpServletResponse response,@NonNull FilterChain filterChain)
            throws ServletException, IOException {
        /*Checking if we have the JWT token*/
        final String authHeader = request.getHeader("Authorization");/*when we make a call we need to pass the
        JWT authentication token within the header, so we will extract the header that contains the JWT token*/
        final String jwt;
        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);/*pass the req and resp to the next filter*/
            return; /*finish the execution of this method*/
        }
        /*Extracting the token from the header*/
        jwt = authHeader.substring(7);
    }
}
