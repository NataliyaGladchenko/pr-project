package com.example.prproject.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.example.prproject.controllers.BaseController.API_URL;

@RequestMapping(value = API_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class BaseController {

    public final static String EXTENSION_URL = "/**";
    public final static String API_URL = "/api";
    public final static String JWT_URL = "/jwt";
    public final static String AUTH_URL = "/authorize";
    public final static String REGISTER_URL = "/register";
    public final static String USERS_URL = "/users";

    public final static String USER_ID_URL = "/{userId}";

    public final static String ADMIN_URL = "/admin";


    @Value("${spring.data.web.pageable.default-page-number}")
    protected int defaultPageNumber;

    @Value("${spring.data.web.pageable.default-page-size}")
    protected int defaultPageSize;


}
