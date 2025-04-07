package com.wahak.config;

import com.wahak.service.RiderUserService;
import com.wahak.utils.jwt.RiderJwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @author krishna.meena
 */
@Component
public class RiderAuthenticationFilter extends OncePerRequestFilter {


    private final RiderJwtUtils riderJwtUtils;

    private final RiderUserService userDetailsService;

    public RiderAuthenticationFilter(RiderJwtUtils riderJwtUtils, RiderUserService userDetailsService) {
        this.riderJwtUtils = riderJwtUtils;
        this.userDetailsService = userDetailsService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestPath = request.getServletPath();

        if (requestPath.startsWith("/riders/")) {
            String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }
            String token = authHeader.substring(7);
            String userId = null;
            boolean isValid = false;

            isValid = riderJwtUtils.isTokenValid(token);
            if (isValid) {
                userId = riderJwtUtils.getRiderId(token);
            }

            if (isValid && userId != null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(userId);
                if(userDetails == null) {
                    filterChain.doFilter(request, response);
                    return;
                }
                CustomAuthenticationToken authenticationToken = new CustomAuthenticationToken(userDetails);
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
            filterChain.doFilter(request, response);

        }else {
            filterChain.doFilter(request, response);
            return;
        }

    }
}
