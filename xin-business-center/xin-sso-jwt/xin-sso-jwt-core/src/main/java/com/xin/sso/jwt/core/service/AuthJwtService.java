package com.xin.sso.jwt.core.service;

import com.xin.sso.jwt.entity.JwtParam;
import com.xin.sso.jwt.entity.JwtUserInfo;

public interface AuthJwtService {

    String generateToken(JwtUserInfo jwtUserInfo) throws Exception ;

    String generateToken(JwtUserInfo jwtUserInfo,Long ttlMillis) throws Exception ;

    String generateToken(JwtUserInfo jwtUserInfo, JwtParam jwtParam) throws Exception ;

    String refreshToken(String token);

    Boolean verifyToken(String token);

    JwtUserInfo getUserInfo(String token) throws Exception ;

    void logoutToken(String token) throws Exception ;
}
