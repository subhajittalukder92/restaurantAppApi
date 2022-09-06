package com.restaurant.restaurant.jwtsecurity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.restaurant.restaurant.exception.TokenException;
import com.restaurant.restaurant.payload.ApiErrorResponse;
import com.restaurant.restaurant.service.JwtUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
// Filters happens before controllers are even resolved so exceptions
// thrown from filters can't be caught by a Controller Advice.
//   Filters are a part of the servlet and not really the MVC stack.
@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, TokenException {
        final String requestTokenHeader = request.getHeader("Authorization");
        String username = null;
        String jwtToken = null;
        String errMsg = "";
        String requestedUrl = request.getRequestURI();
        String urlsTobeExclude[] = {"/api/auth/signup","/api/auth/signin"};

        // JWT Token is in the form "Bearer token". Remove Bearer word and get
        // only the Token
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);

            try {
                username = jwtTokenProvider.getUserNameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
                errMsg = "Unable to get JWT Token" ;

            } catch (ExpiredJwtException e) {
                System.out.println("JWT Token has expired");
                errMsg = "JWT Token has expired";

            } catch (UnsupportedJwtException e){
                System.out.println("Unsupported JWT token");
                errMsg = "Unsupported JWT token";
            }catch (MalformedJwtException e){
                System.out.println("Invalid JWT token");
                errMsg = "Invalid JWT token";
            }
            catch (SignatureException e ){
                errMsg = "Invalid JWT signature";
                System.out.println("Invalid JWT signature");
            }
        } else {
            logger.warn("JWT Token does not begin with Bearer String");
            errMsg = "JWT Token does not begin with Bearer String";
            System.out.println("JWT Token does not begin with Bearer String");
        }
        if(!Arrays.asList(urlsTobeExclude).contains(requestedUrl) && !errMsg.isEmpty()){
            System.out.println(!Arrays.asList(urlsTobeExclude).contains(requestedUrl));
            ObjectMapper objectMapper = new ObjectMapper();
            List<String> errors = new ArrayList<>();
            errors.add(errMsg);
            ApiErrorResponse apiErrorResponse = new ApiErrorResponse(LocalDateTime.now(), HttpStatus.UNAUTHORIZED,"Token problem.",errors );
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(objectMapper.writeValueAsString(apiErrorResponse));
            return;
        }

        // Once we get the token validate it.
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            CustomUserDetails userDetails = (CustomUserDetails) this.jwtUserDetailsService.loadUserByUsername(username);

            // if token is valid configure Spring Security to manually set
            // authentication
            if (jwtTokenProvider.validateToken(jwtToken, userDetails)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // After setting the Authentication in the context, we specify
                // that the current user is authenticated. So it passes the
                // Spring Security Configurations successfully.
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }

}
