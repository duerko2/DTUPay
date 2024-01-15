package reporting.service;

import java.util.List;

public class CustomerReport {
    private List<CustomerPaymentDetails> paymentDetails;

    class CustomerPaymentDetails {
        private int amount;
        private String merchantId;
        private String tokenId;

        public int getAmount() {return amount;}

        public void setAmount(int amount) {this.amount = amount;}

        public String getMerchantId() {return merchantId;}

        public void setMerchantId(String merchantId) {this.merchantId = merchantId;}

        public String getTokenId() {return tokenId;}

        public void setTokenId(String tokenId) {this.tokenId = tokenId;}
    }
}
