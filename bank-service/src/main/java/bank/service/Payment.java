package bank.service;

import java.util.Objects;

public class Payment {

    String paymentId;
    int amount;
    String merchantBankId;
    String customerBankId;

    public Payment() {
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getMerchantBankId() {
        return merchantBankId;
    }

    public void setMerchantBankId(String merchantBankId) {
        this.merchantBankId = merchantBankId;
    }

    public String getCustomerBankId() {
        return customerBankId;
    }

    public void setCustomerBankId(String customerBankId) {
        this.customerBankId = customerBankId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return amount == payment.amount && Objects.equals(paymentId, payment.paymentId) && Objects.equals(merchantBankId, payment.merchantBankId) && Objects.equals(customerBankId, payment.customerBankId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paymentId, amount, merchantBankId, customerBankId);
    }
}
