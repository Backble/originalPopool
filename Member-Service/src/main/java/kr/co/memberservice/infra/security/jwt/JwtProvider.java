package kr.co.memberservice.infra.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import kr.co.memberservice.domain.shared.enums.MemberRole;
import kr.co.memberservice.error.exception.UserDefineException;
import kr.co.memberservice.error.exception.jwt.JwtTokenExpiredException;
import kr.co.memberservice.error.exception.jwt.JwtTokenInvalidException;
import kr.co.memberservice.repository.MemberMstRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtProvider {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    final String MEMBER = "identity";
    private final long ACCESS_EXPIRE = 1000 * 60 * 30;              //30분
    private final long REFRESH_EXPIRE = 1000 * 60 * 60 * 24 * 14;   //2주

    private final MemberMstRepository memberMstRepository;

    /**
     * 시크릿 키를 Base64로 인코딩을 하는 메소드.
     */
    @PostConstruct
    protected void init(){
        SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
    }

    /*
        To do...
            1) 사용자 정보를 통해 Claims 객체를 만들어서 반환하는 메서드
            2) 사용자 정보를 통해 AccessToken 을 만드는 메서드
            3) RefreshToken 을 이용하여 AccessToken 을 만들어내는 메서드
            4) 사용자 정보를 통해 RefreshToken 을 만드는 메서드
     */

    /**
     * Request의 Header에 있는 토큰을 추출하는 메소드
     * 평소 AccessToken을 담아서 주고 받다가, 만료 됐다는 예외 발생 시, 그때 Refresh만.
     * @param request : 사용자의 요청.
     * @return : AccessToken과 RefreshToken을 담은 객체를 Optional로 감싼 데이터.
     */
    public Optional<String> resolveToken(HttpServletRequest request){
        return Optional.ofNullable(request.getHeader("Authorization"));
    }

    /**
     * 키 변환을 위한 key 를 만드는 메서드
     * @return secret key
     */
    private byte[] generateKey(){
        try{
            return SECRET_KEY.getBytes("UTF-8");
        }catch (UnsupportedEncodingException e){
            throw new UserDefineException("키 변환에 실패하였습니다. ", e.getMessage());
        }
    }

    /**
     * 토큰의 유효성을 판단하는 메소드
     * @param token : 토큰
     * @return 토큰이 만료되었는지에 대한 불리언 값
     * @exception ExpiredJwtException 토큰이 만료되었을 경우에 발생하는 예외
     */
    public boolean isUsable(String token){
        try {
            Jwts.parser()
                    .setSigningKey(generateKey())
                    .parseClaimsJws(token);
            return true;
        }catch (SignatureException e){
            log.error("Invalid JWT Signature");
            throw new JwtTokenInvalidException("Invalid JWT Signature");
        }catch (MalformedJwtException e){
            log.error("Invalid JWT token");
            throw new JwtTokenInvalidException("Invalid JWT Token");
        }catch (ExpiredJwtException e){
            log.error("Expired JWT token");
            throw new JwtTokenExpiredException();
        }catch (IllegalArgumentException e){
            log.error("Empty JWT Claims");
            throw new JwtTokenInvalidException("Empty JWT Claims");
        }
    }

    /**
     * 토큰을 이용하여 사용자 아이디를 찾는 메서드
     * @param token 토큰
     * @return 사용자의 아이디
     */
    public String findIdentityByToken(String token){
        return (String) Jwts.parser()
                .setSigningKey(generateKey())
                .parseClaimsJws(token)
                .getBody()
                .get(MEMBER);
    }

    /**
     * 토큰을 이용하여 사용자 권한을 찾는 메서드
     * @param token 토큰
     * @return 사용자의 권한
     */
    public String findRoleByToken(String token){
        return (String) Jwts.parser()
                .setSigningKey(generateKey())
                .parseClaimsJws(token)
                .getBody()
                .get("member_role");
    }

    private Set<? extends GrantedAuthority> getAuthorities(MemberRole role) {
        Set<GrantedAuthority> set = new HashSet<>();

        if(role.equals(MemberRole.ROLE_ADMIN)){
            set.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        set.add(new SimpleGrantedAuthority("ROLE_MEMBER"));

        return set;
    }

    /**
     * 토큰을 통해서 Authentication 객체를 만들어내는 메서드
     * @param token 토큰
     * @return 사용자 정보를 담은 UsernamePasswordAuthenticationToken 객체
     */
    public Authentication getAuthentication(String token){
        UserDetails userDetails =
                new User(findIdentityByToken(token),
                        "",
                        getAuthorities(MemberRole.of(findRoleByToken(token))));

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}
