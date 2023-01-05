package dev.eduardova.jwt.components;

import dev.eduardova.jwt.exceptions.UnauthenticatedException;
import dev.eduardova.jwt.dtos.utils.SimpleMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    @Value("${configs.security.uri-access-token:/auth/authenticate}")
    private String uriAccessToken;

    @Value("${server.servlet.contextPath}")
    private String prefix;

    @Value("${configs.security.uri-refresh-token:/auth/refresh-token}")
    private String uriRefreshToken;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            var uri = request.getRequestURI();
            if (isPublic(uri) && request.getHeader(HttpHeaders.AUTHORIZATION) == null) {
                throw new UnauthenticatedException("Token required");
            }
            filterChain.doFilter(request, response);
        }
        catch (UnauthenticatedException e) {
            log.error(e.getMessage());
            setErrorResponse(HttpStatus.UNAUTHORIZED, response, e.getMessage());
        }
        catch (ExpiredJwtException e) {
            log.error(e.getMessage());
            setErrorResponse(HttpStatus.UNAUTHORIZED, response, "Expired token");
        }
        catch (UnsupportedJwtException e) {
            log.error(e.getMessage());
            setErrorResponse(HttpStatus.BAD_REQUEST, response, "Unsupported Token");
        }
        catch (MalformedJwtException e) {
            log.error(e.getMessage());
            setErrorResponse(HttpStatus.BAD_REQUEST, response, "Malformed token");
        }
        catch (SignatureException e) {
            log.error(e.getMessage());
            setErrorResponse(HttpStatus.BAD_REQUEST, response, "Ivalid token");
        }
        catch (JwtException e) {
            log.error(e.getMessage());
            setErrorResponse(HttpStatus.BAD_REQUEST, response, e.getMessage());
        }
        catch (RuntimeException e) {
            log.error(e.getMessage());
            setErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, response, "Internal error");
        }
    }

    private boolean isPublic(String uri) {
        var pettern = Objects.requireNonNullElse(prefix, "") + "%s";
        return !uri.equals(pettern.formatted(uriAccessToken)) && !uri.equals(pettern.formatted(uriRefreshToken));
    }

    public void setErrorResponse(HttpStatus status, HttpServletResponse response, String message) {
        var simpleMessage = new SimpleMessage(message);
        try {
            var msg = objectMapper.writeValueAsString(simpleMessage);
            response.getWriter().write(msg);
            response.setStatus(status.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        }
        catch (IOException e) {
            log.error(e.getMessage());
        }
    }

}
