package payment.service;

import messaging.Event;
import messaging.MessageQueue;

import java.util.concurrent.CompletableFuture;

public class PaymentService {

	private MessageQueue queue;
	PaymentRepo paymentRepo = new PaymentRepo();

	CompletableFuture<Payment> registeredPayment;

	public PaymentService(MessageQueue queue) {
		this.queue = queue;
		queue.addHandler("PaymentRequestValidated", this::handleTokenValidated);
	}

	public Payment registerPayment(Payment payment) {
		registeredPayment = new CompletableFuture<>();

		Event event = new Event("PaymentRequestSent", new Object[] {payment});
		queue.publish(event);


		// Kald Account Service for at få bank account id
		//

		// Kald Bank Service for at overføre penge

		return registeredPayment.join();

	}
	public void handleTokenValidated(Event e) {
		var validatedPayment = e.getArgument(0, Payment.class);
		registeredPayment.complete(validatedPayment);
	}

}
