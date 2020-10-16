package com.prageeth.service.impl;

import com.prageeth.dto.AuthenticateRequestDTO;
import com.prageeth.dto.UserDTO;
import com.prageeth.entity.UserInfo;
import com.prageeth.exception.AuthException;
import com.prageeth.exception.ResourceNotFoundException;
import com.prageeth.repository.UserInfoRepository;
import com.prageeth.service.UserInfoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserInfo save(UserDTO userDTO) {
        UserInfo userInfo = modelMapper.map(userDTO, UserInfo.class);
        userInfo.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return userInfoRepository.save(userInfo);
    }

    public UserInfo findUser(String userName) throws ResourceNotFoundException {
        UserInfo user = userInfoRepository.findByUserName(userName);
        if (user == null) {
            throw new ResourceNotFoundException("userName : "+userName);
        }
        return user;
    }

    @Override
    public UserInfo validateUser(AuthenticateRequestDTO authenticateRequestDTO) throws AuthException {
        UserInfo user = userInfoRepository.findByUserName(authenticateRequestDTO.getUserName());
        if(user==null){
            throw new AuthException("No valid user");
        }
        boolean matches = passwordEncoder.matches(authenticateRequestDTO.getPassword(), user.getPassword());
        if(!matches){
            throw new AuthException("User mismatch");
        }
        return user;
    }
}
