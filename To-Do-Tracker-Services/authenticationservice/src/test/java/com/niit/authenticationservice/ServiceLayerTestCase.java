package com.niit.authenticationservice;

import com.niit.authenticationservice.exception.UserAlreadyExistException;
import com.niit.authenticationservice.exception.UserNotExistException;
import com.niit.authenticationservice.model.User;
import com.niit.authenticationservice.repository.UserRepository;
import com.niit.authenticationservice.service.UserServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.text.ParseException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServiceLayerTestCase {
    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserServiceImpl userService;
    private User user;
    @Autowired
    private MockMvc mockMvc;
    @BeforeEach
    public void setUp() throws ParseException {
        user=new User("harish@gmail.com","123456");
        mockMvc= MockMvcBuilders.standaloneSetup(userService).build();
    }
    @AfterEach
    public void resetData()
    {
        user=null;
    }
    @Test
    public void givenUserToSaveUser() throws UserAlreadyExistException {
        when(userRepository.findById(user.getEmailId())).thenReturn(Optional.ofNullable(null));
        assertEquals(user,userService.saveUser(user));
        verify(userRepository,times(1)).findById(user.getEmailId());
        verify(userRepository,times(1)).save(user);
    }
    @Test
    public void givenUserToSaveUserFailure(){
        when(userRepository.findById(user.getEmailId())).thenReturn(Optional.ofNullable(user));
        assertThrows(UserAlreadyExistException.class,()->userService.saveUser(user));
        verify(userRepository,times(1)).findById(user.getEmailId());
        verify(userRepository,times(0)).save(user);
    }
    @Test
    public void givenUserToLoginUser() throws UserNotExistException {
        when(userRepository.findByEmailIdAndUserPassword(user.getEmailId(),user.getUserPassword())).thenReturn(user);
        assertEquals(user,userService.loginUser(user.getEmailId(),user.getUserPassword()));
        verify(userRepository,times(1)).findByEmailIdAndUserPassword(user.getEmailId(),user.getUserPassword());
    }
    @Test
    public void givenUserToLoginUserFailure(){
        when(userRepository.findByEmailIdAndUserPassword(user.getEmailId(),user.getUserPassword())).thenReturn(null);
        assertThrows(UserAlreadyExistException.class,()->userService.loginUser(user.getEmailId(),user.getUserPassword()));
        verify(userRepository,times(1)).findByEmailIdAndUserPassword(user.getEmailId(),user.getUserPassword());
    }
    @Test
    public void givenUserToUpdateUser() throws UserNotExistException, UserAlreadyExistException {
        when(userRepository.findById(user.getEmailId())).thenReturn(Optional.ofNullable(null));
        user.setUserPassword("1234");
        userService.saveUser(user);
        assertEquals(user,userService.updateUserData(user.getEmailId(),user));
        verify(userRepository,times(2)).findById(user.getEmailId());
        verify(userRepository,times(1)).save(user);
    }
    @Test
    public void givenUserToUpdateUserFailure(){
        when(userRepository.findById(user.getEmailId())).thenReturn(Optional.ofNullable(user));
        user.setUserPassword("1234");
        assertThrows(UserAlreadyExistException.class,()->userService.updateUserData(user.getEmailId(),user));
        verify(userRepository,times(1)).findById(user.getEmailId());
        verify(userRepository,times(0)).save(user);
    }
}
