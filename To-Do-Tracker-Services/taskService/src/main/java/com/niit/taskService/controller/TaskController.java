package com.niit.taskService.controller;


import com.niit.taskService.Exception.TaskAlreadyExist;
import com.niit.taskService.Exception.TaskNotFoundException;
import com.niit.taskService.Exception.UserAlreadyExistsException;
import com.niit.taskService.Exception.UserNotExistsException;
import com.niit.taskService.model.Task;
import com.niit.taskService.model.User;
import com.niit.taskService.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/niit/user")
@CrossOrigin
public class TaskController {
    UserService userTaskService;

    @Autowired
    public TaskController(UserService userTaskService) {
        this.userTaskService = userTaskService;
    }

    @PostMapping("/register")
    ResponseEntity<?> registerUser(@RequestBody User user) throws UserAlreadyExistsException {
        try {
            return new ResponseEntity<>(userTaskService.registerUser(user),HttpStatus.CREATED);
        }catch (UserAlreadyExistsException userAlreadyExistsException){
            throw new UserAlreadyExistsException();
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("try",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/get-userName/{emailId}")
    ResponseEntity<?> getUserName(@PathVariable String emailId) throws UserNotExistsException {
        try {
            return new ResponseEntity<>(userTaskService.getUserName(emailId),HttpStatus.CREATED);
        }catch (UserNotExistsException userNotExistsException){
            throw new UserNotExistsException();
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("try",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/update-profile/{emailId}")
    ResponseEntity<?> updateUserProfile(@PathVariable String emailId, @RequestBody User user) throws UserNotExistsException {
        try {
            return new ResponseEntity<>(userTaskService.updateProfile(emailId, user), HttpStatus.OK);
        } catch (UserNotExistsException e) {
            throw new UserNotExistsException();
        } catch (Exception exception) {
            return new ResponseEntity<>("try after some tim", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @PutMapping("/update/{emailId}")
    ResponseEntity<?> updateUser(@PathVariable String emailId, @RequestBody User user) throws TaskNotFoundException, UserNotExistsException {
        try {
            return new ResponseEntity<>(userTaskService.updateUserData(emailId, user), HttpStatus.OK);
        } catch (UserNotExistsException e) {
            throw new UserNotExistsException();
        } catch (Exception exception) {
            return new ResponseEntity<>("try after some tim", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/task/add/{email}")
    ResponseEntity<?> addTaskToUser(@RequestBody Task task, @PathVariable String email) throws UserNotExistsException, TaskAlreadyExist {
        try {
            return new ResponseEntity<>(userTaskService.addTaskToUser(task, email), HttpStatus.OK);
        } catch (UserNotExistsException userNotExist) {
            throw new UserNotExistsException();
        } catch (TaskAlreadyExist taskAlreadyExist) {
            throw new TaskAlreadyExist();
        } catch (Exception e) {
            return new ResponseEntity<>("try after some time", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/task/modify/{email}")
    ResponseEntity<?> modifyUserTask(@RequestBody Task task, @PathVariable String email) throws TaskNotFoundException, UserNotExistsException {
        try {
            return new ResponseEntity<>(userTaskService.modifyUserTask(task, email), HttpStatus.OK);
        } catch (TaskNotFoundException e) {
            throw new TaskNotFoundException();
        } catch (UserNotExistsException e) {
            throw new UserNotExistsException();
        } catch (Exception e) {
            return new ResponseEntity<>("try after some time", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/task/remove/{email}/{taskId}/{operation}")
    ResponseEntity<?> removeUserTask(@PathVariable int taskId, @PathVariable String email,@PathVariable String operation) throws TaskNotFoundException, UserNotExistsException {
        try {
            return new ResponseEntity<>(userTaskService.removeUserTask(taskId, email,operation), HttpStatus.OK);
        } catch (TaskNotFoundException e) {
            throw new TaskNotFoundException();
        } catch (UserNotExistsException e) {
            throw new UserNotExistsException();
        } catch (Exception exception) {
            return new ResponseEntity<>("try after some tim", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @GetMapping("/task/get-task/{email}/{taskId}")
    ResponseEntity<?> getTaskData(@PathVariable int taskId, @PathVariable String email) throws TaskNotFoundException, UserNotExistsException {
        try {
            return new ResponseEntity<>(userTaskService.getTask( email,taskId), HttpStatus.OK);
        } catch (TaskNotFoundException e) {
            throw new TaskNotFoundException();
        } catch (UserNotExistsException e) {
            throw new UserNotExistsException();
        } catch (Exception e) {
            return new ResponseEntity<>("try after some time", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/task/all-task/{email}")
    ResponseEntity<?> getUserAllTask(@PathVariable String email) throws UserNotExistsException {
        try {
            return new ResponseEntity<>(userTaskService.getAllTask(email), HttpStatus.OK);
        } catch (UserNotExistsException userNotExistsException) {
            throw new UserNotExistsException();
        } catch (Exception e) {
            return new ResponseEntity<>("try after some time", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/task/start-date/{email}")
    ResponseEntity<?> getTaskByStartDate(@PathVariable String email) throws UserNotExistsException {
        try {
            return new ResponseEntity<>(userTaskService.categorizeByStartDate(email), HttpStatus.OK);
        } catch (UserNotExistsException userNotExistsException) {
            throw new UserNotExistsException();
        } catch (Exception e) {
            return new ResponseEntity<>("try after some time", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/task/end-date/{email}")
    ResponseEntity<?> getTaskByEndDate(@PathVariable String email) throws UserNotExistsException {
        try {
            return new ResponseEntity<>(userTaskService.categorizeByEndDate(email), HttpStatus.OK);
        } catch (UserNotExistsException userNotExistsException) {
            throw new UserNotExistsException();
        } catch (Exception e) {
            return new ResponseEntity<>("try after some time", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/task/priority/{email}")
    ResponseEntity<?> getTaskByPriority(@PathVariable String email) throws UserNotExistsException {
        try {
            return new ResponseEntity<>(userTaskService.categorizeByPriority(email), HttpStatus.OK);
        } catch (UserNotExistsException userNotExistsException) {
            throw new UserNotExistsException();
        } catch (Exception e) {
            return new ResponseEntity<>("try after some time", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/task/near-due/{email}")
    public ResponseEntity<?> getTasksWithNearDueDate(@PathVariable String email) throws UserNotExistsException {
        try {
            return new ResponseEntity<>(userTaskService.getTasksWithNearDueDate(email), HttpStatus.OK);
        } catch(UserNotExistsException e) {
            throw new UserNotExistsException();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("try after some time", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/task/overdue/{email}")
    public ResponseEntity<?> getTasksOverDue(@PathVariable String email) throws UserNotExistsException {
        try {
            return new ResponseEntity<>(userTaskService.getTasksWithOverDue(email), HttpStatus.OK);
        } catch(UserNotExistsException e) {
            throw new UserNotExistsException();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("try after some time", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @GetMapping("/user-profile/{emailId}")
    ResponseEntity <?> returnUserName(@PathVariable String emailId) throws UserNotExistsException {
        try {
            return new ResponseEntity<>(userTaskService.returnUserDetails(emailId),HttpStatus.OK);
        }catch (UserNotExistsException existsException){
            throw new UserNotExistsException();
        }catch (Exception e){
            return new ResponseEntity<>("try after some time",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
