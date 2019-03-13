package com.wxf.rabbitmq.work;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;
import com.wxf.rabbitmq.ConnectUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Recv2 {
    private final static String QUEUE_NAME = "test_queue_work";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnectUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
//        channel.basicQos(1);
        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);

        channel.basicConsume(QUEUE_NAME, false, queueingConsumer);
        while (true) {
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            String s = new String(delivery.getBody());
            System.out.println(" [x]22 Received '" + s + "'");

            Thread.sleep(10);
//            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);

        }
    }
}
