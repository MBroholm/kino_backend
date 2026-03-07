package org.example.kino_backend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
//OncePerRequestFilter is a filter that is executed only once per request(Duh)
//All request go through this filter before reaching our controller ensuring valid auths and security
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, //Incoming HTTP request
                                    HttpServletResponse response, //outgoing HTTP response
                                    FilterChain filterChain) // Security chain where each link filters the rules
                                                            // set in security config.
        throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        //Reading the token from the header
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            //Validating the token, creating auth object, storing it in the security context
            if (jwtUtil.validateToken(token)) {
                String username = jwtUtil.getUsernameFromToken(token);
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(username, null, List.of());
                //List.of is set instead of null due to a quirk in spring sec. it would treat null as not authenticated.
                //Its just an empty list that could be used for access roles later(Admin, owner, clerk etc.)
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
            filterChain.doFilter(request, response); //this must always be called at the end, even if auth fails
                                                    //otherwise the request never reaches the controller
    }
}
