package com.niit.ArchieveService;//package com.niit.ArchieveService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.niit.ArchieveService.Exception.TaskAlreadyExist;
import com.niit.ArchieveService.Exception.UserAlreadyExistsException;
import com.niit.ArchieveService.Exception.UserNotExistsException;
import com.niit.ArchieveService.controller.ArchiveController;
import com.niit.ArchieveService.model.Task;
import com.niit.ArchieveService.model.User;
import com.niit.ArchieveService.service.ArchiveServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @ExtendWith(MockitoExtension.class)
        @Mock
        private ArchiveServiceImpl archiveService;

        @InjectMocks
        private ArchiveController archiveController;

        //to create a bean
        @Autowired
        private MockMvc mockMvc;



        private Task task;
        private User user;
        private List<Task> taskList;

        @BeforeEach
        public void setUp() throws ParseException {
            task=new Task(1,"ravi's birthday","birthday","wishing you a very happy birthday",new Date(), (Date)new SimpleDateFormat("dd/MM/yyyy").parse("10/05/2022"),false ,"HIGH");
            user=new User("harish@gmail.com","harish","sharma","male",(Date)new SimpleDateFormat("dd/MM/yyyy").parse("10/05/1995"),"123456",taskList);
            taskList= Arrays.asList(task);
            mockMvc= MockMvcBuilders.standaloneSetup(archiveController).build();
        }

        @AfterEach
        public void tearDown(){
            user=null;
            task=null;
        }

        //method for conversion from Domain/Model/Pojo Object to JSON formatted Fashioned Object
        private  String jsonToString(final Object obj) throws JsonProcessingException {
            String result=null;
            try {
                ObjectMapper mapper = new ObjectMapper(); // provides functionality for reading and writing JSON,
                String jsonContent = mapper.writeValueAsString(obj);
                result=jsonContent;
            }
            catch (JsonProcessingException e){
                result="error while conversion";
            }
            return result;
        }

        //positive test cases for insertion of data
        @Test
        public void givenUserToSaveReturnUser() throws Exception{
            when(archiveService.registerUser(any())).thenReturn(user);
            mockMvc.perform(post("/niit/archive/register")
                            .contentType(MediaType.APPLICATION_JSON).content(jsonToString(user)))
                    .andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print()); //actions
            verify(archiveService,times(1)).registerUser(any());
        }
        //negative test cases for insertion of data
        @Test
        public void givenUserToSaveReturnUserFailure()throws Exception
        {
            when(archiveService.registerUser(any())).thenThrow(UserAlreadyExistsException.class);
            mockMvc.perform(post("/niit/archive/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonToString(task)))
                    .andExpect(status().isConflict()).andDo(MockMvcResultHandlers.print());
            verify(archiveService,times(1)).registerUser(any());
        }

    @Test
    public void givenTaskToAddReturnUser() throws Exception{
        when(archiveService.addTaskToUser(any(),anyString())).thenReturn(user);
        mockMvc.perform(put("/niit/archive/task/add/harish@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON).content(jsonToString(user)))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(archiveService,times(1)).addTaskToUser(any(),anyString());
    }
    @Test
    public void givenTaskToSaveReturnTaskFailure()throws Exception
    {
        when(archiveService.addTaskToUser(any(),anyString())).thenThrow(UserNotExistsException.class);
        mockMvc.perform(put("/niit/archive/task/add/harish12@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(task)))
                .andExpect(status().isNotFound()).andDo(MockMvcResultHandlers.print());
        verify(archiveService,times(1)).addTaskToUser(any(),anyString());
    }

    @Test
    public void givenUserEmailToAddReturnCompletedTask() throws Exception{
        task.setStatus(true);
        when(archiveService.getCompletedTask(anyString())).thenReturn(Arrays.asList(task));
        mockMvc.perform(get("/niit/archive/task/get-completedTask/harish@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(user)))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(archiveService,times(1)).getCompletedTask(anyString());
    }
//
//    //negative test cases for add of user data
    @Test
    public void givenUserEmailToAddReturnCompletedTaskFailure() throws Exception
    {
        when(archiveService.getCompletedTask(anyString())).thenReturn(null);
        mockMvc.perform(get("/niit/archive/task/get-completedTask/harish@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(user)))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(archiveService,times(1)).getCompletedTask(anyString());
    }


    @Test
    public void givenUserEmailToAddReturnDeletedTask() throws Exception{
        when(archiveService.getDeletedTask(anyString())).thenReturn(taskList);
        mockMvc.perform(get("/niit/archive/task/get-deleted/harish@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(user)))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(archiveService,times(1)).getDeletedTask(anyString());
    }

    @Test
    public void givenUserEmailToAddReturnDeletedTaskFailure() throws Exception
    {
        when(archiveService.getDeletedTask(anyString())).thenReturn(null);
        mockMvc.perform(get("/niit/archive/task/get-deleted/harish@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(user)))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(archiveService,times(1)).getDeletedTask(anyString());
    }
    }//end of class


