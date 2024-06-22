package Goweb.FormMaker.security;

import Goweb.FormMaker.domain.user.User;
import Goweb.FormMaker.exception.AppException;
import Goweb.FormMaker.exception.error.AuthErrorCode;
import Goweb.FormMaker.service.user.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component // 스프링 컴포넌트로 등록
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자를 만듦
public class JwtFilter extends OncePerRequestFilter { // 요청당 한 번 수행되는 필터

    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String TOKEN_DELIMITER = " ";
    private static final int TOKEN_INDEX = 1;

    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException
    {
        System.out.println("필터 체인 "+filterChain);
        try {

            // 요청 URI를 확인하여 특정 경로의 요청은 필터링하지 않음
            String requestURI = request.getRequestURI();
            if (requestURI.startsWith("/auths/") ||
                    requestURI.startsWith("/image/") ||
                    requestURI.startsWith("/swagger-ui") || // Swagger UI
                    requestURI.startsWith("/v3/api-docs/swagger-config") ||     // Swagger API docs
                    requestURI.startsWith("/swagger-resources") || // Swagger resources
                    requestURI.startsWith("/v3/api-docs")||
                    requestURI.startsWith("/webjars/")) {          // Swagger webjars
                filterChain.doFilter(request, response);
                System.out.println("필터링 안하는 페이지");
                return;
            }
            // Authorization 헤더의 유효성을 확인
            String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (isInValidHeader(authorization)) {
                throw new AppException(AuthErrorCode.INVALID_TOKEN);
            }

            // 토큰에서 사용자 ID 추출하여 사용자 정보 조회
            String token = authorization.split(TOKEN_DELIMITER)[TOKEN_INDEX];
            if (JwtUtil.isExpired(token)) {
                throw new AppException(AuthErrorCode.INVALID_TOKEN);
            }

            // 사용자 정보 기반으로 인증된 사용자의 권한 설정
            Long userId = JwtUtil.getUserIdFromToken(token);
            System.out.println("여긴가");
            User user = userService.findUserById(userId);
            System.out.println("아님 여긴가");
            System.out.println(user.toString());

            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getUserType().name()));
            System.out.println(authorities);

            // Spring Security 컨텍스트에 인증 정보 등록
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userId, null, authorities);
            System.out.println(authenticationToken);
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            filterChain.doFilter(request, response);
            System.out.println("request : " + request+"/n response : "+response);
        } catch (AppException e) {
            // 예외 발생시 응답 상태와 메시지 설정
            response.setStatus(e.getErrorCode().getHttpStatus().value());
            response.getWriter().write(e.getErrorCode().getMessage());
        }
    }

    // Authorization 헤더가 유효한지 확인하는 메소드
    private static boolean isInValidHeader(String authorization) {
        System.out.println("토큰 확인 : "+authorization);
        return authorization == null || !authorization.startsWith(TOKEN_PREFIX);
    }
}