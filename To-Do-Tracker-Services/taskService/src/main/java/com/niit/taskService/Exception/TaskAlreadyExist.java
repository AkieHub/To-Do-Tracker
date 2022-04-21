package com.niit.taskService.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT,reason = "Task already exixst in list")
public class TaskAlreadyExist extends Exception{
}
