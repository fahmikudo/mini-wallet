package io.fahmikudo.wallet.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.fahmikudo.wallet.domain.User;
import io.fahmikudo.wallet.exception.HttpException;
import io.fahmikudo.wallet.model.request.OrderProductRequest;
import io.fahmikudo.wallet.model.response.OrderProductResponse;

public interface OrderServiceProduct extends OrderRouterService {

    OrderProductResponse orderProduct(User user, OrderProductRequest orderProductRequest) throws HttpException, JsonProcessingException;

}
