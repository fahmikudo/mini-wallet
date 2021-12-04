package io.fahmikudo.wallet.util.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.fahmikudo.wallet.domain.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageSender {

    private final AmqpTemplate rabbitTemplate;

    @Value("${mini.wallet.rabbitmq.exchange}")
    private String exchange;

    @Value("${mini.wallet.rabbitmq.routingkey}")
    private String routingkey;

    public MessageSender(AmqpTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(Order order) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String orderReq = objectMapper.writeValueAsString(order);
        Message message = MessageBuilder.withBody(orderReq.getBytes())
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .setHeader("x-delay", 60000)
                .build();
        rabbitTemplate.convertAndSend(exchange, routingkey, message);
        log.info("Sending message >>> " + orderReq);
    }

}
