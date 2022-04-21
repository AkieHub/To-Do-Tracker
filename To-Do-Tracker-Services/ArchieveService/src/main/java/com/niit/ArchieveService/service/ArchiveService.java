package com.niit.ArchieveService.service;

import com.niit.ArchieveService.Exception.TaskAlreadyExist;
import com.niit.ArchieveService.Exception.TaskNotFoundException;
import com.niit.ArchieveService.Exception.UserAlreadyExistsException;
import com.niit.ArchieveService.Exception.UserNotExistsException;
import com.niit.ArchieveService.model.Task;
import com.niit.ArchieveService.model.User;

import java.util.List;

public interface ArchiveService {
    User registerUser(User user)throws UserAlreadyExistsException;
    User addTaskToUser(Task task, String userEmail) throws UserNotExistsException, TaskAlreadyExist, TaskNotFoundException;
    List<Task> getDeletedTask(String emailId) throws TaskAlreadyExist;
    List<Task> getCompletedTask(String emailId) throws TaskAlreadyExist;

}
