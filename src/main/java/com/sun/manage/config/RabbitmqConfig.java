package com.sun.manage.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:mq配置
 * @Author: sunzhao
 * @Create on: 2018-11-28 9:52
 *
 * 在创建durable的队列（或者交换机）的时候，将auto_delete设置成false是很重要的，否则队列将会在最后一个消费者断开的时候消失，与durable与否无关。如果将durable和auto_delete都设置成True，只有尚有消费者活动的队列可以在RabbitMQ意外崩溃的时候自动恢复。
 *
 * （你可以注意到了另一个标志，称为“exclusive”。如果设置成True，只有创建这个队列的消费者程序才允许连接到该队列。这种队列对于这个消费者程序是私有的）。
 * ---------------------
 * 作者：木坦坦
 * 来源：CSDN
 * 原文：https://blog.csdn.net/simplty/article/details/50466043
 * 版权声明：本文为博主原创文章，转载请附上博文链接！
 */
@Configuration
@EnableRabbit
public class RabbitmqConfig {
    //延时消息发送目标队列
    @Bean(name = "delayQuene")
    public Queue delayQuene() {
        Map<String,Object> args=new HashMap<>();
        args.put("x-dead-letter-exchange","Dead-letter-exchange");//不配置路由key的话，默认是该队列的名称（delayQuene）;
//        args.put("Dead letter routing key","delayQuene1");
        return new Queue("delay-quene",true,false,false,args);
    }
    //延时消息处理目标队列
    @Bean(name="delayHandleQuene")
    public Queue delayHandleQuene() {
        return new Queue("delay-handle-quene");
    }

    @Bean(name = "directExchange")
    public DirectExchange exchange() {
        return new DirectExchange("Dead-letter-exchange");
    }

    @Bean
    Binding bindingExchangeMessage(@Qualifier("delayHandleQuene") Queue delayHandleQuene, @Qualifier("directExchange")DirectExchange exchange) {
        return BindingBuilder.bind(delayHandleQuene).to(exchange).with("delay-quene");//delay-quene：由于delay-quene队列没有配置路由key的话，routekey默认是该队列的名称（delayQuene），所以消费端需绑定的routekey为delayQuene1
    }

}
