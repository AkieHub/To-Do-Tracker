package com.niit.ArchieveService.controller;

import com.niit.ArchieveService.Exception.TaskAlreadyExist;
import com.niit.ArchieveService.Exception.UserAlreadyExistsException;
import com.niit.ArchieveService.Exception.UserNotExistsException;
import com.niit.ArchieveService.model.Task;
import com.niit.ArchieveService.model.User;
import com.niit.ArchieveService.service.ArchiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/niit/archive")
@CrossOrigin
public class ArchiveController {
    ArchiveService archiveService;

    @Autowired
    public ArchiveController(ArchiveService archiveService) {
        this.archiveService = archiveService;
    }
    @PostMapping("/register")
    ResponseEntity<?> registerUser(@RequestBody User user) throws UserAlreadyExistsException {
        try {
            return new ResponseEntity<>(archiveService.registerUser(user),HttpStatus.CREATED);
        }catch (UserAlreadyExistsException userAlreadyExistsException){
            throw new UserAlreadyExistsException();
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("try",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/task/add/{email}")
    ResponseEntity<?> addTaskToUser(@RequestBody Task task, @PathVariable String email) throws UserNotExistsException, TaskAlreadyExist {
        try {
            return new ResponseEntity<>(archiveService.addTaskToUser(task, email), HttpStatus.OK);
        } catch (UserNotExistsException userNotExist) {
            throw new UserNotExistsException();
        } catch (TaskAlreadyExist taskAlreadyExist) {
            throw new TaskAlreadyExist();
        } catch (Exception e) {
            return new ResponseEntity<>("try after some time", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/task/get-completedTask/{emailId}")
    ResponseEntity<?> getAllCompletedTask(@PathVariable String emailId) throws TaskAlreadyExist {
        try {
            return new ResponseEntity<>(archiveService.getCompletedTask(emailId), HttpStatus.OK);
        }  catch (TaskAlreadyExist taskAlreadyExist) {
            throw new TaskAlreadyExist();
        } catch (Exception e) {
            return new ResponseEntity<>("try after some time", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/task/get-deleted/{emailId}")
    ResponseEntity<?> getDeletedTask(@PathVariable String emailId) throws TaskAlreadyExist {
        try {
            return new ResponseEntity<>(archiveService.getDeletedTask(emailId), HttpStatus.OK);
        }  catch (TaskAlreadyExist taskAlreadyExist) {
            throw new TaskAlreadyExist();
        } catch (Exception e) {
            return new ResponseEntity<>("try after some time", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
