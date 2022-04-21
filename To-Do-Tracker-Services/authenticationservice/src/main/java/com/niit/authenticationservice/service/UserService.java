package com.niit.authenticationservice.service;

import com.niit.authenticationservice.exception.UserAlreadyExistException;
import com.niit.authenticationservice.exception.UserNotExistException;
import com.niit.authenticationservice.model.User;

import java.util.Map;

public interface UserService {
   User saveUser(User user) throws UserAlreadyExistException;
    User loginUser(String emailId,String userPassword) throws UserNotExistException;
    User updateUserData(String emailId,User user1) throws UserNotExistException;
}
