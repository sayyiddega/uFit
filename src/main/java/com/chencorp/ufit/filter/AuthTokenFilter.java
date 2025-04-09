package com.chencorp.ufit.filter;

import com.chencorp.ufit.model.Token;
import com.chencorp.ufit.repository.TokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private TokenRepository tokenRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String tokenValue = authHeader.substring(7);

            // Log token value untuk debugging
            System.out.println("Token received: " + tokenValue);

            // Cari token di database
            Token token = tokenRepository.findByToken(tokenValue);

            if (token != null && token.getInactive().isAfter(LocalDateTime.now())) {
                // Token valid
                System.out.println("Token is valid. User ID: " + token.getUser().getId());

                // Set authentication context
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        token.getUser().getId(), null, null); // Pasang user ID atau authorities jika ada
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                // Lanjutkan request
                filterChain.doFilter(request, response);
                return;
            } else {
                // Token expired or invalid
                System.out.println("Invalid or expired token.");
            }
        } else {
            System.out.println("Authorization header is missing or incorrect.");
        }

        // Jika token tidak valid atau tidak ada
        response.setStatus(HttpServletResponse.SC_FORBIDDEN); // Ganti dari 401 menjadi 403 jika token tidak valid
        response.getWriter().write("Forbidden: Invalid or expired token");
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // Skip filter untuk login dan register
        String path = request.getServletPath();
        return path.equals("/service/login") || path.equals("/service/register");
    }
}
