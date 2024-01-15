package behaviourtests;

import io.cucumber.java.After;
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
            CompletableFuture<Payment> result;
            registeredPayment = service.registerPayment(payment);
        }).start();
    }

    @Then("the {string} event is sent")
    public void the_event_is_sent(String string) {
        // Write code here that turns the phrase above into concrete actions
        Event event = new Event(string, new Object[]{payment});
        assertEquals(event, publishedEvent.join());
    }

    @And("the {string} event is sent with a merchantId and customerId and a paymentId")
    public void theEventIsSentWithAMerchantIdAndCustomerIdAndAPaymentId(String arg0) {
        // copy of the payment sent
        Payment expectedPayment = new Payment();
        expectedPayment.setAmount(payment.getAmount());
        expectedPayment.setMerchantId(payment.getMerchantId());
        expectedPayment.setToken(payment.getToken());


        // Mocks the downstream services
        expectedPayment.setCustomerId("customerId");
        expectedPayment.setPaymentId(payment.getPaymentId());

        Event event = new Event(arg0, new Object[]{expectedPayment});
        service.handleBankTransferCompleted(event);
    }

    @Then("the payment is stored in the successful payments ledger")
    public void thePaymentIsStoredInTheSuccessfulPaymentsLedger() {
        // Write code here that turns the phrase above into concrete actions
        Payment result = registeredPayment.join();
        assertNotNull(result.getPaymentId());
    }
}


