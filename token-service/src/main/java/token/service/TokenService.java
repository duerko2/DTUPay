package token.service;

import messaging.Event;
import messaging.MessageQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TokenService {

	TokenRepo tokenRepo = new TokenRepo();
	MessageQueue queue;

	public TokenService(MessageQueue q) {
		this.queue = q;
		this.queue.addHandler("InitialTokensRequested", this::handleInitialTokenEvent);
		this.queue.addHandler("PaymentRequestSent", this::handlePaymentRequestSent);
	}

	public Account handleInitialTokenEvent(Event ev) {
		var account = ev.getArgument(0, Account.class);
		// 6 tokens
		List<Token> tokenList = new ArrayList<>();
		for (int i = 0; i < 6; i++) {
			tokenList.add(generateRandomToken());
		}
		addTokensToAccount(account, tokenList);

		Event event = new Event("InitialTokensAssigned", new Object[]{account});
		queue.publish(event);
		return account;
	}

	private void addTokensToAccount(Account account, List<Token> tokenList) {
		account.setTokens(tokenList);
		for (Token token : tokenList) {
			tokenRepo.addToken(token, account.getAccountId());
		}
	}

	private Token generateRandomToken() {
		Random r = new Random();

		// Copilot generated this code
		String randomString = r.ints(48, 122)
				.filter(i -> (i < 57 || i > 65) && (i < 90 || i > 97))
				.mapToObj(i -> (char) i)
				.limit(10)
				.collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
				.toString();
		/*
		//Rasmus generated this code
		String ran = UUID.randomUUID().toString();
		//Andreas generated this code
		String dinMor = "ooga booga";
		 */

		return new Token(randomString);
	}

	public void handlePaymentRequestSent(Event ev) {
		var payment = ev.getArgument(0, Payment.class);

		var accountId = tokenRepo.getAccountId(payment.getToken());

		payment.setAccountId(accountId);

		tokenRepo.deleteToken(payment.getToken());
		tokenRepo.addToken(generateRandomToken(),accountId);

		Event event = new Event("PaymentRequestValidated", new Object[]{payment});
		queue.publish(event);
	}
	public TokenRepo getTokenRepo(){
		return tokenRepo;
	}

}
