package cn.liyu.springbootrabbitmq.mq;

import cn.liyu.springbootrabbitmq.constant.RabbitBeanInfo;
import cn.liyu.springbootrabbitmq.vo.Programmer;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author liyu
 * @date 2020/3/11 18:20
 * @description
 */
@Component
@Slf4j
public class RabbitmqBeanConsumer {

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = RabbitBeanInfo.QUEUE_NAME, durable = RabbitBeanInfo.QUEUE_DURABLE),
            exchange = @Exchange(value = RabbitBeanInfo.EXCHANGE_NAME, type = RabbitBeanInfo.EXCHANGE_TYPE),
            key = RabbitBeanInfo.ROUTING_KEY)
    )
    @RabbitHandler
    public void onMessage(@Payload Programmer programmer, @Headers Map<String, Object> headers, Channel channel) throws Exception {
        log.info("programmer:{} ", programmer);
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        channel.basicAck(deliveryTag, false);
    }
}
