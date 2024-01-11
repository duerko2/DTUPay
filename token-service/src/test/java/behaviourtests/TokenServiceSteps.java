package behaviourtests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import messaging.Event;
import messaging.MessageQueue;
import token.service.Account;
import token.service.Payment;
import token.service.Token;
import token.service.TokenService;

import java.util.concurrent.CompletableFuture;

public class TokenServiceSteps {
	MessageQueue queue = mock(MessageQueue.class);
	TokenService s = new TokenService(queue);
	Account account;
	Account expected;
	private CompletableFuture<Account> TokenizedAccount;
	Payment payment;
	Token token;
	String prevRFID;



	@When("a {string} event for an account is received")
	public void aEventForAnAccountIsReceived(String eventName) {
		// mocking the event
		account = new Account();
		// Bond..
		account.setName("James");
		account.setLastname("Bond");
		account.setCpr("007");
		account.setAccountId("123");

		//System.out.println(account);

		account = s.handleInitialTokenEvent(new Event(eventName, new Object[]{account}));
	}

	@Then("the {string} event is sent")
	public void theEventIsSent(String eventName) {
		expected = new Account();
		expected.setName("James");
		expected.setLastname("Bond");
		expected.setCpr("007");
		expected.setAccountId("123");

		var event = new Event(eventName, new Object[]{expected});
		verify(queue).publish(event);
	}

	@Then("the account has {int} tokens")
	public void theAccountGetsTokens(Integer int1) {
		assertEquals(int1, account.getTokens().size());
	}




	@Then("the {string}")
	public void the(String string) {
		// Write code here that turns the phrase above into concrete actions

	}

	@Given("a valid payment with a valid token that exist")
	public void aValidPaymentWithAValidTokenThatExist() {
		token = new Token("xxx69xxx");
	s.getTokenRepo().addToken( token, "customer");
		payment = new Payment();
		payment.setAmount(100);
		payment.setMerchantId("merchant");
		payment.setToken(token);

	}
	@When("a {string} for a payment")
	public void aForAPayment(String eventName) {

		Object[] arguments = new Object[]{payment};
		s.handlePaymentRequestSent(new Event(eventName, arguments));

	}

	@Then("the token is deleted")
	public void the_token_is_deleted() {
		// Write code here that turns the phrase above into concrete actions'
		assertNull(s.getTokenRepo().getAccountId(token));
	}


	@Then("the {string} event is sent with a payment")
	public void theEventIsSentWithAPayment(String eventName) {
		Payment expectedPayment = new Payment();
		expectedPayment.setMerchantId(payment.getMerchantId());
		expectedPayment.setToken(payment.getToken());
		expectedPayment.setAmount(payment.getAmount());

		expectedPayment.setAccountId("customer");

		var event = new Event(eventName, new Object[]{expectedPayment});
		verify(queue).publish(event);

	}
}



