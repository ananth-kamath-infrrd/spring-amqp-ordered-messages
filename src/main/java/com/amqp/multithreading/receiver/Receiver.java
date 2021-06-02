package com.amqp.multithreading.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@RabbitListener(queues = "queue")
public class Receiver {

    @RabbitHandler
    public void listen(String in) {
        System.out.println("\n\nReceived: "+in);
    }
}
