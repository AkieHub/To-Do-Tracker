package com.niit.ArchieveService.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    String emailId;
    String firstName;
    String lastName;
    String gender;
    Date dateOfBirth;
    String userPassword;
    List<Task> userTask;

}
