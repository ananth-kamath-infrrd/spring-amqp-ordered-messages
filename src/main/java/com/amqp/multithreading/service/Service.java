package com.amqp.multithreading.service;

import org.springframework.amqp.rabbit.connection.ThreadChannelConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class Service {

    private final RabbitTemplate template;

    private final TaskExecutor exec;

    private final ThreadChannelConnectionFactory connectionFactory;

    private int count;

    public Service(RabbitTemplate template, TaskExecutor exec, ThreadChannelConnectionFactory connectionFactory) {
        this.template = template;
        this.exec = exec;
        this.connectionFactory = connectionFactory;
        this.count = 1;
    }

    public void messagingService(String toSend) throws InterruptedException {
            for(int j=1;j<=10;j++) {
              String payload = "\n From: " + "Service - " + this.count
                      + "\n Thread: " + Thread.currentThread().getName()
                      + "\n Message:" + toSend + " " + j;
              this.template.convertAndSend("queue", payload);
              Thread.sleep(1000);
            }
            this.count++;
            Object context = this.connectionFactory.prepareSwitchContext();
            this.exec.execute(() -> {
                try {
                    messagingService(toSend, context);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
    }

    public void messagingService(String toSend, Object threadContext) throws InterruptedException {
            if(this.count>10) {
                this.connectionFactory.closeThreadChannel();
                return;
            } else {
                this.connectionFactory.switchContext(threadContext);
                for(int j=1;j<=10;j++) {
                    String payload = "\nFrom: " + "Service - " + this.count
                            + "\n Thread: " + Thread.currentThread().getName()
                            + "\n Message:" + toSend + " " + j;
                    this.template.convertAndSend("queue", payload);
                    Thread.sleep(1000);
                }
                this.count++;
                Object newContext = this.connectionFactory.prepareSwitchContext();
                this.exec.execute(() -> {
                    try {
                        messagingService(toSend, newContext);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
    }

}
