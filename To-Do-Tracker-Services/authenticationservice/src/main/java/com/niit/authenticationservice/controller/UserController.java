package com.niit.authenticationservice.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.niit.authenticationservice.exception.UserAlreadyExistException;
import com.niit.authenticationservice.exception.UserNotExistException;
import com.niit.authenticationservice.model.User;
import com.niit.authenticationservice.security.JwtSecurityToken;
import com.niit.authenticationservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/niit/user/auth")
@CrossOrigin
public class UserController {
    UserService userService;
    JwtSecurityToken securityToken;
    @Autowired
    public UserController(UserService userService,JwtSecurityToken securityToken) {
        this.userService = userService;
        this.securityToken = securityToken;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> addingUserData(@RequestBody User user) throws UserAlreadyExistException {
        try {
            return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
        } catch (UserAlreadyExistException e) {
            throw new UserAlreadyExistException();
        } catch (Exception e) {
            System.out.println(e.toString()+"aouth service error");
            return new ResponseEntity<>("please try after some times",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @HystrixCommand(fallbackMethod = "fallBackUserLogin")
    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "5000")
    @PostMapping("/login")
    public ResponseEntity<?> findByUserEmailAndPassword(@RequestBody User user) throws UserNotExistException {
        Map<String,String> map=null;
        try {
            User user1= userService.loginUser(user.getEmailId(),user.getUserPassword());
            if(user1!=null){
               map=securityToken.createUserToken(user);
            }
            return new ResponseEntity<>(map,HttpStatus.OK);
        }
        catch (UserNotExistException ex){
            throw new UserNotExistException();
        }
        catch (Exception ex){
            return new ResponseEntity<>("Please try after sometime",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{emailId}")
    public ResponseEntity<?> updateUser(@PathVariable String emailId, @RequestBody User user) throws UserNotExistException {
        try {
            return new ResponseEntity<>(userService.updateUserData(emailId,user), HttpStatus.CREATED);
        }
        catch (UserNotExistException ex){
            throw new UserNotExistException();
        }catch (Exception e) {
            return new ResponseEntity<>("please try after some times",HttpStatus.CONFLICT);
        }
    }

    ResponseEntity<?> fallBackUserLogin(User user){
        return new ResponseEntity<>("login fail try after some time",HttpStatus.GATEWAY_TIMEOUT);
    }
}
