package bank.service;

import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.BankServiceService;
import messaging.Event;
import messaging.MessageQueue;
import java.math.BigDecimal;

public class DTUPayBankService {

    MessageQueue queue;

    BankService bank = new BankServiceService().getBankServicePort();


    public DTUPayBankService(MessageQueue q) {
        this.queue = q;
        this.queue.addHandler("BankAccountsAssigned", this::makeBankTransfer);
    }

    private void makeBankTransfer(Event event) {
        var p = event.getArgument(0, Payment.class);

        // Needs to actually transfer the money

        try {

            bank.transferMoneyFromTo(
                    p.getCustomerBankId(), p.getMerchantBankId(), BigDecimal.valueOf(p.getAmount()),
                    "I don't know what they want from me\n" +
                            "It's like the more money we come across\n" +
                            "The more problems we see\n");

        }catch (BankServiceException_Exception e){
            System.out.println(e.getMessage());
            return;
        }

        // Publish successful payment event
        p.setPaymentId("123");
        Event paymentSuccessfulEvent = new Event("PaymentSuccessful", new Object[]{p});
        queue.publish(paymentSuccessfulEvent);

    }

}
