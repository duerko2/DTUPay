package payment.service;

public class Payment {

    String merchantId;
    int amount;
    Token token;
    String customerId;
    String merchantBankId;
    String customerBankId;
    String paymentId;

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

    public void setAccountId(String accountId) {
        this.customerId = accountId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
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
    public boolean equals(Object obj) {
        if (obj instanceof Payment) {
            Payment other = (Payment) obj;
            return other.amount == amount && other.merchantId.equals(merchantId);
        }
        return false;
    }
}
