package com.fibikky.vehicle.common.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

/**
 * 基于jwt的Token生成及校验服务
 *
 * @author Aminor_z
 */
public class JwtRestService {
    /**
     * seconds
     */
    private final long expire = 86400;
    Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final String issuer = "intelligent_vehicle_detection_system";

    public String generate(String session, int uid) {
        Date nowDate = new Date();
        //过期时间
        Date expireDate = new Date(nowDate.getTime() + expire * 1000);
        return Jwts.builder().setIssuer(issuer).setSubject(session).setExpiration(expireDate).signWith(key).compact();
    }

    private Claims getTokenClaim(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean verify(String session, String token) {
        var claims = getTokenClaim(token);
        if (claims == null) {
            return false;
        } else {
            return claims.getExpiration().after(new Date()) && getTokenClaim(token).getSubject().equals(session) && claims.getIssuer().equals(issuer);
        }
    }
}
