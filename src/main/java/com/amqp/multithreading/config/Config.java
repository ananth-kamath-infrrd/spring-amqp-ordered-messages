package com.amqp.multithreading.config;

import com.amqp.multithreading.receiver.Receiver;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ThreadChannelConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class Config {

    @Bean
    TaskExecutor exec() {
        ThreadPoolTaskExecutor exec = new ThreadPoolTaskExecutor();
        exec.setCorePoolSize(10);
        return exec;
    }

    @Bean
    ThreadChannelConnectionFactory tccf() {
        ConnectionFactory rabbitConnectionFactory = new ConnectionFactory();
        rabbitConnectionFactory.setHost("localhost");
        return new ThreadChannelConnectionFactory(rabbitConnectionFactory);
    }

    @Bean
    Queue queue() {
        return new Queue("queue");
    }

    @Bean
    public Receiver receiver() {
        return new Receiver();
    }

}
