package com.niit.ArchieveService.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    private int taskId;
   private String taskHeading ;
    private String categoryName;
    private String taskDescription;
    private Date taskEndDate;
    private Date taskStartDate;
    private boolean status;
    private String priority;
}
