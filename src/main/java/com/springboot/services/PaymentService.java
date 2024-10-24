package com.springboot.services;

import com.razorpay.RazorpayException;
import com.springboot.model.Order;
import com.springboot.response.PaymentResponse;

public interface PaymentService {
	
	public PaymentResponse createPaymentLink(Order order) throws RazorpayException ;  
}
