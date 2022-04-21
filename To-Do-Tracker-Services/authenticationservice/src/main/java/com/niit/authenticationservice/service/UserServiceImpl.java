package com.niit.authenticationservice.service;

import com.niit.authenticationservice.exception.UserAlreadyExistException;
import com.niit.authenticationservice.exception.UserNotExistException;
import com.niit.authenticationservice.model.User;
import com.niit.authenticationservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User saveUser(User user) throws UserAlreadyExistException {
        if (userRepository.findById(user.getEmailId()).isPresent()){
            throw new UserAlreadyExistException();
        }
        return userRepository.save(user);
    }

    @Override
    public User loginUser(String emailId,String userPassword) throws  UserNotExistException {
       User user= userRepository.findByEmailIdAndUserPassword(emailId,userPassword);
        if (user==null){
            throw new UserNotExistException();
        }
        return user;
    }

    @Override
    public User updateUserData(String emailId,User user1) throws UserNotExistException {
        if (userRepository.findById(emailId).isEmpty()){
            throw new UserNotExistException();
        }
        User user =userRepository.findById(emailId).get();
        user.setUserPassword(user1.getUserPassword());
        userRepository.save(user);
        return user;
    }

}
