package com.niit.ArchieveService.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND,reason = "User is not found")
public class UserNotExistsException extends Exception{
}
