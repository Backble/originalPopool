package kr.co.memberservice.infra.interceptor;

import kr.co.memberservice.infra.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final JwtProvider jwtProvider;

    //To do...
}
