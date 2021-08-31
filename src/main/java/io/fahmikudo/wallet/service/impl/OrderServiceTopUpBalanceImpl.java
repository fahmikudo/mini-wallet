package io.fahmikudo.wallet.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.fahmikudo.wallet.domain.Order;
import io.fahmikudo.wallet.domain.User;
import io.fahmikudo.wallet.exception.HttpException;
import io.fahmikudo.wallet.model.request.OrderTopUpRequest;
import io.fahmikudo.wallet.model.response.OrderTopUpResponse;
import io.fahmikudo.wallet.repository.OrderRepository;
import io.fahmikudo.wallet.service.OrderServiceTopUpBalance;
import io.fahmikudo.wallet.util.commonfunction.CommonFunction;
import io.fahmikudo.wallet.util.domain.OrderStatus;
import io.fahmikudo.wallet.util.domain.OrderType;
import io.fahmikudo.wallet.util.service.MessageSender;
import io.fahmikudo.wallet.validator.OrderValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class OrderServiceTopUpBalanceImpl implements OrderServiceTopUpBalance {

    private final OrderValidator orderValidator;
    private final OrderRepository orderRepository;
    private final MessageSender messageSender;

    public OrderServiceTopUpBalanceImpl(OrderValidator orderValidator, OrderRepository orderRepository, MessageSender messageSender) {
        this.orderValidator = orderValidator;
        this.orderRepository = orderRepository;
        this.messageSender = messageSender;
    }

    @Override
    public boolean isOrderType(OrderType type) {
        return OrderType.TOPUP.equals(type);
    }

    @Override
    @Transactional
    public void order(Order order) {
        orderRepository.save(order);
    }

    @Override
    public OrderTopUpResponse orderTopUp(User user, OrderTopUpRequest orderTopUpRequest) throws HttpException, JsonProcessingException {
        String validation = orderValidator.orderTopUpValidator(orderTopUpRequest);
        if (validation != null) {
            throw new HttpException(validation, HttpStatus.BAD_REQUEST);
        }
        var orderReq = Order.builder()
                .orderNo(CommonFunction.setOrderNo())
                .balance(orderTopUpRequest.getBalance())
                .phone(orderTopUpRequest.getPhone())
                .orderType(OrderType.TOPUP)
                .status(OrderStatus.SUCCESS)
                .totalAmount(getTotalAmount(orderTopUpRequest.getBalance()).setScale(2))
                .orderDate(new Date())
                .user(user)
                .build();
        order(orderReq);
        messageSender.send(orderReq);
        return OrderTopUpResponse.builder()
                .orderNo(orderReq.getOrderNo())
                .orderStatus(orderReq.getStatus())
                .total(orderReq.getTotalAmount())
                .orderDate(orderReq.getOrderDate())
                .build();
    }

    private BigDecimal getTotalAmount(Integer balance){
        double tax = 0.10 * balance;
        return new BigDecimal(String.valueOf(BigDecimal.valueOf(balance).add(BigDecimal.valueOf(tax))));
    }

}
