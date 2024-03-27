package com.traveloper.tourfinder.common.exception;

import com.traveloper.tourfinder.auth.jwt.JwtTokenUtils;
import com.traveloper.tourfinder.common.RedisRepo;
import com.traveloper.tourfinder.common.config.PermitAllPath;
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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
@Slf4j
public class CustomJwtExceptionFilter extends OncePerRequestFilter {
    private final JwtTokenUtils jwtTokenUtils;
    private final PermitAllPath permitAllPath;
    // ExceptionFilter에서 검증하지 않고 넘길 url정의
    private final AntPathMatcher pathMatcher = new AntPathMatcher();
    private final RedisRepo redisRepo;

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {





        // permitAllPath에 포함된 경로 중에서 현재 요청 URI와 매치되는지 확인
        for (String path : permitAllPath.getPERMIT_ALL_PATTERN()) {
            System.out.println(path + " and " + request.getContextPath());
            if (new AntPathRequestMatcher(path).matches(request)) {
                filterChain.doFilter(request, response);
                return;
            }
        }




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

            System.out.println(redisRepo.getRefreshToken(token).isEmpty() + "토큰이 있는지 체크");
                if(redisRepo.getRefreshToken(token).isEmpty()){
                    log.info("만료되었거나, 삭제 처리된 토큰");
                    setErrorResponse(CustomGlobalErrorCode.TOKEN_EXPIRED,response);
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


