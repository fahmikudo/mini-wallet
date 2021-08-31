package io.fahmikudo.wallet.service.impl;

import io.fahmikudo.wallet.domain.Order;
import io.fahmikudo.wallet.domain.User;
import io.fahmikudo.wallet.exception.HttpException;
import io.fahmikudo.wallet.repository.OrderRepository;
import io.fahmikudo.wallet.service.OrderServicePayment;
import io.fahmikudo.wallet.util.domain.OrderStatus;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderServicePaymentImpl implements OrderServicePayment {

    private final OrderRepository orderRepository;

    public OrderServicePaymentImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Boolean payment(User user, String orderNo) throws HttpException {
        Optional<Order> order = orderRepository.findByOrderNoAndIsDeleted(orderNo, false);
        if (order.isEmpty()) {
            throw new HttpException("Order not found", HttpStatus.BAD_REQUEST);
        }
        order.get().setStatus(OrderStatus.PAID);
        order.get().setUpdatedBy(user.getFirstName());
        order.get().setUser(user);
        orderRepository.save(order.get());
        return Boolean.TRUE;
    }
}
