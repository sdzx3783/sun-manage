package com.sun.manage;

import com.sun.manage.common.util.DateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * @Description:mq测试
 * @Author: sunzhao
 * @Create on: 2018-11-28 10:39
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RabbitMqTest {
    @Autowired
    private AmqpTemplate template;
    //不显式声明交换机时并且发送消息不指定交换机，则默认使用Direct，并且声明队列时，不显式绑定队列与交换机，则队列以队列名为routing-key绑定到默认的direct交换机，发送消息不指定交换机时，则将消息发到默认的direct交换机
    @Test
    public void testProducer() {
        MessageProperties msProperties=new MessageProperties();
        //msProperties.setTimestamp(new Date());
        msProperties.setExpiration("120000");//过期时间
        Message ms=new Message("哈哈延时消息 ".getBytes(),msProperties);
        template.send("delay-quene",ms);
        System.out.print("mq延时消息已发送....");
    }

}
