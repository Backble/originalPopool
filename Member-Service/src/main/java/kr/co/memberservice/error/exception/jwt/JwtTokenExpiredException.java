package kr.co.memberservice.error.exception.jwt;

import kr.co.memberservice.error.exception.BusinessLogicException;

public class JwtTokenExpiredException extends BusinessLogicException {
    public JwtTokenExpiredException() {
        super("JWT Token Expired");
    }
}
