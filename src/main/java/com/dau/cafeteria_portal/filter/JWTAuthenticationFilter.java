//package com.dau.cafeteria_portal.filter;
//
//import com.dau.cafeteria_portal.util.JWTUtil;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//public class JWTAuthenticationFilter extends OncePerRequestFilter {
//
//    private final AuthenticationManager authenticationManager;
//    private final JWTUtil jwtUtil;
//
//    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
//        this.authenticationManager = authenticationManager;
//        this.jwtUtil = jwtUtil;
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain filterChain) throws ServletException, IOException {
//
//        // Allow filter to continue for all other routes
//        if (!request.getServletPath().equals("/auth/generate-token")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        // Parse login credentials
//        ObjectMapper objectMapper = new ObjectMapper();
////        LoginRequest loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);
////
////        UsernamePasswordAuthenticationToken authToken =
////                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
////        Authentication authResult = authenticationManager.authenticate(authToken);
//
//        // On successful authentication, generate and return tokens
////        if (authResult.isAuthenticated()) {
////            String username = authResult.getName();
////            String accessToken = jwtUtil.generateToken(username, 1440); //1 day
////            String refreshToken = jwtUtil.generateToken(username, 7 * 24 * 60); // 7 days
//
//            // Set refresh token in HttpOnly cookie
//            Cookie refreshCookie = new Cookie("refreshToken", refreshToken);
//            refreshCookie.setHttpOnly(true);
//            refreshCookie.setSecure(true);
//            refreshCookie.setPath("/auth/refresh-token");
//            refreshCookie.setMaxAge(7 * 24 * 60 * 60); // 7 days
//            response.addCookie(refreshCookie);
//
//            // Return access token as JSON
//            response.setContentType("application/json");
//            response.setCharacterEncoding("UTF-8");
//
//            Map<String, String> tokenMap = new HashMap<>();
//            tokenMap.put("token", accessToken);
//            tokenMap.put("username", username);
//
//            objectMapper.writeValue(response.getWriter(), tokenMap);
//        } else {
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication failed");
//        }
//    }
//}
