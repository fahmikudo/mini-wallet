package io.fahmikudo.wallet.util.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.fahmikudo.wallet.domain.Order;
import io.fahmikudo.wallet.repository.OrderRepository;
import io.fahmikudo.wallet.util.domain.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
public class MessageReceiver {

    private final OrderRepository orderRepository;

    public MessageReceiver(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @RabbitListener(queues = "miniwallet.queue")
    @Transactional
    public void receiveMessage(Message message) throws JsonProcessingException {
        log.info("Received message >>> {} | {}", new String(message.getBody()), new Date());
        ObjectMapper objectMapper = new ObjectMapper();
        Order order = objectMapper.readValue(new String(message.getBody()), Order.class);
        Optional<Order> getOrder = orderRepository.findByOrderNoAndStatusAndIsDeleted(order.getOrderNo(), OrderStatus.SUCCESS, false);
        if (getOrder.isPresent()) {
            order.setStatus(OrderStatus.CANCELLED);
            order.setUser(getOrder.get().getUser());
            order.setUpdatedBy(getOrder.get().getUser().getFirstName());
            orderRepository.saveAndFlush(order);
        }
    }


}
