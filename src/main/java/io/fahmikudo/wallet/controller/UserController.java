package io.fahmikudo.wallet.controller;

import io.fahmikudo.wallet.exception.HttpException;
import io.fahmikudo.wallet.model.request.UserRegistrationRequest;
import io.fahmikudo.wallet.model.response.UserRegistrationResponse;
import io.fahmikudo.wallet.security.SecurityUtils;
import io.fahmikudo.wallet.model.BaseResponse;
import io.fahmikudo.wallet.service.UserService;
import io.fahmikudo.wallet.util.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static io.fahmikudo.wallet.util.commonfunction.CommonFunction.getError;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController extends BaseController {

    private final SecurityUtils securityUtils;
    private final UserService userService;

    private BaseResponse response;

    public UserController(UserService userService, SecurityUtils securityUtils) {
        this.userService = userService;
        this.securityUtils = securityUtils;
    }

    @PostMapping("/register")
    public ResponseEntity<BaseResponse> userRegister(
            @RequestBody UserRegistrationRequest request
    ) {
        try {
            UserRegistrationResponse res = userService.userRegistration(request);
            response = new BaseResponse(OK.value(), BaseController.SUCCESS, res);

            return new ResponseEntity<>(response, OK);
        } catch (HttpException e) {
            log.error(e.getMessage());
            response = getError(e.getHttpStatus(), e.getMessage());
            return new ResponseEntity<>(response, e.getHttpStatus());
        } catch (Exception e) {
            log.error(e.getMessage());
            response = getError(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
