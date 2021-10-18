package com.vehicle_survey.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

public class TokenUtils {
    //过期时间
    private static final long EXPIRE_TIME = 60 * 60 * 1000 * 24 * 30;//默认一天
    //私钥
    private static final String TOKEN_SECRET = "privateKey";

    /**
     * 生成签名，15分钟过期
     *
     * @return
     */
    public static String createToken(String no) {
        try {
            // 设置过期时间
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            // 私钥和加密算法
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            // 设置头部信息
            Map<String, Object> header = new HashMap<>(2);
            header.put("Type", "Jwt");
            header.put("alg", "HS256");
            // 返回token字符串
            return JWT.create()
                    .withHeader(header)
                    .withClaim("no", no)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 生成token，自定义过期时间 毫秒
     *
     * @param **username**
     * @param **password**
     * @return
     */
    public static String createToken(String userId, long expireDate) {
        try {
            // 设置过期时间
            Date date = new Date(System.currentTimeMillis() + expireDate);
            // 私钥和加密算法
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            // 设置头部信息
            Map<String, Object> header = new HashMap<>(2);
            header.put("Type", "Jwt");
            header.put("alg", "HS256");
            // 返回token字符串
            return JWT.create()
                    .withHeader(header)
                    .withClaim("userId", userId)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 检验token是否正确
     *
     * @param **token**
     * @return
     */
    public static String verifyToken(String token) {
        String no;
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            no = jwt.getClaim("no").asString();
            System.out.println(no);
            return no;
        } catch (TokenExpiredException e) {
            e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
    public static void main(String[] args) {
        System.out.println(createToken("2000303308"));
        System.out.println(verifyToken("eyJUeXBlIjoiSnd0IiwidHlwIjoiSldUIiwiYWxnIjoiSFMyNTYifQ.eyJubyI6IjIwMDAzMDMzMDgiLCJleHAiOjE2MzE1NzEwMTF9.m6Hxwt_yC5vQCmX3AbG_OszBgWWJsX3jFO3kzFyLup0"));
    }
     */
}
