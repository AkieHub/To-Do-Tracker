package com.niit.taskService.proxy;

import com.niit.taskService.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-arch-service",url = "localhost:8085")
public interface UserArchiveProxy {
    @PostMapping("/niit/archive/register")
    ResponseEntity<?> saveUser(@RequestBody User user);
}
