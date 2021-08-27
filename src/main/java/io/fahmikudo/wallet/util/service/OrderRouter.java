package io.fahmikudo.wallet.util.service;

import io.fahmikudo.wallet.domain.Order;
import io.fahmikudo.wallet.service.OrderRouterService;
import lombok.Setter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class OrderRouter implements ApplicationContextAware {

    @Setter
    private ApplicationContext applicationContext;

    public void order(Order order){
        applicationContext.getBeansOfType(OrderRouterService.class).values()
                .stream()
                .filter(orderRouterService -> orderRouterService.isOrderType(order.getOrderType()))
                .findFirst()
                .ifPresent(orderRouterService -> orderRouterService.order(order));
    }


}
