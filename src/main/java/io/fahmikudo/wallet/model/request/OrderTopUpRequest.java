package io.fahmikudo.wallet.model.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class OrderTopUpRequest {

    private String phone;

    private Integer balance;
}
