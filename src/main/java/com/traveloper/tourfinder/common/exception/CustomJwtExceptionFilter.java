package com.traveloper.tourfinder.common.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Getter
@AllArgsConstructor
public class CustomJwtExceptionFilter extends OncePerRequestFilter {

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            setErrorResponse(CustomGlobalErrorCode.TOKEN_EXPIRED, response);
        } catch (UnsupportedJwtException e) {
            setErrorResponse(CustomGlobalErrorCode.UNSUPPORTED_TOKEN, response);
        } catch (MalformedJwtException e) {
            setErrorResponse(CustomGlobalErrorCode.MALFORMED_TOKEN, response);
        } catch (SignatureException e) {
            setErrorResponse(CustomGlobalErrorCode.SIGNATURE_INVALID, response);
        } catch (IllegalArgumentException e) {
            setErrorResponse(CustomGlobalErrorCode.TOKEN_EMPTY, response);
        } catch (JwtException e) {
            setErrorResponse(CustomGlobalErrorCode.TOKEN_EMPTY, response); // Or a general JWT error enum value
        }
    }

    private void setErrorResponse(CustomGlobalErrorCode error, HttpServletResponse response) throws IOException {
        response.setStatus(error.getStatus());
        response.setContentType("application/json");
        response.getWriter().write(convertToJson(error.getCode(), error.getMessage()));
    }

    private String convertToJson(String code, String message) {
        return "{\"status\": \"" + code + "\", \"error\": \"" + message + "\"}";
    }
}


