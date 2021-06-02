# spring-amqp-ordered-messages (Using ThreadedChannelConnectionFactory)
Problem statement:
Employ 10 threads to produce a message to a single queue. Each thread produces 10 messages. The messages must enter the Queue in the order of that produced by it's respective producer thread. A thread must not be allowed to produce messages to the queue till the current thread is done producing all it's messages. 

## SOP
Step 1: Make sure that rabbitMQ service is running on localhost.  
Step 2: Import the current project to IntelliJ IDEA.  
Step 3: Right click on the file "RabbitAmqpTutorialsApplication.java" and run it has a Java Application.
