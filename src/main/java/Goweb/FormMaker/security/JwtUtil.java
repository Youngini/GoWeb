package Goweb.FormMaker.security;


import Goweb.FormMaker.DTO.auth.LoginSuccessDTO;
import Goweb.FormMaker.domain.user.User;
import Goweb.FormMaker.exception.AppException;
import Goweb.FormMaker.exception.error.AuthErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    private static final Long HOUR = 1000 * 60 * 60L;
    private static final Long MONTH = 30 * 24 * 1000 * 60 * 60L;
    private static String secretKey;

    @Value("${jwt.secret}")
    public void setSecretKey(String secret) {
        JwtUtil.secretKey = secret;
    }

    public static Long getUserIdFromToken(String token) {
        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                    .getBody().get("userId", Long.class);
        } catch (ExpiredJwtException e) {
            throw new AppException(AuthErrorCode.INVALID_TOKEN);
        }
    }

    public static boolean isExpired(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return false;
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    public static LoginSuccessDTO createTokens(User user) {
        String accessToken = createToken(user, HOUR);
        String refreshToken = createToken(user, MONTH);
        return new LoginSuccessDTO(accessToken, refreshToken);
    }

    private static String createToken(User user, Long expiredTime) {
        Claims claims = Jwts.claims();
        claims.put("userId", user.getId());
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}