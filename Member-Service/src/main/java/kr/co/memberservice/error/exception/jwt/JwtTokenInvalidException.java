package kr.co.memberservice.error.exception.jwt;

import kr.co.memberservice.error.exception.UserDefineException;

public class JwtTokenInvalidException extends UserDefineException {
    public JwtTokenInvalidException(String message) {
        super(message);
    }
}
