package com.niit.taskService.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageConfiguration {
    private String exchangeName="task_exchange";
    private String taskRegisterQueue="task_queue";
    private String notificationRegisterQueue="task_queue";
    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(exchangeName);
    }
    @Bean
    public Queue taskQueueRegister(){
        return new Queue(taskRegisterQueue,false);
    }
    @Bean
    public Queue notificationQueueRegister(){
        return new Queue(notificationRegisterQueue,false);
    }
    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }
    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate=new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
    @Bean
    public Binding taskBindingUser(DirectExchange directExchange){
        return BindingBuilder.bind(taskQueueRegister()).to(directExchange).with("taskRouting_key");

    }
    @Bean
    public Binding notificationBindingUser(DirectExchange directExchange){
        return BindingBuilder.bind(notificationQueueRegister()).to(directExchange).with("notificationRouting_key");

    }
}
