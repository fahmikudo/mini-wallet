package io.fahmikudo.wallet.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationRequest {

    @NotNull
    @NotEmpty
    @JsonProperty("email")
    private String email;

    @NotNull
    @NotEmpty
    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @NotNull
    @NotEmpty
    @JsonProperty("phone")
    private String phone;

    @JsonProperty("address")
    private String address;

    @NotNull
    @NotEmpty
    @JsonProperty("password")
    private String password;

}
