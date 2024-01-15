package reporting.service;

import java.util.List;

public class MerchantReport {

    List<MerchantPaymentDetails> paymentDetails;

    class MerchantPaymentDetails{
        private int amount;
        private String tokenId;
    }

}
