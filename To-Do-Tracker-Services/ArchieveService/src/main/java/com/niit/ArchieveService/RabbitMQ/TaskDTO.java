package com.niit.ArchieveService.RabbitMQ;

import lombok.Data;

import java.util.Date;

@Data
public class TaskDTO {
    private int taskId;
    private String taskHeading ;
    private String categoryName;
    private String taskDescription;
    private Date taskEndDate;
    private Date taskStartDate;
    private boolean status;
    private String priority ;
    private String emailId;
}
