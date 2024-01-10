package studentid.service;

import messaging.Event;
import messaging.MessageQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class TokenService {

	MessageQueue queue;
	public TokenService(MessageQueue q) {
		this.queue = q;
		this.queue.addHandler("InitialTokensRequested", this::handleInitialTokenEvent);
	}
	public Account handleInitialTokenEvent(Event ev) {
		var account = ev.getArgument(0, Account.class);
		// 6 tokens
		List<Token> tokenList = new ArrayList<>();
		for(int i = 0; i < 6; i++){
			tokenList.add(generateRandomToken());
		}
		account.setTokens(tokenList);
		Event event = new Event("InitialTokensAssigned", new Object[] { account });
		queue.publish(event);
		return account;
	}
	private Token generateRandomToken(){
		Random r = new Random();

		// Copilot generated this code
		String randomString = r.ints(48,122)
				.filter(i-> (i<57 || i>65) && (i <90 || i>97))
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
}
