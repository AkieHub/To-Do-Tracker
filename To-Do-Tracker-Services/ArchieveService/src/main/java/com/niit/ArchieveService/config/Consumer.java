package com.niit.ArchieveService.config;

import com.niit.ArchieveService.Exception.TaskAlreadyExist;
import com.niit.ArchieveService.Exception.UserNotExistsException;
import com.niit.ArchieveService.RabbitMQ.TaskDTO;
import com.niit.ArchieveService.model.Task;
import com.niit.ArchieveService.service.ArchiveServiceImpl;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Consumer {
    @Autowired
    private ArchiveServiceImpl archiveService;

  @RabbitListener(queues = "task_queue")
    public void getCustomerData( TaskDTO taskDTO) throws TaskAlreadyExist, UserNotExistsException {
        Task task=new Task(taskDTO.getTaskId(),taskDTO.getTaskHeading(),taskDTO.getCategoryName(),taskDTO.getTaskDescription(),taskDTO.getTaskEndDate(),taskDTO.getTaskStartDate(),taskDTO.isStatus(),taskDTO.getPriority());
      archiveService.addTaskToUser(task,taskDTO.getEmailId());
    }
}
