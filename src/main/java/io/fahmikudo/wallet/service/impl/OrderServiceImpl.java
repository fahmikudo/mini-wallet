package io.fahmikudo.wallet.service.impl;

import io.fahmikudo.wallet.domain.Order;
import io.fahmikudo.wallet.domain.User;
import io.fahmikudo.wallet.exception.HttpException;
import io.fahmikudo.wallet.model.response.OrderDetailResponse;
import io.fahmikudo.wallet.repository.OrderRepository;
import io.fahmikudo.wallet.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<OrderDetailResponse> getOrderDetails(User user, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("order_date"));
        Page<Order> orders = orderRepository.findByUserAndIsDeleted(user, pageable, false);
        List<OrderDetailResponse> orderDetailResponses = new ArrayList<>();
        if (orders.isEmpty()) {
            return orderDetailResponses;
        }
        for (Order order : orders.getContent()){
            var orderDetailResponse = new OrderDetailResponse();
            orderDetailResponse.setOrderNo(order.getOrderNo());
            orderDetailResponse.setOrderStatus(order.getStatus());
            orderDetailResponse.setOrderDate(order.getOrderDate());
            orderDetailResponse.setBalance(order.getBalance() == null ? 0 : order.getBalance());
            orderDetailResponse.setPrice(order.getPrice() == null ? BigDecimal.ZERO : order.getPrice());
            orderDetailResponse.setProductName(order.getProductName());
            orderDetailResponse.setShippingAddress(order.getShippingAddress());
            orderDetailResponse.setTotal(order.getTotalAmount());
            orderDetailResponses.add(orderDetailResponse);
        }
        return orderDetailResponses;
    }

    @Override
    public OrderDetailResponse getOrderDetail(User user, String orderNo) throws HttpException {
        Optional<Order> order = orderRepository.findByOrderNoAndUserAndIsDeleted(orderNo, user, false);
        if (order.isEmpty()) {
            throw new HttpException("Order not found", HttpStatus.NOT_FOUND);
        }
        return OrderDetailResponse.builder()
                .orderNo(order.get().getOrderNo())
                .orderStatus(order.get().getStatus())
                .balance(order.get().getBalance() == null ? 0 : order.get().getBalance())
                .price(order.get().getPrice() == null ? BigDecimal.ZERO : order.get().getPrice())
                .productName(order.get().getProductName())
                .shippingAddress(order.get().getShippingAddress())
                .total(order.get().getTotalAmount())
                .build();
    }

}
