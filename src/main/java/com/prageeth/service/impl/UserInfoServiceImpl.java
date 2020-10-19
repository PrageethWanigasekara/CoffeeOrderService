package com.prageeth.service.impl;

import com.prageeth.dto.AuthenticateRequestDTO;
import com.prageeth.dto.UserDTO;
import com.prageeth.entity.UserInfo;
import com.prageeth.exception.AuthException;
import com.prageeth.exception.ExistingResourceException;
import com.prageeth.exception.BadRequestDataException;
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

    @Override
    public UserInfo save(UserDTO userDTO) throws ExistingResourceException {
        UserInfo user = userInfoRepository.findByUserName(userDTO.getUserName());
        if(user!=null){
            throw new ExistingResourceException("Username : "+userDTO.getUserName());
        }
        UserInfo userInfo = modelMapper.map(userDTO, UserInfo.class);
        userInfo.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return userInfoRepository.save(userInfo);
    }

    @Override
    public UserInfo findUser(String userName) throws BadRequestDataException {
        UserInfo user = userInfoRepository.findByUserName(userName);
        if (user == null) {
            throw new BadRequestDataException("userName : "+userName);
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
