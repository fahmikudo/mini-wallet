package io.fahmikudo.wallet.validator;

import io.fahmikudo.wallet.model.request.OrderProductRequest;
import io.fahmikudo.wallet.model.request.OrderTopUpRequest;
import io.fahmikudo.wallet.util.commonfunction.ValidatorUtil;
import org.springframework.stereotype.Component;


@Component
public class OrderValidator {

    public String orderTopUpValidator(OrderTopUpRequest orderTopUpRequest){
        if (ValidatorUtil.isEmptyOrNull(orderTopUpRequest.getPhone())) {
            return "Phone number can not be null or empty";
        }
        if (!ValidatorUtil.regexValidate(orderTopUpRequest.getPhone(), ValidatorUtil.PHONE_PATTERN)) {
            return "Invalid format phone number";
        }
        return null;
    }

    public String orderProductValidator(OrderProductRequest orderProductRequest){
        if (ValidatorUtil.isEmptyOrNull(orderProductRequest.getProductName())) {
            return "Product name can not be null or empty";
        }
        if (ValidatorUtil.isEmptyOrNull(orderProductRequest.getShippingAddress())) {
            return "Shipping address can not be null or empty";
        }
        if (ValidatorUtil.isEmptyOrNull(orderProductRequest.getPrice())) {
            return "Price can not be null or empty";
        }
        return null;
    }

}
