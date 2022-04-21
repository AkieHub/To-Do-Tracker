package com.niit.authenticationservice.security;

import com.niit.authenticationservice.exception.UserNotExistException;
import com.niit.authenticationservice.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtSecurityTokenImpl implements JwtSecurityToken {
    @Override
    public Map createUserToken(User user){ //generate token for user with hs256 algo
        String jwtTokenPass= Jwts.builder().setSubject(user.getEmailId())
                .setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS256,"userPass")
                .compact();
        Map<String,String> tokenMap=new HashMap<>();
        tokenMap.put("token",jwtTokenPass);
        tokenMap.put("message","User successfully logged in");
        return tokenMap;
    }
}
