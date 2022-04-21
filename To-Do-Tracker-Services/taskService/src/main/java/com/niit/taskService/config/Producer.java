package com.niit.taskService.config;

import com.niit.taskService.RabbitMQ.TaskDTO;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Producer {
    private RabbitTemplate rabbitTemplate;
    private DirectExchange directExchange;

    @Autowired
    public Producer(RabbitTemplate rabbitTemplate, DirectExchange directExchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.directExchange = directExchange;
    }
    public void sendMessageToRabbitMQ(TaskDTO taskDTO){
        rabbitTemplate.convertAndSend(directExchange.getName(),"taskRouting_key",taskDTO);
    }
}
