package io.fahmikudo.wallet.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.fahmikudo.wallet.domain.Order;
import io.fahmikudo.wallet.domain.User;
import io.fahmikudo.wallet.exception.HttpException;
import io.fahmikudo.wallet.model.request.OrderProductRequest;
import io.fahmikudo.wallet.model.response.OrderProductResponse;
import io.fahmikudo.wallet.repository.OrderRepository;
import io.fahmikudo.wallet.service.OrderServiceProduct;
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
public class OrderServiceProductImpl implements OrderServiceProduct {

    private final OrderRepository orderRepository;
    private final OrderValidator orderValidator;
    private final MessageSender messageSender;

    public OrderServiceProductImpl(OrderRepository orderRepository, OrderValidator orderValidator, MessageSender messageSender) {
        this.orderRepository = orderRepository;
        this.orderValidator = orderValidator;
        this.messageSender = messageSender;
    }

    @Override
    public boolean isOrderType(OrderType type) {
        return OrderType.PRODUCT.equals(type);
    }

    @Override
    @Transactional
    public void order(Order order) {
        orderRepository.save(order);
    }

    @Override
    public OrderProductResponse orderProduct(User user, OrderProductRequest orderProductRequest) throws HttpException, JsonProcessingException {
        String validation = orderValidator.orderProductValidator(orderProductRequest);
        if (validation != null) {
            throw new HttpException(validation, HttpStatus.BAD_REQUEST);
        }
        var orderReq = Order.builder()
                .orderNo(CommonFunction.setOrderNo())
                .productName(orderProductRequest.getProductName())
                .shippingAddress(orderProductRequest.getShippingAddress())
                .price(orderProductRequest.getPrice())
                .totalAmount(getTotalAmount(orderProductRequest.getPrice()))
                .orderType(OrderType.PRODUCT)
                .orderDate(new Date())
                .status(OrderStatus.SUCCESS)
                .user(user)
                .build();
        order(orderReq);
        messageSender.send(orderReq);
        return OrderProductResponse.builder()
                .orderNo(orderReq.getOrderNo())
                .productName(orderReq.getProductName())
                .shippingAddress(orderReq.getShippingAddress())
                .price(orderReq.getPrice())
                .orderStatus(orderReq.getStatus())
                .total(orderReq.getTotalAmount())
                .build();
    }

    private BigDecimal getTotalAmount(BigDecimal price){
        return new BigDecimal(String.valueOf(BigDecimal.valueOf(10000).add(price)));
    }

}
