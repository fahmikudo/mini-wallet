package io.fahmikudo.wallet.util.commonfunction;

import io.fahmikudo.wallet.model.BaseResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonFunction {

    public static BaseResponse getError(HttpStatus httpStatus, String message) {
        return new BaseResponse(httpStatus.value(), message, null);
    }

    public static synchronized String setOrderNo(){
        Random random = new Random();
        Integer randomNumber = random.nextInt(9999);
        SimpleDateFormat df = new SimpleDateFormat("ddMMyyhhmm");
        String orderNo = String.format("%s%04d", df.format(new Date()), randomNumber);
        return orderNo;
    }

}
