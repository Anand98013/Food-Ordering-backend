//package com.springboot.services;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import com.springboot.model.Order;
//import com.springboot.response.PaymentResponse;
//import com.stripe.Stripe;
//import com.stripe.exception.StripeException;
//import com.stripe.model.checkout.Session;
//import com.stripe.param.checkout.SessionCreateParams;
//
//@Service
//public class PaymentServiceImp implements PaymentService {
//    
//    @Value("${stripe.api.key}")
//    private String stripeSecretKey;
//    
//    @Override
//    public PaymentResponse createPaymentLink(Order order) throws StripeException {
//        
//        Stripe.apiKey = stripeSecretKey;
//        
//            SessionCreateParams params = SessionCreateParams.builder()
//                    .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
//                    .setMode(SessionCreateParams.Mode.PAYMENT)
//                    .setSuccessUrl("http://localhost:3000/payment/success/" + order.getId())
//                    .setCancelUrl("http://localhost:3000/payment/fail")
//                    .addLineItem(SessionCreateParams.LineItem.builder()
//                            .setQuantity(1L)
//                            .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
//                                    .setCurrency("usd")
//                                    .setUnitAmount((long) order.getTotalPrize() * 100)
//                                    .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
//                                            .setName("mesh food")
//                                            .build())
//                                    .build())
//                            .build())
//                    .build();
//            
//            Session session = Session.create(params);
//            
//            PaymentResponse response = new PaymentResponse();
//            response.setPayment_url(session.getUrl());
//            
//            return response;
//    }
//}


package com.springboot.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.springboot.model.Order;
import com.springboot.response.PaymentResponse;
import org.json.JSONObject;

@Service
public class PaymentServiceImp implements PaymentService {

    @Value("${razorpay.api.key}")
    private String razorpayApiKey;
    
    @Value("${razorpay.api.secret}")
    private String razorpayApiSecret;
    
    @Override
    public PaymentResponse createPaymentLink(Order order) throws RazorpayException {
        
        // Initialize Razorpay client
        RazorpayClient razorpayClient = new RazorpayClient(razorpayApiKey, razorpayApiSecret);

        // Create a new order
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", order.getTotalPrize() * 100); // Razorpay takes amount in paise, hence multiply by 100
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", "order_rcptid_" + order.getId());

        // Create an order in Razorpay
        com.razorpay.Order razorpayOrder = razorpayClient.orders.create(orderRequest);
        
        // Prepare the response with order details to be used by the frontend
        PaymentResponse response = new PaymentResponse();
        response.setOrderId(razorpayOrder.get("id")); // Set Razorpay order ID to track
        response.setAmount(order.getTotalPrize()); // Set amount
        response.setCurrency("INR"); // Set currency
        response.setKey(razorpayApiKey); // Pass the Razorpay API key to the frontend
        
        response.setSuccessUrl("http://localhost:3000/payment/success/" + order.getId()); // Set success URL
        response.setFailureUrl("http://localhost:3000/payment/fail"); // Set failure URL

        return response;
    }
}
