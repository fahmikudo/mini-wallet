package io.fahmikudo.wallet.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.fahmikudo.wallet.util.domain.OrderStatus;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class OrderTopUpResponse implements Serializable {

    @JsonProperty(value="order_status")
    private OrderStatus orderStatus;

    @JsonProperty(value="order_no")
    private String orderNo;

    private BigDecimal total;

    @JsonProperty(value="order_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date orderDate;

}
