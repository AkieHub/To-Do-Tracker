package com.niit.ArchieveService;


import com.niit.ArchieveService.model.Task;
import com.niit.ArchieveService.model.User;
import com.niit.ArchieveService.repository.ArchiveRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class UserRepositoryTest {

    @Autowired
    private ArchiveRepository archiveRepository;
    private User user;
    private Task task;
    List<Task> taskList;

    @BeforeEach
    public void setUp() throws ParseException {

        task=new Task(1,"ravi's birthday","birthday","wishing you a very happy birthday",new Date(), (Date)new SimpleDateFormat("dd/MM/yyyy").parse("10/05/2022"),false ,"HIGH");
        user=new User("harish@gmail.com","harish","sharma","male",(Date)new SimpleDateFormat("dd/MM/yyyy").parse("10/05/1995"),"123456",taskList);
    }

    @AfterEach
    public void tearDown(){
        user=null;
        task=null;
        archiveRepository.deleteAll();
    }

    //positive test cases for insertion
    @Test
    public void givenUserToSaveAndReturnTheUser(){
        archiveRepository.save(user);
        User user1 = archiveRepository.findById(user.getEmailId()).get();
        assertEquals(user.getEmailId(),user1.getEmailId());
        assertEquals("harish",user.getFirstName());
    }

    //negative test cases for insertion
    @Test
    public void givenUserToSaveAndReturnTheUserFailure(){
        archiveRepository.save(user);
        User user1 = archiveRepository.findById(user.getEmailId()).get();
        assertNotNull(user1);
        assertNotEquals(0,user1.getFirstName());
        assertNotEquals(2,user1.getFirstName());
        assertNotEquals("rahul",user.getFirstName());
    }


    //positive test cases for getting all task
    @Test
    public void givenTaskSaveTaskToUser(){
        user.setUserTask( Arrays.asList(task));
        archiveRepository.save(user);
        User user1 = archiveRepository.findById(user.getEmailId()).get();
        List<Task> list =user1.getUserTask();
        assertEquals(1,list.size());
        assertEquals("ravi's birthday",list.get(0).getTaskHeading());
        assertEquals("birthday",list.get(0).getCategoryName());
        assertEquals("HIGH",list.get(0).getPriority());
    }

    //negative cases for getting all task
    @Test
    public void givenTaskSaveTaskToUserFailure(){
        user.setUserTask( Arrays.asList(task));
        archiveRepository.save(user);
        User user1 = archiveRepository.findById(user.getEmailId()).get();
        List<Task> list =user1.getUserTask();
        assertNotEquals(0,list.size());
        assertNotEquals("ravi's birthdays",list.get(0).getTaskHeading());
        assertNotEquals("birthdayparty",list.get(0).getCategoryName());
        assertNotEquals("Low",list.get(0).getPriority());
    }

    //positive test cases for delete cases
    @Test
    public void getCompletedTaskOfUser(){
        task.setStatus(true);
        user.setUserTask(  Arrays.asList(task));
        archiveRepository.save(user);
        User user1 = archiveRepository.findById(user.getEmailId()).get();
        List<Task> list =user1.getUserTask().stream().filter(t->t.isStatus()).collect(Collectors.toList());
        assertEquals(1,list.size());
        assertEquals("ravi's birthday",list.get(0).getTaskHeading());
        assertEquals("birthday",list.get(0).getCategoryName());
        assertEquals(true,list.get(0).isStatus());
    }

    //negative test case for delete
    @Test
    public void getCompletedTaskOfUserFailure(){
        user.setUserTask(  Arrays.asList(task));
        archiveRepository.save(user);
        User user1 = archiveRepository.findById(user.getEmailId()).get();
        List<Task> list =user1.getUserTask().stream().filter(t->t.isStatus()).collect(Collectors.toList());
        assertNotEquals(1,list.size());
    }


    @Test
    public void getDeletedTaskOfUser(){
        task.setStatus(true);
        user.setUserTask(  Arrays.asList(task));
        archiveRepository.save(user);
        User user1 = archiveRepository.findById(user.getEmailId()).get();
        List<Task> list =user1.getUserTask().stream().filter(t->t.isStatus()).collect(Collectors.toList());
        assertEquals(1,list.size());
        assertEquals("ravi's birthday",list.get(0).getTaskHeading());
        assertEquals("birthday",list.get(0).getCategoryName());
        assertEquals(true,list.get(0).isStatus());
    }

    //negative test case for delete
    @Test
    public void getDeletedTaskOfUserFailure(){
        user.setUserTask(  Arrays.asList(task));
        archiveRepository.save(user);
        User user1 = archiveRepository.findById(user.getEmailId()).get();
        List<Task> list =user1.getUserTask().stream().filter(t->!t.isStatus()).collect(Collectors.toList());
        //assertNotEquals(1,list.size());
    }


}//end of class




