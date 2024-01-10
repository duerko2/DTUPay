package accountregistration.service.adapter.rest;

import accountregistration.service.AccountDeletionService;
import accountregistration.service.AccountRegistrationService;
import messaging.MessageQueue;
import messaging.implementations.RabbitMqQueue;

public class AccountServiceFactory {
	static AccountDeletionService deletionService = null;
	static AccountRegistrationService registrationService = null;

	private final MessageQueue mq = new RabbitMqQueue("rabbitMq");


	public synchronized AccountDeletionService getDeletionService(){
		if (deletionService != null) {
			return deletionService;
		}
		deletionService = new AccountDeletionService(mq);
		return deletionService;
	}
	public synchronized AccountRegistrationService getRegistrationService(){
		if (registrationService != null) {
			return registrationService;
		}
		registrationService = new AccountRegistrationService(mq);
		return registrationService;
	}

	/*
	public synchronized StudentRegistrationService getService() {
		// The singleton pattern.
		// Ensure that there is at most
		// one instance of a PaymentService
		if (service != null) {
			return service;
		}
		
		// Hookup the classes to send and receive
		// messages via RabbitMq, i.e. RabbitMqSender and
		// RabbitMqListener. 
		// This should be done in the factory to avoid 
		// the PaymentService knowing about them. This
		// is called dependency injection.
		// At the end, we can use the PaymentService in tests
		// without sending actual messages to RabbitMq.
		var mq = new RabbitMqQueue("rabbitMq");
		service = new StudentRegistrationService(mq);
//		new StudentRegistrationServiceAdapter(service, mq);
		return service;
	}

	 */
}
