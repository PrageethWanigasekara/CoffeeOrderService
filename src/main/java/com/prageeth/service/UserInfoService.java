package com.prageeth.service;

import com.prageeth.dto.AuthenticateRequestDTO;
import com.prageeth.dto.UserDTO;
import com.prageeth.entity.UserInfo;
import com.prageeth.exception.AuthException;
import com.prageeth.exception.ResourceNotFoundException;

public interface UserInfoService {

    UserInfo save(UserDTO userDTO);

    UserInfo findUser(String userName) throws ResourceNotFoundException;

    UserInfo validateUser(AuthenticateRequestDTO authenticateRequestDTO) throws AuthException;
}
