package io.fahmikudo.wallet.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class OrderProductRequest implements Serializable {

    @JsonProperty(value="product_name")
    private String productName;

    @JsonProperty(value="shipping_address")
    private String shippingAddress;

    private BigDecimal price;

}
