package behaviourtests;

import bank.service.DTUPayBankService;
import bank.service.Payment;
import dtu.ws.fastmoney.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import messaging.MessageQueue;
import messaging.Event;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;


public class BankServiceSteps {

    BankService bank = mock(BankService.class);
    MessageQueue queue = mock(MessageQueue.class);

    Payment payment;
    String paymentId;
    int amount;
    String merchantBankId;
    String customerBankId;
    DTUPayBankService service = new DTUPayBankService(queue,bank);

    @Given("a valid payment with merchantBankId, customerBankId and amount")
    public void a_valid_payment_with_merchant_bank_id_customer_bank_id_and_amount() {
        merchantBankId = "merchantBankId";
        customerBankId = "customerBankId";
        amount = 200;

        payment = new Payment();
        payment.setMerchantBankId(merchantBankId);
        payment.setAmount(amount);
        payment.setCustomerBankId(customerBankId);
    }

    @When("a {string} event is sent")
    public void aEventIsSent(String arg0) {
        Event event = new Event(arg0, new Object[]{payment});
        service.makeBankTransfer(event);
    }

    @Then("the request has been sent to the {string} bank service")
    public void theRequestHasBeenSentToTheBankService(String arg0) throws BankServiceException_Exception {
        verify(bank).transferMoneyFromTo(payment.getCustomerBankId(),payment.getMerchantBankId(), BigDecimal.valueOf(payment.getAmount()),"paymentDesc");
    }

    @Then("a {string} event is published")
    public void aEventIsPublishedWithAPaymentID(String arg0) {

        verify(queue).publish(new Event(arg0, new Object[]{payment}));

    }


}