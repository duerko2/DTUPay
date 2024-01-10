package account.service;

import java.util.concurrent.CompletableFuture;

import messaging.Event;
import messaging.MessageQueue;
import java.util.UUID;

public class AccountService {

	private MessageQueue queue;
	private CompletableFuture<Account> registeredAccount;
	AccountRepo accountRepo = new AccountRepo();
	
	public AccountService(MessageQueue q) {
		queue = q;
		queue.addHandler("InitialTokensAssigned", this::handleInitialTokensAssigned);
	}

	public Account register(Account s) throws AccountAlreadyExists {

		// check if account exists in repo
		if(accountRepo.accountExists(s.getCpr())){
			throw new AccountAlreadyExists("Account already exists");
		}


		String newAccountNumber = UUID.randomUUID().toString();
		s.setAccountId(newAccountNumber);

		if(s.getType().getType().equals("customer")){
			s = registerCustomerAccount(s);
		} else {
			s = registerMerchantAccount(s);
		}
		accountRepo.storeAccount(s);
		return s;
	}

	private Account registerMerchantAccount(Account s) {
		return s;
	}

	private Account registerCustomerAccount(Account s) {
		registeredAccount = new CompletableFuture<>();
		Event event = new Event("InitialTokensRequested", new Object[] { s });
		queue.publish(event);

		return registeredAccount.join();
	}

	public void handleInitialTokensAssigned(Event e) {
		var s = e.getArgument(0, Account.class);
		accountRepo.storeAccount(s);
		registeredAccount.complete(s);
	}

	public void deleteAccount(String accountId) {
		accountRepo.deleteAccount(accountId);
	}

	public Account getAccount(String accountId) {
		return accountRepo.getAccount(accountId);
	}
}
