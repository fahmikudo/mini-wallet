package io.fahmikudo.wallet.util.domain;

import com.fasterxml.jackson.annotation.JsonValue;

public enum OrderStatus {

    PAID,
    SUCCESS,
    FAILED,
    CANCELLED;

    @JsonValue
    public String getStatus(){
        return this.name();
    }

}
