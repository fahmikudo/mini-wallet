package io.fahmikudo.wallet.controller;

import io.fahmikudo.wallet.domain.User;
import io.fahmikudo.wallet.exception.HttpException;
import io.fahmikudo.wallet.model.BaseResponse;
import io.fahmikudo.wallet.model.request.OrderProductRequest;
import io.fahmikudo.wallet.model.request.OrderTopUpRequest;
import io.fahmikudo.wallet.model.response.OrderDetailResponse;
import io.fahmikudo.wallet.model.response.OrderProductResponse;
import io.fahmikudo.wallet.model.response.OrderTopUpResponse;
import io.fahmikudo.wallet.service.OrderService;
import io.fahmikudo.wallet.service.OrderServicePayment;
import io.fahmikudo.wallet.service.OrderServiceProduct;
import io.fahmikudo.wallet.service.OrderServiceTopUpBalance;
import io.fahmikudo.wallet.util.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static io.fahmikudo.wallet.util.commonfunction.CommonFunction.getError;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController extends BaseController {

    private BaseResponse response;

    private final OrderServiceTopUpBalance orderTopUpService;

    private final OrderServiceProduct orderProductService;

    private final OrderService orderService;

    private final OrderServicePayment orderServicePayment;

    public OrderController(OrderServiceTopUpBalance orderTopUpService, OrderServiceProduct orderProductService, OrderService orderService, OrderServicePayment orderServicePayment) {
        this.orderTopUpService = orderTopUpService;
        this.orderProductService = orderProductService;
        this.orderService = orderService;
        this.orderServicePayment = orderServicePayment;
    }

    @PostMapping("/top-up-balance")
    public ResponseEntity<BaseResponse> orderTopUpBalance(@RequestBody OrderTopUpRequest request){
        try {
            User user = getUserActiveFromContext();
            OrderTopUpResponse res = orderTopUpService.orderTopUp(user, request);
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

    @PostMapping("/product")
    public ResponseEntity<BaseResponse> orderProduct(@RequestBody OrderProductRequest request){
        try {
            User user = getUserActiveFromContext();
            OrderProductResponse res = orderProductService.orderProduct(user, request);
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

    @GetMapping("/order-history")
    public ResponseEntity<BaseResponse> getOrderHistory(@RequestParam(name = "page", defaultValue = "1") int page,
                                                 @RequestParam(name = "size", defaultValue = "10") int size){
        try {
            page = page - 1;
            User user = getUserActiveFromContext();
            List<OrderDetailResponse> res = orderService.getOrderDetails(user, page, size);
            response = new BaseResponse(OK.value(), BaseController.SUCCESS, res);

            return new ResponseEntity<>(response, OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            response = getError(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/order-detail/{order_no}")
    public ResponseEntity<BaseResponse> getOrder(@PathVariable("order_no") String orderNo){
        try {
            User user = getUserActiveFromContext();
            OrderDetailResponse res = orderService.getOrderDetail(user, orderNo);
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

    @PostMapping("/payment/{order_no}")
    public ResponseEntity<BaseResponse> orderPayment(@PathVariable("order_no") String orderNo){
        try {
            User user = getUserActiveFromContext();
            Boolean res = orderServicePayment.payment(user, orderNo);
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
