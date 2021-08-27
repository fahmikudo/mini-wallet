package io.fahmikudo.wallet.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.fahmikudo.wallet.util.domain.BaseModel;
import io.fahmikudo.wallet.util.domain.OrderStatus;
import io.fahmikudo.wallet.util.domain.OrderType;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Entity
@Builder
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
public class Order extends BaseModel {

    @Column(name = "order_no", length = 20)
    private String orderNo;

    @Column(name = "phone", length = 15)
    private String phone;

    @Column(name = "balance")
    private Integer balance;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "shipping_address")
    private String shippingAddress;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "order_type")
    @Enumerated(EnumType.STRING)
    private OrderType orderType;

    @Column(name = "order_date")
    @Temporal(TemporalType.TIME)
    private Date orderDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonBackReference
    private User user;

}
