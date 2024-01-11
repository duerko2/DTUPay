package payment.service;

import messaging.Event;
import messaging.MessageQueue;

import java.util.concurrent.CompletableFuture;

public class PaymentService {

	private MessageQueue queue;
	PaymentRepo paymentRepo = new PaymentRepo();

	CompletableFuture<Payment> completedPayment;


	public PaymentService(MessageQueue queue) {
		this.queue = queue;
		queue.addHandler("PaymentSuccessful", this::handleBankTransferCompleted);
	}

	public Payment registerPayment(Payment payment) {
		completedPayment = new CompletableFuture<>();

		// Event to token service
		Event tokenEvent = new Event("PaymentRequestSent", new Object[] {payment});
		queue.publish(tokenEvent);

		return completedPayment.join();
	}


	public void handleBankTransferCompleted(Event event) {
		var bankTransferCompletedPayment = event.getArgument(0, Payment.class);
		paymentRepo.addPayment(bankTransferCompletedPayment);
		this.completedPayment.complete(bankTransferCompletedPayment);
	}

}
