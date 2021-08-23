package io.fahmikudo.wallet.domain;

import io.fahmikudo.wallet.util.domain.BaseModel;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Builder
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseModel {

    public final static String ACTIVE = "active";
    public final static String INACTIVE = "inactive";

    @Column(name = "first_name", length = 50)
    private String firstName;

    @Column(name = "last_name", length = 50)
    private String lastName;

    @Column(length = 50)
    private String email;

    @Column(length = 50)
    private String phone;

    @Column(length = 500)
    private String password;

    @Column(name = "status", length = 20)
    private String status;

    @Column(name = "address", columnDefinition = "TEXT")
    private String address;

    @Column(name = "is_active")
    private Boolean isActive;

}
