package br.com.vetta.viridis.security.jwt;

import br.com.vetta.viridis.model.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class JwtService {

    @Value("${security.jwt.expiracao}")
    private String expiration;

    @Value("${security.jwt.chave-assinatura}")
    private String keySignature;

    public String generateToken( Users users){
        long expString = Long.valueOf(expiration);
        LocalDateTime dateHourExpiration = LocalDateTime.now().plusMinutes(expString);
        Instant instant = dateHourExpiration.atZone(ZoneId.systemDefault()).toInstant();
        Date data = Date.from(instant);

        return Jwts
                .builder()
                .setSubject(users.getLogin())
                .setExpiration(data)
                .signWith( SignatureAlgorithm.HS512, keySignature )
                .compact();
    }

    private Claims getClaims( String token ) throws ExpiredJwtException {
        return Jwts
                 .parser()
                 .setSigningKey(keySignature)
                 .parseClaimsJws(token)
                 .getBody();
    }

    public boolean tokenValid( String token ){
        try{
            Claims claims = getClaims(token);
            Date dateExpiration = claims.getExpiration();
            LocalDateTime date =
                    dateExpiration.toInstant()
                            .atZone(ZoneId.systemDefault()).toLocalDateTime();
            return !LocalDateTime.now().isAfter(date);
        }catch (Exception e){
            return false;
        }
    }

    public String getLoginUser(String token) throws ExpiredJwtException{
        return (String) getClaims(token).getSubject();
    }
}
