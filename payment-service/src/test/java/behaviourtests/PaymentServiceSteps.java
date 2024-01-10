package behaviourtests;

import io.cucumber.java.en.*;
import messaging.Event;
import messaging.MessageQueue;
import payment.service.Payment;
import payment.service.PaymentService;
import payment.service.Token;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PaymentServiceSteps {
    Payment payment;
    private CompletableFuture<Event> publishedEvent = new CompletableFuture<>();


    private MessageQueue q = new MessageQueue() {

        @Override
        public void publish(Event event) {
            publishedEvent.complete(event);
        }

        @Override
        public void addHandler(String eventType, Consumer<Event> handler) {
        }

    };
    private PaymentService service = new PaymentService(q);
    private CompletableFuture<Payment> registeredPayment = new CompletableFuture<>();


    @Given("there is a payment with {int} token merchantId {string} and amount {int}")
    public void there_is_a_payment_with_token_merchant_id_and_amount(Integer int1, String string, Integer int2) {
        // Write code here that turns the phrase above into concrete actions
        payment = new Payment();
        payment.setAmount(int2);
        payment.setMerchantId(string);
        payment.setToken(new Token("123"));

    }

    @When("the payment is requested")
    public void the_payment_is_requested() {
        // Write code here that turns the phrase above into concrete actions
        new Thread(() -> {
            Payment result;
            result = service.registerPayment(payment);
            registeredPayment.complete(result);
        }).start();
    }

    @Then("the {string} event is sent")
    public void the_event_is_sent(String string) {
        // Write code here that turns the phrase above into concrete actions
        Event event = new Event(string, new Object[]{payment});
        assertEquals(event, publishedEvent.join());
        publishedEvent = new CompletableFuture<>();
    }

    @When("the {string} event is sent with a customerId")
    public void the_event_is_sent_with_a_customer_id(String string) {

        // Mocker token service
        var p = new Payment();
        p.setAmount(payment.getAmount());
        p.setMerchantId(payment.getMerchantId());
        p.setToken(payment.getToken());
        p.setCustomerId("customerId");

        service.handleTokenValidated(new Event(string, new Object[]{p}));
    }

    @Then("the payment is registered and customerId is set")
    public void the_payment_is_registered_and_customer_id_is_set() {
        // Write code here that turns the phrase above into concrete actions

        assertNotNull(registeredPayment.join().getCustomerId());
    }

    @When("the {string} event is sent with bankAccountId's for customer and merchant")
    public void theEventIsSentWithBankAccountIdSForCustomerAndMerchant(String arg0) {
        // mocks the account service
        var p = new Payment();
        p.setAmount(payment.getAmount());
        p.setMerchantId(payment.getMerchantId());
        p.setToken(payment.getToken());
        p.setCustomerId(payment.getCustomerId());
        p.setCustomerId(payment.getCustomerId());

        p.setCustomerId("customerId");

        p.setCustomerBankId("customerBankId");
        p.setMerchantBankId("merchantBankId");

        service.handleBankAccountsAssigned(new Event(arg0, new Object[]{p}));
    }

    @Then("the payment has a bank account assigned for the merchant and the customer")
    public void thePaymentHasABankAccountAssignedForTheMerchantAndTheCustomer() {
        // Write code here that turns the phrase above into concrete actions
        assertNotNull(registeredPayment.join().getCustomerBankId());
        assertNotNull(registeredPayment.join().getMerchantBankId());
    }
}


