package com.authentifcation.projectpitwo.configuration;

import com.authentifcation.projectpitwo.service.JwtService;
import com.authentifcation.projectpitwo.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private JwtService jwtService;
    Claims claims = null;
    private String username = null;
    private Integer id =null;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader("Authorization");

        String username = null;
        String jwtToken = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtUtil.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                System.out.println("JWT Token has expired");
            }
        } else {
            System.out.println("JWT token does not start with Bearer");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = jwtService.loadUserByUsername(username);

            if (jwtUtil.validateToken(jwtToken, userDetails)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);

    }
    public boolean isAdmin() {
        // Get the current authentication object from the SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Check if authentication object is not null and user has the "ROLE_ADMIN" authority
        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(authority -> "ROLE_ADMIN".equalsIgnoreCase(authority.getAuthority()))) {
            return true;
        }

        return false;
    }
    public String getCurrentUsername() {
        return username;
    }
    public String getCurrentid() {
        // Retrieve the authentication object from the security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Check if authentication is not null and contains principal details
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            // Cast the principal to UserDetails to access user details
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // Assuming the UserDetails implementation has a method to return user ID
            return userDetails.getUsername(); // Adjust this line according to your UserDetails implementation
        } else {
            // Return null if authentication or principal is null or does not contain user details
            return null;
        }
    }



}
