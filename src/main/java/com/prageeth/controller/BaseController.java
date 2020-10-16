package com.prageeth.controller;

import com.prageeth.entity.UserInfo;
import com.prageeth.exception.AuthException;
import com.prageeth.exception.JwtTokenException;
import com.prageeth.exception.ResourceNotFoundException;
import com.prageeth.service.UserInfoService;
import com.prageeth.service.impl.JwtTokenService;
import com.prageeth.utility.ConstantUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

public class BaseController {

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UserInfoService userInfoService;

    protected int checkAuthentication(HttpServletRequest httpServletRequest) throws AuthException, ResourceNotFoundException, JwtTokenException {

        String authorizationHeader = httpServletRequest.getHeader(ConstantUtil.AUTHORIZATION_HEADER);
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new AuthException("No valid header");
        }
        String jwtToken = authorizationHeader.substring(7);
        String userName = jwtTokenService.getUserNameFromToken(jwtToken);
        UserInfo user = userInfoService.findUser(userName);
        if(!jwtTokenService.validateToken(jwtToken, user)){
            throw new JwtTokenException("Token expired");
        }
        return user.getUserId();
    }
}
