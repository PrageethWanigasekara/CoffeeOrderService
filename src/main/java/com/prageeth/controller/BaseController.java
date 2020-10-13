package com.prageeth.controller;

import com.prageeth.utility.ConstantUtil;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;

public class BaseController {

    protected void checkAuthentication(HttpServletRequest httpServletRequest){

        String apiHeader = httpServletRequest.getHeader(ConstantUtil.API_HEADER);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(ConstantUtil.API_HEADER,apiHeader);
        //TODO call for a service to user authentication

    }
}
