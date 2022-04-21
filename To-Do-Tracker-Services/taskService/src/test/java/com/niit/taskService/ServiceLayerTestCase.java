//package com.niit.taskService;
//
//
//import com.niit.taskService.Exception.TaskAlreadyExist;
//import com.niit.taskService.Exception.UserAlreadyExistsException;
//import com.niit.taskService.Exception.UserNotExistsException;
//import com.niit.taskService.model.Priority;
//import com.niit.taskService.model.Task;
//import com.niit.taskService.model.User;
//import com.niit.taskService.repository.UserRepository;
//import com.niit.taskService.service.UserServiceImpl;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class ServiceLayerTestCase {
//    @Mock
//    UserRepository userRepository;
//    @InjectMocks
//    UserServiceImpl userService;
//    private Task task;
//    private User user;
//    List<Task> taskList;
//    @Autowired
//    private MockMvc mockMvc;
//    @BeforeEach
//    public void setUp() throws ParseException {
//        task=new Task(1,"raj's birthday","birthday","wishing you a very happy birthday",new Date(), (Date)new SimpleDateFormat("dd/MM/yyyy").parse("10/05/2022"),false , Priority.HIGH);
//
//        user=new User("harish@gmail.com","harish","sharma","male",(Date)new SimpleDateFormat("dd/MM/yyyy").parse("10/05/1995"),"123456",taskList);
//        mockMvc= MockMvcBuilders.standaloneSetup(userService).build();
//
//    }
//    @AfterEach
//    public void resetData()
//    {
//        user=null;
//        task=null;
//    }
////    @Test
////    public void givenUserToSaveUser() throws  UserAlreadyExistsException {
////        when(userRepository.findById(user.getEmailId())).thenReturn(Optional.ofNullable(null));
////        when(userRepository.save(user)).thenReturn(user);
////        assertEquals(user,userService.registerUser(user));
////        verify(userRepository,times(1)).findById(user.getEmailId());
////        verify(userRepository,times(1)).save(user);
////    }
//    @Test
//    public void givenUserToSaveUserFailure(){
//        when(userRepository.findById(user.getEmailId())).thenReturn(Optional.ofNullable(user));
//        assertThrows(UserAlreadyExistsException.class,()->userService.registerUser(user));
//        verify(userRepository,times(1)).findById(user.getEmailId());
//        verify(userRepository,times(0)).save(user);
//    }
//    @Test
//    public void addTaskToUser() throws  TaskAlreadyExist, UserNotExistsException {
//        taskList=Arrays.asList(task);
//        user.setUserTask(taskList);
//        when(userRepository.findById(user.getEmailId())).thenReturn(Optional.ofNullable(user));
//        when(userRepository.save(user)).thenReturn(user);
//        assertEquals(user,userService.addTaskToUser(task,user.getEmailId()));
//        verify(userRepository,times(2)).findById(user.getEmailId());
//        verify(userRepository,times(1)).save(user);
//    }
//    @Test
//    public void addTaskToUserFailure(){
//        taskList=Arrays.asList(task);
//        when(userRepository.findById(user.getEmailId())).thenReturn(Optional.ofNullable(null));
//        assertThrows(UserNotExistsException.class,()->userService.addTaskToUser(task,user.getEmailId()));
//        verify(userRepository,times(1)).findById(user.getEmailId());
//        verify(userRepository,times(0)).save(user);
//    }
//
////    @Test
////    public void getCompletedTaskOfUser() throws TaskAlreadyExist, UserNotExistsException {
////        task.setStatus(true);
////        user.setUserTask( Arrays.asList(task));
////        when(userRepository.findById(user.getEmailId())).thenReturn(Optional.ofNullable(user));
////        assertEquals(user.getUserTask(),userService.getCompletedTask(user.getEmailId()));
////        verify(userRepository,times(1)).findById(user.getEmailId());
////    }
////
////    @Test
////    public void getCompletedTaskOfUserFailure() throws TaskAlreadyExist {
////        user.setUserTask( Arrays.asList(task));
////        when(userRepository.findById(user.getEmailId())).thenReturn(Optional.ofNullable(user));
////        assertNotEquals(user.getUserTask(),userService.getCompletedTask(user.getEmailId()));
////        verify(userRepository,times(1)).findById(user.getEmailId());
////    }
////    @Test
////    public void getDeletedTaskOfUser() throws  TaskAlreadyExist, UserNotExistsException {
////        user.setUserTask( Arrays.asList(task));
////        when(archiveRepository.findById(user.getEmailId())).thenReturn(Optional.ofNullable(user));
////        assertEquals(user.getUserTask(),userService.getDeletedTask(user.getEmailId()));
////        verify(archiveRepository,times(1)).findById(user.getEmailId());
////    }
////
////    @Test
////    public void getDeletedTaskOfUserFailure() throws TaskAlreadyExist {
////        task.setStatus(true);
////        user.setUserTask( Arrays.asList(task));
////        when(archiveRepository.findById(user.getEmailId())).thenReturn(Optional.ofNullable(user));
////        assertNotEquals(user.getUserTask(),userService.getDeletedTask(user.getEmailId()));
////        verify(archiveRepository,times(1)).findById(user.getEmailId());
////    }
//}
