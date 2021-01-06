package uz.pdp.appg4duonotaryserver.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uz.pdp.appg4duonotaryserver.entity.Order;
import uz.pdp.appg4duonotaryserver.payload.ApiResponse;
import uz.pdp.appg4duonotaryserver.repository.OrderRepository;
import uz.pdp.appg4duonotaryserver.repository.PaymentRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class StripeService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    PaymentRepository paymentRepository;

    @Value("${stripe.secretKey}")
    private String stripeSecretKey;


    public ApiResponse getClientSecretForNowCharge(UUID orderId) {
        try {
            Stripe.apiKey = stripeSecretKey;
            Optional<Order> byId = orderRepository.findById(orderId);
            if (byId.isPresent()) {
                Order order = byId.get();
                PaymentIntentCreateParams createParams = new PaymentIntentCreateParams.Builder()
                        .setCurrency("usd")
                        .setAmount((long) ((order.getAmount() - order.getAmountDiscount()) * 100))
                        .build();
                PaymentIntent intent = PaymentIntent.create(createParams);

                return new ApiResponse("ok", true, intent.getClientSecret());
            }
            return new ApiResponse("error", false);
        } catch (Exception e) {
            return new ApiResponse("error", false);
        }

    }

//    @SneakyThrows
//    public ApiResponse sessionApi() {
//        PaymentIntentCreateParams params =
//                PaymentIntentCreateParams.builder()
//                        .setAmount(1099L)
//                        .setCurrency("usd")
//                        .addPaymentMethodType("card")
//                        .setCaptureMethod(PaymentIntentCreateParams.CaptureMethod.MANUAL)
//                        .build();
//
//        PaymentIntent paymentIntent = PaymentIntent.create(params);
//    }

    public ApiResponse testCharge() {
        try {
            Stripe.apiKey = "sk_test_51HiumVL3EZks8ZyyD9XUtAgzrE7UtijGB1dti8vDoW94v1Wg1pyqzniUYlks34kzCx7ApBsQPrpE01hPC9B32jrA00kyNwUYrc";

            PaymentIntentCreateParams createParams = new PaymentIntentCreateParams.Builder()
                    .setCurrency("usd")
                    .setAmount((long) 1000)
                    .addPaymentMethodType("card")
                    .setCaptureMethod(PaymentIntentCreateParams.CaptureMethod.MANUAL)
                    .setPaymentMethod("{{PAYMENT_METHOD_ID}}")
                    .setCustomer(String.valueOf(makeCustomer().getObject()))
                    .setConfirm(true)
                    .setOffSession(true)
                    .build();
            PaymentIntent intent = PaymentIntent.create(createParams);

            return new ApiResponse("ok", true, intent.getClientSecret());
        } catch (StripeException e) {
            e.printStackTrace();
            return new ApiResponse("error", false);
        }

    }
    public ApiResponse makeCustomer() {
        try {
            Stripe.apiKey = "sk_test_51HiumVL3EZks8ZyyD9XUtAgzrE7UtijGB1dti8vDoW94v1Wg1pyqzniUYlks34kzCx7ApBsQPrpE01hPC9B32jrA00kyNwUYrc";

            CustomerCreateParams params =
                    CustomerCreateParams.builder()
                            .build();

            Customer customer = Customer.create(params);
            return new ApiResponse("ok", true, customer.getObject());
        } catch (StripeException e) {
            e.printStackTrace();
            return new ApiResponse("error", false);
        }

    }
//    public ApiResponse orderPaid(UUID orderId) {
//        Optional<Order> orderById = orderRepository.findById(orderId);
//        if (orderById.isPresent()){
//
//            Optional<Payment> byId = paymentRepository.findByOrderId(orderId);
//            if (byId.isPresent()){
//                Payment payment = byId.get();
//                payment.setPayStatus(PayStatus.PAID);
//                paymentRepository.save(payment);
//                return new ApiResponse("ok", true);
//
//            }else{
//                Payment payment=new Payment();
//                payment.setPayStatus(PayStatus.PAID);
//                payment.setOrder(orderById.get());
//                payment.setSum(orderById.get().getAmount()-orderById.get().getAmountDiscount());
//                paymentRepository.save(payment);
//                return new ApiResponse("ok", true);
//            }
//        }
//            return new ApiResponse("error", false);
//    }



    public ApiResponse payHold(UUID orderId){
        Stripe.apiKey = stripeSecretKey;
        try{
        Optional<Order> byId = orderRepository.findById(orderId);
        if (byId.isPresent()) {
            Order order = byId.get();


        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setAmount((long) ((order.getAmount() - order.getAmountDiscount()) * 100))
                        .setCurrency("usd")
                        .addPaymentMethodType("card")
                        .setCaptureMethod(PaymentIntentCreateParams.CaptureMethod.MANUAL)
                        .build();
            PaymentIntent paymentIntent = PaymentIntent.create(params);
            return new ApiResponse("ok", true, paymentIntent.getClientSecret());
        }
            return new ApiResponse("error", false);
    } catch (Exception e) {
        return new ApiResponse("error", false);
    }

    }
}


