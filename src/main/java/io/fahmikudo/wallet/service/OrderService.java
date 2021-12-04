package io.fahmikudo.wallet.service;

import io.fahmikudo.wallet.domain.User;
import io.fahmikudo.wallet.exception.HttpException;
import io.fahmikudo.wallet.model.response.OrderDetailResponse;

import java.util.List;

public interface OrderService {

    List<OrderDetailResponse> getOrderDetails(User user, int page, int size);

    OrderDetailResponse getOrderDetail(User user, String orderNo) throws HttpException;

}
