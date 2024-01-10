package payment.service;

import messaging.Event;
import messaging.MessageQueue;

import java.util.concurrent.CompletableFuture;

public class PaymentService {

	private MessageQueue queue;
	PaymentRepo paymentRepo = new PaymentRepo();

	CompletableFuture<Payment> validatedPayment;

	CompletableFuture<Payment> bankAccountAssignedPayment;
	private CompletableFuture<Payment> completedPayment;


	public PaymentService(MessageQueue queue) {
		this.queue = queue;
		queue.addHandler("PaymentRequestValidated", this::handleTokenValidated);
		queue.addHandler("BankAccountsAssigned", this::handleBankAccountsAssigned);
		queue.addHandler("BankTransferCompleted", this::handleBankTransferCompleted);
	}

	public Payment registerPayment(Payment payment) {
		validatedPayment = new CompletableFuture<>();
		bankAccountAssignedPayment = new CompletableFuture<>();


		// Event to token service
		Event tokenEvent = new Event("PaymentRequestSent", new Object[] {payment});
		queue.publish(tokenEvent);

		// Event for account service
		Event bankEvent = new Event("BankAccountsAssignmentRequest", new Object[] {validatedPayment.join()});
		queue.publish(bankEvent);

		// Event for bank service
		//Event paymentEvent = new Event("BankTransferRequest", new Object[] {bankAccountAssignedPayment.join()});
		//queue.publish(paymentEvent);

		return bankAccountAssignedPayment.join();

	}
	public void handleTokenValidated(Event e) {
		var validatedPayment = e.getArgument(0, Payment.class);
		this.validatedPayment.complete(validatedPayment);
	}
	public void handleBankAccountsAssigned(Event event) {
		var bankAccountAssignedPayment = event.getArgument(0, Payment.class);
		this.bankAccountAssignedPayment.complete(bankAccountAssignedPayment);
	}
	public void handleBankTransferCompleted(Event event) {
		var bankTransferCompletedPayment = event.getArgument(0, Payment.class);
		paymentRepo.addPayment(bankTransferCompletedPayment);
		this.completedPayment.complete(bankTransferCompletedPayment);
	}

}
