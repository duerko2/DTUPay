package accountregistration.service;

import java.util.concurrent.CompletableFuture;

import messaging.Event;
import messaging.MessageQueue;
import java.util.UUID;

public class AccountRegistrationService {

	private MessageQueue queue;
	private CompletableFuture<Account> registeredAccount;
	
	public AccountRegistrationService(MessageQueue q) {
		queue = q;
		queue.addHandler("InitialTokensAssigned", this::handleInitialTokensAssigned);
	}

	public Account register(Account s) {
		registeredAccount = new CompletableFuture<>();

		String newAccountNumber = UUID.randomUUID().toString();
		s.setAccountId(newAccountNumber);
		Event event = new Event("InitialTokensRequested", new Object[] { s });
		queue.publish(event);
		return registeredAccount.join();
	}

	public void handleInitialTokensAssigned(Event e) {
		var s = e.getArgument(0, Account.class);



		// TODO: Store the account in the database
		// accountRepo.storeAccount(s);

		registeredAccount.complete(s);
	}
}
