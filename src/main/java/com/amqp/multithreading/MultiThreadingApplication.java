package com.amqp.multithreading;

import com.amqp.multithreading.service.Service;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;

@SpringBootApplication
public class MultiThreadingApplication {

	public static void main(String[] args) {
		SpringApplication.run(MultiThreadingApplication.class, args);
	}

	@Bean
	public ApplicationRunner runner(Service service, TaskExecutor exec) {
		return args -> {
			exec.execute(() -> {
				try {
					service.messagingService( "Hello World");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			});
		};

	}
}
