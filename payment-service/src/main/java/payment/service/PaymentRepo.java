package payment.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class PaymentRepo {
    List<Payment> payments = new ArrayList<>();
    Map<String,CompletableFuture<Payment>> paymentIdToPaymentMap = new HashMap<>();
    public void addPayment(Payment bankTransferCompletedPayment) {
        payments.add(bankTransferCompletedPayment);
    }
    public void addFuturePayment(String paymentId, CompletableFuture<Payment> payment) {
        paymentIdToPaymentMap.put(paymentId,payment);
    }
    public CompletableFuture<Payment> getFuturePayment(String paymentId) {
        return paymentIdToPaymentMap.get(paymentId);
    }
}
