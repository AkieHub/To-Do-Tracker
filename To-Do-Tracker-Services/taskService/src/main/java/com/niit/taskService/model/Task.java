package com.niit.taskService.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
@Data
@AllArgsConstructor
public class Task {
    @Transient
    public static final String SEQUENCE_NAME="task_sequence";


    @Id
    private int taskId;
    private String taskHeading ;
    private String categoryName;
    private String taskDescription;
    private Date taskEndDate;
    private Date taskStartDate;
    private boolean status;
    public Priority priority;
}
