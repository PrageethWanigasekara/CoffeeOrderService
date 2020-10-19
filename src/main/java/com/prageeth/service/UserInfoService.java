package com.prageeth.service;

import com.prageeth.dto.AuthenticateRequestDTO;
import com.prageeth.dto.UserDTO;
import com.prageeth.entity.UserInfo;
import com.prageeth.exception.AuthException;
import com.prageeth.exception.ExistingResourceException;
import com.prageeth.exception.BadRequestDataException;

public interface UserInfoService {

    UserInfo save(UserDTO userDTO) throws ExistingResourceException;

    UserInfo findUser(String userName) throws BadRequestDataException;

    UserInfo validateUser(AuthenticateRequestDTO authenticateRequestDTO) throws AuthException;
}
