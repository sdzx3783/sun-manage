package com.sun.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Description:mq消费者
 * @Author: sunzhao
 * @Create on: 2018-11-28 13:36
 */
@Component
@Slf4j
public class MqConsumer {
    @RabbitListener(queues="delay-handle-quene")
    public void consume(byte[] message){
        log.info("mq消费消息 msg：{}",new String(message));
    }
}
