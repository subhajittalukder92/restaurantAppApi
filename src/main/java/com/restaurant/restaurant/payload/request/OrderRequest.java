package com.restaurant.restaurant.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class OrderRequest {
    @NotNull(message = "User id is required.")
    private Long userId;
    @NotNull(message = "Address id is required.")
    private Long addressId;
    @NotEmpty(message = "Payment method is required.")
    private String paymntMethod;

    private Long couponId;
}
