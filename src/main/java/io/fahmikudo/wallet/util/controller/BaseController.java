package io.fahmikudo.wallet.util.controller;

import io.fahmikudo.wallet.domain.User;
import io.fahmikudo.wallet.util.config.ConstantConfig;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public abstract class BaseController {

    public final static String SUCCESS = "success";
    public final static String ERROR = "error";

    public static boolean checkGuestAccessAuthorization(String apiKey) {
        String apiKeyGeneral = ConstantConfig.getInstance().getApiKeyGeneral();
        return apiKey.equalsIgnoreCase(apiKeyGeneral);
    }

    protected User getUserActiveFromContext() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
