package bank.service;

import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.BankServiceService;
import messaging.Event;
import messaging.MessageQueue;
import java.math.BigDecimal;

public class DTUPayBankService {

    MessageQueue queue;

    BankService bank;


    public DTUPayBankService(MessageQueue q, BankService bank) {
        this.bank = bank;
        this.queue = q;
        this.queue.addHandler("BankAccountsAssigned", this::makeBankTransfer);
    }

    public void makeBankTransfer(Event event) {
        var p = event.getArgument(0, Payment.class);

        // Needs to actually transfer the money

        try {

            bank.transferMoneyFromTo(
                    p.getCustomerBankId(), p.getMerchantBankId(), BigDecimal.valueOf(p.getAmount()), "paymentDesc");

        }catch (BankServiceException_Exception e){
            System.out.println(e.getMessage());
            return;
        }

        // Publish successful payment event
        Event paymentSuccessfulEvent = new Event("PaymentSuccessful", new Object[]{p});
        queue.publish(paymentSuccessfulEvent);

    }

}
