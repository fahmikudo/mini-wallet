package io.fahmikudo.wallet.validator;

import io.fahmikudo.wallet.model.request.LoginRequest;
import io.fahmikudo.wallet.util.commonfunction.ValidatorUtil;
import org.springframework.stereotype.Component;

@Component
public class AuthValidator {

    public String loginValidator(LoginRequest request){
        if (ValidatorUtil.isEmptyOrNull(request.getEmail())) {
            return "Email can not be null or empty";
        }
        if (!ValidatorUtil.regexValidate(request.getEmail(), ValidatorUtil.EMAIL_PATTERN)) {
            return "Invalid format email";
        }
        if (ValidatorUtil.isEmptyOrNull(request.getPassword())) {
            return "Password can not be null or empty";
        }
        return null;
    }

}
