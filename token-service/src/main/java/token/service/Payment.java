package token.service;

public class Payment {


    int amount;
    Token token;
    String merchantId;
    String customerId;
    String paymentId;

    public Payment() {
    }

    public int getAmount() {
        return amount;
    }


    public String getMerchantId() {
        return merchantId;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }


    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Payment) {
            Payment other = (Payment) obj;
            return other.getPaymentId().equals(this.getPaymentId());
        }
        return false;
    }
}
