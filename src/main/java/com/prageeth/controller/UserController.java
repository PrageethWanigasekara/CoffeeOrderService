package com.prageeth.controller;

import com.prageeth.dto.AuthenticateRequestDTO;
import com.prageeth.dto.AuthenticateResponseDTO;
import com.prageeth.dto.UserDTO;
import com.prageeth.entity.UserInfo;
import com.prageeth.exception.AuthException;
import com.prageeth.service.UserInfoService;
import com.prageeth.service.impl.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private JwtTokenService jwtTokenService;


    @Autowired
    private UserInfoService userInfoService;


    @PostMapping("/register")
    public ResponseEntity<String> addNewUser(@RequestBody UserDTO user) {
        userInfoService.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticateResponseDTO> authenticateUser(@RequestBody AuthenticateRequestDTO request) throws AuthException {
        UserInfo userInfo = userInfoService.validateUser(request);
        return new ResponseEntity(new AuthenticateResponseDTO(jwtTokenService.generateToken(userInfo)),HttpStatus.OK);
    }


}
