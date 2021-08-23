package io.fahmikudo.wallet.util.commonfunction;

import io.fahmikudo.wallet.model.BaseResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonFunction {

    public static BaseResponse getError(HttpStatus httpStatus, String message) {
        return new BaseResponse(httpStatus.value(), message, null);
    }

}
