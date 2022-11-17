package org.learning.workqueues;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;
import java.util.Random;

public class Worker {

    private static final int workerId = getWorkerId();

    public Worker() {}

    private static final String QUEUE_NAME = "hello";

    private static int getWorkerId() {
        Random random = new Random();
        return random.nextInt(1000, 9000);
    }

    public int getId() {
        return workerId;
    }

    private static void doWork(String task) throws InterruptedException {
        System.out.println("Worker: [" + workerId + "] Processing task: " + task);
        for (char ch: task.toCharArray()) {
            if (ch == '.') Thread.sleep(10);
        }
    }

    public static void receiveWork() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println("\nWorker: [" + workerId + "] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println("\nWorker: [" + workerId + "] Received '" + message + "'");
            try {
                try {
                    doWork(message);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } finally {
                System.out.println("Worker: [" + workerId + "] Task completed.");
            }
        };
        boolean autoAck = true;
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });

    }
}