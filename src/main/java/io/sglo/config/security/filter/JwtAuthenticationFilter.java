package io.sglo.config.security.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final CustomUserDetailsService userDetailsService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.debug("JwtAuthenticationFilter 진행");
        extractToken(request).map(userDetailsService::loadUserByUsername).ifPresent(this::setAuthentication);
        chain.doFilter(request, response);
    }

    private Optional<String> extractToken(ServletRequest request) {
        log.debug("토큰 추출");
        return Optional.ofNullable(((HttpServletRequest)request).getHeader("Authorization"));
    }

    private void setAuthentication(CustomUserDetails userDetails) {
        log.debug("ContextHolder 에 유저 저장");
        SecurityContextHolder.getContext().setAuthentication(new CustomAuthenticationToken(userDetails, userDetails.getAuthorities()));
    }
}