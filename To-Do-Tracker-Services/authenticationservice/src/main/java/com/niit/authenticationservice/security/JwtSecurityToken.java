package com.niit.authenticationservice.security;

import com.niit.authenticationservice.model.User;

import java.util.Map;

public interface JwtSecurityToken {
    Map createUserToken(User user);
}
