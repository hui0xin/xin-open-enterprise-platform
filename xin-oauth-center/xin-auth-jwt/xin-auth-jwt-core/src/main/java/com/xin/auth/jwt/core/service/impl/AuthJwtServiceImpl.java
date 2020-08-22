package com.xin.auth.jwt.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xin.auth.jwt.common.errorcode.AuthJwtErrorCode;
import com.xin.auth.jwt.common.exception.AuthJwtException;
import com.xin.auth.jwt.common.utils.JwtTokenUtil;
import com.xin.auth.jwt.core.service.AuthJwtService;
import com.xin.auth.jwt.entity.JwtParam;
import com.xin.auth.jwt.entity.JwtUserInfo;
import com.xin.commons.redis.service.RedisService;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;


/**
 * @ClassName: AuthJwtServiceImpl
 * @author: xin
 * 鉴权 业务处理类
 */
@Slf4j
@Service(value = "authJwtService")
public class AuthJwtServiceImpl implements AuthJwtService {

    /**
     * 注意redis的key的命名方式，"：" 这样容易维护
     */
    private static final String REIDSUSERKEY = "xin:auth:jwt:loginuser:";

    @Autowired
    private RedisService redisService;


    @Override
    public String generateToken(JwtUserInfo jwtUserInfo) throws Exception {
        JwtParam jwtParam = null;
        return generateToken(jwtUserInfo, jwtParam);
    }

    @Override
    public String generateToken(JwtUserInfo jwtUserInfo, Long ttlMillis) throws Exception {
        JwtParam jwtParam = new JwtParam();
        jwtParam.setTtlMillis(ttlMillis);
        return generateToken(jwtUserInfo, jwtParam);
    }

    /**
     * 生成token
     *
     * @param jwtUserInfo
     * @param jwtParam
     * @return
     */
    @Override
    public String generateToken(JwtUserInfo jwtUserInfo, JwtParam jwtParam) throws Exception {
        if (ObjectUtils.isEmpty(jwtUserInfo)) {
            log.error("AuthJwtServiceImpl.generateToken1 jwtUserInfo is null");
            throw new AuthJwtException(AuthJwtErrorCode.USER_INFO_IS_NULL);
        }
        String id = UUID.randomUUID().toString();
        JSONObject object = (JSONObject) JSONObject.toJSON(jwtUserInfo);

        String issuer = null;
        Long ttlMillis = null;
        if (!ObjectUtils.isEmpty(jwtParam)) {
            id = jwtParam.getId();
            issuer = jwtParam.getIssuer();
            ttlMillis = jwtParam.getTtlMillis();
        }
        if (ttlMillis == null) {
            ttlMillis = JwtTokenUtil.EXPIRES_IN;
        }
        //设置用户，存入redis
        redisService.set(REIDSUSERKEY + jwtUserInfo.getUserId(), object.toJSONString(), ttlMillis);
        return JwtTokenUtil.generateToken(id, issuer, object.toJSONString(), ttlMillis);
    }


    /**
     * 刷新token 通过原来的token，解析出生成token的参数，然后生成新的token
     *
     * @param token
     * @return
     * @throws Exception
     */
    @Override
    public String refreshToken(String token) {
        String tokenResult = null;
        if (StringUtils.isBlank(token)) {
            log.error("AuthJwtServiceImpl.refreshToken token is null");
            throw new AuthJwtException(AuthJwtErrorCode.TOKEN_IS_NULL);
        }
        try {
            Claims claims = JwtTokenUtil.parseToken(token);
            String id = claims.getId();
            String issuer = claims.getIssuer();
            String subjectStr = claims.getSubject();

            //旧的结束时间
            String exp = claims.get("exp").toString();
            //旧的开始时间
            String iat = claims.get("iat").toString();
            //旧的有效期
            Long time = Long.parseLong(exp)-Long.parseLong(iat);

            JSONObject subject = JSONObject.parseObject(subjectStr);
            String userId = subject.get("userId").toString();
            redisService.del(REIDSUSERKEY + userId);

            //设置用户，存入redis
            redisService.set(REIDSUSERKEY + userId, subject.toJSONString(), time);
            tokenResult = JwtTokenUtil.generateToken(id, issuer, subjectStr, time);

        } catch (Exception e) {
            log.error("AuthJwtServiceImpl.verifyToken失败：{}", ExceptionUtils.getStackTrace(e));
            throw new AuthJwtException(AuthJwtErrorCode.REFRESH_TOKEN_IS_FAIL);
        }
        return tokenResult;
    }

    /**
     * 验证token有效性
     */
    @Override
    public Boolean verifyToken(String token) {
        Boolean result = false;
        if (StringUtils.isBlank(token)) {
            log.error("AuthJwtServiceImpl.verifyToken token is null");
            throw new AuthJwtException(AuthJwtErrorCode.TOKEN_IS_NULL);
        }
        try {
            Claims claims = JwtTokenUtil.parseToken(token);

            //TODO 结和业务来实现，比如redis查看该用户还在吗

        } catch (Exception e) {
            log.error("AuthJwtServiceImpl.verifyToken失败：{}", ExceptionUtils.getStackTrace(e));
        }
        return result;
    }

    /**
     * 获取用户信息
     * 注意，前提是在生成token的时候，使用userId，userName等相关的用户信息生成的，则可以获取
     *
     * @param token
     * @return
     * @throws Exception
     */
    @Override
    public JwtUserInfo getUserInfo(String token) throws Exception {
        JwtUserInfo jwtUserInfo = null;
        if (StringUtils.isBlank(token)) {
            log.error("AuthJwtServiceImpl.getUserInfo token is null");
            throw new AuthJwtException(AuthJwtErrorCode.TOKEN_IS_NULL);
        }
        try {
            Claims claims = JwtTokenUtil.parseToken(token);
            JSONObject subject = JSONObject.parseObject(claims.getSubject());
            jwtUserInfo = subject.toJavaObject(JwtUserInfo.class);
        } catch (Exception e) {
            log.error("AuthJwtServiceImpl.getUserInfo失败：{}", ExceptionUtils.getStackTrace(e));
            throw new AuthJwtException(AuthJwtErrorCode.GET_USER_INFO_IF_FAIL);
        }
        return jwtUserInfo;
    }

    /**
     * 注销
     * 这里直接从redis中移除该用户就可以了，
     * 因为jwt本身不能做退出，生成的token不能回收
     *
     * @param token
     * @return
     * @throws Exception
     */
    @Override
    public void logoutToken(String token) throws Exception {
        if (StringUtils.isBlank(token)) {
            log.error("AuthJwtServiceImpl.logoutToken token is null");
            throw new AuthJwtException(AuthJwtErrorCode.TOKEN_IS_NULL);
        }
        try {
            Claims claims = JwtTokenUtil.parseToken(token);
            JSONObject subject = JSONObject.parseObject(claims.getSubject());
            Long userId = Long.parseLong(subject.get("userId").toString());
            redisService.del(REIDSUSERKEY + userId);
        } catch (Exception e) {
            log.error("AuthJwtServiceImpl.logoutToken失败：{}", ExceptionUtils.getStackTrace(e));
            throw new AuthJwtException(AuthJwtErrorCode.LOGOUT_TOKEN_IS_FAIL);
        }
    }

}
