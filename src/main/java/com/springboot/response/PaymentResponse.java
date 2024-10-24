package com.springboot.response;

import lombok.Data;

@Data
public class PaymentResponse {
	    private String orderId;
	    private String key;
	    private Long amount;
	    private String currency;
	    private String successUrl; // URL for successful payment
	    private String failureUrl;
}
