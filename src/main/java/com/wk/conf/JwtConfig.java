package com.wk.conf;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@ConfigurationProperties(prefix = "config.jwt")
public class JwtConfig {

    private String secret;
    private long expire;
    private String header;

    public String createToken(String subject){
        Date nowDate = new Date();
        Date expireDate = new Date(nowDate.getTime() + expire * 1000);
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(subject)
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.ES512, secret)
                .compact();
    }

    private Claims getTokenClaims(String token){
        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        }catch (Exception e){
            return null;
        }
    }

    //token是否过期
    public boolean isTokenExpired(String token){
        Claims claims = getTokenClaims(token);
        if(claims!=null){
            return claims.getExpiration().before(new Date());
        }
        return true;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public long getExpire() {
        return expire;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }
}
