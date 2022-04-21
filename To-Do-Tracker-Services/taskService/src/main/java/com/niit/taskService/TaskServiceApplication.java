package com.niit.taskService;

import com.niit.taskService.Exception.UserNotExistsException;
import com.niit.taskService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.event.EventListener;

import javax.mail.MessagingException;
import java.text.ParseException;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class TaskServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskServiceApplication.class, args);
	}
	private UserService userService;
	@Autowired
	public TaskServiceApplication(UserService userService){
		this.userService=userService;
	}
//	@EventListener(ApplicationReadyEvent.class)
//	public void sendEmailReminderOInit() throws UserNotExistsException, MessagingException, ParseException {
//		System.out.println(userService.emailRemindersForTask());
//	}
}
