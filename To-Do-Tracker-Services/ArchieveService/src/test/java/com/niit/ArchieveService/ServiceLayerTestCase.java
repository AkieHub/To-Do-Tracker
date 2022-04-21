package com.niit.ArchieveService;


import com.niit.ArchieveService.Exception.TaskAlreadyExist;
import com.niit.ArchieveService.Exception.TaskNotFoundException;
import com.niit.ArchieveService.Exception.UserAlreadyExistsException;
import com.niit.ArchieveService.Exception.UserNotExistsException;
import com.niit.ArchieveService.model.Task;
import com.niit.ArchieveService.model.User;
import com.niit.ArchieveService.repository.ArchiveRepository;
import com.niit.ArchieveService.service.ArchiveServiceImpl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class ServiceLayerTestCase {
    @Mock
    ArchiveRepository archiveRepository;
    @InjectMocks
    ArchiveServiceImpl archiveService;
    private Task task;
    private User user;
    List<Task> taskList;
    @Autowired
    private MockMvc mockMvc;
    @BeforeEach
    public void setUp() throws ParseException {
        task=new Task(1,"raj's birthday","birthday","wishing you a very happy birthday",new Date(), (Date)new SimpleDateFormat("dd/MM/yyyy").parse("10/05/2022"),false ,"HIGH");

        user=new User("harish@gmail.com","harish","sharma","male",(Date)new SimpleDateFormat("dd/MM/yyyy").parse("10/05/1995"),"123456",taskList);
        mockMvc= MockMvcBuilders.standaloneSetup(archiveService).build();

    }
    @AfterEach
    public void resetData()
    {
        user=null;
        task=null;
    }
    @Test
    public void givenUserToSaveUser() throws  UserAlreadyExistsException {
        when(archiveRepository.findById(user.getEmailId())).thenReturn(Optional.ofNullable(null));
        when(archiveRepository.save(user)).thenReturn(user);
        assertEquals(user,archiveService.registerUser(user));
        verify(archiveRepository,times(1)).findById(user.getEmailId());
        verify(archiveRepository,times(1)).save(user);
    }
    @Test
    public void givenUserToSaveUserFailure(){
        when(archiveRepository.findById(user.getEmailId())).thenReturn(Optional.ofNullable(user));
        assertThrows(UserAlreadyExistsException.class,()->archiveService.registerUser(user));
        verify(archiveRepository,times(1)).findById(user.getEmailId());
        verify(archiveRepository,times(0)).save(user);
    }
    @Test
    public void addTaskToUser() throws  TaskAlreadyExist, UserNotExistsException {
        taskList=Arrays.asList(task);
        when(archiveRepository.findById(user.getEmailId())).thenReturn(Optional.ofNullable(user));
        when(archiveRepository.save(user)).thenReturn(user);
        assertEquals(user,archiveService.addTaskToUser(task,user.getEmailId()));
        verify(archiveRepository,times(2)).findById(user.getEmailId());
        verify(archiveRepository,times(1)).save(user);
    }
    @Test
    public void addTaskToUserFailure(){
        taskList=Arrays.asList(task);
        when(archiveRepository.findById(user.getEmailId())).thenReturn(Optional.ofNullable(null));
        assertThrows(UserNotExistsException.class,()->archiveService.addTaskToUser(task,user.getEmailId()));
        verify(archiveRepository,times(1)).findById(user.getEmailId());
        verify(archiveRepository,times(0)).save(user);
    }

    @Test
    public void getCompletedTaskOfUser() throws  TaskAlreadyExist, UserNotExistsException {
        task.setStatus(true);
        user.setUserTask( Arrays.asList(task));
        when(archiveRepository.findById(user.getEmailId())).thenReturn(Optional.ofNullable(user));
        assertEquals(user.getUserTask(),archiveService.getCompletedTask(user.getEmailId()));
        verify(archiveRepository,times(1)).findById(user.getEmailId());
    }

    @Test
    public void getCompletedTaskOfUserFailure() throws TaskAlreadyExist {
        user.setUserTask( Arrays.asList(task));
        when(archiveRepository.findById(user.getEmailId())).thenReturn(Optional.ofNullable(user));
        assertNotEquals(user.getUserTask(),archiveService.getCompletedTask(user.getEmailId()));
        verify(archiveRepository,times(1)).findById(user.getEmailId());
    }
    @Test
    public void getDeletedTaskOfUser() throws  TaskAlreadyExist, UserNotExistsException {
        user.setUserTask( Arrays.asList(task));
        when(archiveRepository.findById(user.getEmailId())).thenReturn(Optional.ofNullable(user));
        assertEquals(user.getUserTask(),archiveService.getDeletedTask(user.getEmailId()));
        verify(archiveRepository,times(1)).findById(user.getEmailId());
    }

    @Test
    public void getDeletedTaskOfUserFailure() throws TaskAlreadyExist {
        task.setStatus(true);
        user.setUserTask( Arrays.asList(task));
        when(archiveRepository.findById(user.getEmailId())).thenReturn(Optional.ofNullable(user));
        assertNotEquals(user.getUserTask(),archiveService.getDeletedTask(user.getEmailId()));
        verify(archiveRepository,times(1)).findById(user.getEmailId());
    }
}
