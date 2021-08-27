package io.fahmikudo.wallet.service;

import io.fahmikudo.wallet.domain.Order;
import io.fahmikudo.wallet.util.domain.OrderType;

public interface OrderRouterService {

    boolean isOrderType(OrderType type);

    void order(Order order);

}
