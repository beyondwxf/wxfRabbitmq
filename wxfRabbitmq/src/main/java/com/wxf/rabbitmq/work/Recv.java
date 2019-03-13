package com.wxf.rabbitmq.work;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;
import com.wxf.rabbitmq.ConnectUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Recv {
    private final static String QUEUE_NAME = "test_queue_work";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnectUtil.getConnection();
        Channel channel = connection.createChannel();
        //声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //同一时刻服务器只会发出一条消息给消费者
        //        channel.basicQos(1);
        //定义队列的消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);
        //监听队列，false标识手动返回完成状态，true表示自动
        channel.basicConsume(QUEUE_NAME, false, consumer);

        //获取消息
        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println(" [x]11 Received '" + message + "'");
            Thread.sleep(10);
            // 返回确认状态，注释掉表示使用自动确认模式
//            channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);
        }

    }
}
