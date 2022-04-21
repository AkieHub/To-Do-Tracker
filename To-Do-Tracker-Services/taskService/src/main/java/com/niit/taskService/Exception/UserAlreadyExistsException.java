package com.niit.taskService.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT,reason = "User Already exists")
public class UserAlreadyExistsException extends Exception{
}
