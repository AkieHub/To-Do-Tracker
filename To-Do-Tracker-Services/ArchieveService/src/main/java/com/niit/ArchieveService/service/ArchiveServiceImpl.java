package com.niit.ArchieveService.service;

import com.niit.ArchieveService.Exception.TaskAlreadyExist;
import com.niit.ArchieveService.Exception.UserAlreadyExistsException;
import com.niit.ArchieveService.Exception.UserNotExistsException;
import com.niit.ArchieveService.model.Task;
import com.niit.ArchieveService.model.User;
import com.niit.ArchieveService.repository.ArchiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArchiveServiceImpl implements ArchiveService{
    ArchiveRepository archiveRepository;
    @Autowired
    public ArchiveServiceImpl(ArchiveRepository archiveRepository) {
        this.archiveRepository = archiveRepository;
    }

    @Override
    public User registerUser(User user) throws UserAlreadyExistsException {
        if (archiveRepository.findById(user.getEmailId()).isPresent()) {

            throw new UserAlreadyExistsException();
        }

        return archiveRepository.save(user);
    }
    @Override
    public User addTaskToUser(Task task, String userEmail) throws TaskAlreadyExist, UserNotExistsException { //throws UserNotExist
        if (archiveRepository.findById(userEmail).isEmpty()) {
            throw new UserNotExistsException();
        }
        User user = archiveRepository.findById(userEmail).get();
        List<Task> tasks;
        if (user.getUserTask() == null) {
            tasks = new ArrayList<>();
     }
        else {
           tasks = user.getUserTask();
//            for (Task task1 : tasks) {
//                if (task1.getTaskHeading().equalsIgnoreCase(task.getTaskHeading())) {
//                    throw new TaskAlreadyExist();
//                }
//            }
     }
        tasks.add(task);
        user.setUserTask(tasks);
        System.out.println(user);
        return archiveRepository.save(user);
    }

    @Override
    public List<Task> getDeletedTask(String emailId) throws TaskAlreadyExist {
        return archiveRepository.findById(emailId).get().getUserTask().stream().filter(t->!t.isStatus()).collect(Collectors.toList());
    }
    @Override
    public List<Task> getCompletedTask(String emailId) throws TaskAlreadyExist {
        return archiveRepository.findById(emailId).get().getUserTask().stream().filter(t->t.isStatus()).collect(Collectors.toList());
    }
}
