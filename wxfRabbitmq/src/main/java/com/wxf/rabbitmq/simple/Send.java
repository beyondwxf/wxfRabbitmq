package com.wxf.rabbitmq.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.wxf.rabbitmq.Util.ConnectUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {
    private final static String QUERY_NAME = "1_test_01";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 获取到连接以及mq通道
        Connection connection = ConnectUtil.getConnection();
        // 从连接中创建通道
        Channel channel = connection.createChannel();
        // 声明（创建）队列
        channel.queueDeclare(QUERY_NAME, false, false, false, null);
        //消息内容
        String message = "Hello World!";
        channel.basicPublish("", QUERY_NAME, null, message.getBytes());

        System.out.println("[x] Sent '" + message + "'");
        channel.close();
        connection.close();
    }
}
