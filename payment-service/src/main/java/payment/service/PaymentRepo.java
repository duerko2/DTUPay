package payment.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaymentRepo {
    List<Payment> payments = new ArrayList<>();
    public void addPayment(Payment bankTransferCompletedPayment) {
        payments.add(bankTransferCompletedPayment);
    }
}
