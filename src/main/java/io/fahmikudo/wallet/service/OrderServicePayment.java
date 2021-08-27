package io.fahmikudo.wallet.service;

import io.fahmikudo.wallet.domain.User;
import io.fahmikudo.wallet.exception.HttpException;

public interface OrderServicePayment {

    Boolean payment(User user, String orderNo) throws HttpException;

}
