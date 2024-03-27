package com.traveloper.tourfinder.common.exception;

import com.traveloper.tourfinder.auth.jwt.JwtTokenUtils;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Getter
@AllArgsConstructor
@Slf4j
public class CustomJwtExceptionFilter extends OncePerRequestFilter {
    private final JwtTokenUtils jwtTokenUtils;
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {


        try {
                log.info("필터 시작");
                String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

                if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                    log.info("토큰이 없거나 Bearer 타입이 아님");
                    setErrorResponse(CustomGlobalErrorCode.MALFORMED_TOKEN, response);
                    return;
                }

                String token = authHeader.substring(7); // "Bearer " 다음부터 토큰 추출
                if (token.isEmpty()) {
                    setErrorResponse(CustomGlobalErrorCode.TOKEN_EMPTY, response);
                    return;
                }

                if (!jwtTokenUtils.validate(token)) {
                    log.info("올바르지 않은 토큰");
                    setErrorResponse(CustomGlobalErrorCode.MALFORMED_TOKEN, response);
                    return;
                }

            filterChain.doFilter(request, response);
        } catch (IllegalArgumentException e){
            setErrorResponse(CustomGlobalErrorCode.TOKEN_EMPTY, response);
        }
        catch (ExpiredJwtException e) {
            setErrorResponse(CustomGlobalErrorCode.TOKEN_EXPIRED, response);
        } catch (UnsupportedJwtException e) {
            setErrorResponse(CustomGlobalErrorCode.UNSUPPORTED_TOKEN, response);
        } catch (MalformedJwtException e) {
            setErrorResponse(CustomGlobalErrorCode.MALFORMED_TOKEN, response);
        } catch (SignatureException e) {
            setErrorResponse(CustomGlobalErrorCode.SIGNATURE_INVALID, response);
        } catch (JwtException e) {
            setErrorResponse(CustomGlobalErrorCode.TOKEN_EMPTY, response); // Or a general JWT error enum value
        }
    }

    private void setErrorResponse(CustomGlobalErrorCode error, HttpServletResponse response) throws IOException {
        response.setStatus(error.getStatus());
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(convertToJson(error.getCode(), error.getMessage()));
    }

    private String convertToJson(String code, String message) {
        return "{\"code\": \"" + code + "\", \"message\": \"" + message + "\"}";
    }
}


