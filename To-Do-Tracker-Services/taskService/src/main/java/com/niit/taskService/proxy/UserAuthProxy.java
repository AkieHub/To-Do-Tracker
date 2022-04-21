package com.niit.taskService.proxy;

import com.niit.taskService.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "user-auth-service",url = "localhost:8083")
public interface UserAuthProxy {
    @PostMapping("/niit/user/auth/signin")
    ResponseEntity<?> saveUser(@RequestBody User user);
    @PutMapping("/niit/user/auth/update/{emailId}")
     ResponseEntity<?> updateUser(@PathVariable String emailId, @RequestBody User user);
}
