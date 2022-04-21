package com.niit.taskService.service;

import com.niit.taskService.Exception.TaskAlreadyExist;
import com.niit.taskService.Exception.TaskNotFoundException;
import com.niit.taskService.Exception.UserAlreadyExistsException;
import com.niit.taskService.Exception.UserNotExistsException;
import com.niit.taskService.model.Task;
import com.niit.taskService.model.User;

import javax.mail.MessagingException;
import java.text.ParseException;
import java.util.List;

public interface UserService {
    User registerUser(User user)throws UserAlreadyExistsException;
    User updateUserData(String emailId, User user) throws UserNotExistsException;
    User returnUserDetails(String emailId) throws UserNotExistsException;
    String getUserName(String emailId) throws UserNotExistsException;
    User updateProfile(String emailId, User user1) throws  UserNotExistsException;
    User addTaskToUser(Task task, String userEmail) throws UserNotExistsException, TaskAlreadyExist, TaskNotFoundException;
    User modifyUserTask(Task task,String userEmail) throws UserNotExistsException, TaskNotFoundException;
    String removeUserTask(int taskId,String userEmail,String operation) throws UserNotExistsException, TaskNotFoundException;
    List<Task> getAllTask(String emailId) throws  UserNotExistsException;


    List<Task> categorizeByStartDate(String userEmail) throws UserNotExistsException;
    List<Task> categorizeByEndDate(String userEmail) throws UserNotExistsException;

    List<Task> categorizeByPriority(String userEmail) throws UserNotExistsException;


    Task getTask(String userEmail, int taskId) throws TaskNotFoundException, UserNotExistsException;


    List<Task> getTasksWithNearDueDate(String userEmail) throws UserNotExistsException;

    List<Task> getTasksWithOverDue(String userEmail) throws UserNotExistsException;
    String emailRemindersForTask() throws UserNotExistsException, MessagingException, ParseException;
}
