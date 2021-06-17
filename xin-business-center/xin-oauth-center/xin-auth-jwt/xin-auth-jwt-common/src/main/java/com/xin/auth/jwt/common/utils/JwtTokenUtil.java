package com.xin.auth.jwt.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.util.Date;

/**
 * 描述: jwt 工具类
 * 注意，jwt签发后无法撤回，有效期不宜太长，
 * 比如黑名单
 * @Auther: xin
 */
@Slf4j
public class JwtTokenUtil {

    // 生成jwt的 秘钥明文
    public static final String KEY = "xinjwt";

    //这是jwt生成token的默认有效期 为 ：1000 * 60 * 60 * 24L = 1天
    public static final Long EXPIRES_IN = 1000 * 60 * 60 * 24L;

    //这是jwt生成token的默认签发人
    public static final String ISSUER = "xin";

    /**
     * 由字符串生成签名密钥
     *
     * @return
     */
    public static SecretKey generalKey() {
        byte[] encodedKey = DatatypeConverter.parseBase64Binary(KEY);
        SecretKeySpec key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    /**
     * 创建jwt
     *
     * @param id        jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，
     *                  主要用来作为一次性token,从而回避重放攻击。UUID.randomUUID().toString()
     * @param issuer    jwt签发人 clientId: 098f6bcd4621d373cade4e832627b4f6
     * @param subject   代表这个JWT的主体，这个是一个json格式的字符串，
     * @param ttlMillis token的有效时长
     * @return
     * @throws Exception
     */
    public static String generateToken(String id, String issuer, String subject, Long ttlMillis) throws Exception {

        // 指定签名的时候使用的签名算法，也就是header那部分，jjwt已经将这部分内容封装好了。
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        // 生成JWT的时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        //过期时间
        long expMillis = 0;
        if (ttlMillis == null) {
            expMillis = nowMillis + EXPIRES_IN;
        } else {
            // 设置过期时间
            if (ttlMillis >= 0) {
                expMillis = nowMillis + ttlMillis;
            } else {
                expMillis = nowMillis + EXPIRES_IN;
            }
        }
        if (StringUtils.isBlank(issuer)) {
            issuer = ISSUER;
        }

        // 生成签名的时候使用的秘钥secret，切记这个秘钥不能外露哦。它就是你服务端的私钥，
        // 在任何场景都不应该流露出去。一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了。
        SecretKey key = generalKey();

        // 下面就是在为payload添加各种标准声明和私有声明了
        JwtBuilder builder = Jwts.builder()
                // 设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，
                // 主要用来作为一次性token,从而回避重放攻击。
                .setId(id)
                // iat: jwt的签发时间
                .setIssuedAt(now)
                // issuer：jwt签发人
                .setIssuer(issuer)
                // sub(Subject)：代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，
                // 可以存放什么userid，roldid之类的，作为什么用户的唯一标志。可以是个json
                .setSubject(subject)
                // 设置签名使用的签名算法和签名使用的秘钥
                .signWith(signatureAlgorithm, key)
                // 设置过期时间;
                .setExpiration(new Date(expMillis));
        return builder.compact();
    }


    /**
     * 解密jwt
     * @param token 字符串
     * @return
     * @throws Exception
     */
    public static Claims parseToken(String token) throws Exception {
        //签名秘钥，和生成的签名的秘钥一模一样
        SecretKey key = generalKey();
        Claims claims = Jwts.parser()
                //设置签名的秘钥
                .setSigningKey(key)
                //设置需要解析的jwt
                .parseClaimsJws(token).getBody();
        return claims;
    }

}