package payment.service.adapter.rest;

import javax.transaction.Status;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import payment.service.Payment;
import payment.service.PaymentService;

import java.util.concurrent.CompletionException;
import java.util.concurrent.TimeoutException;

@Path("/payments")
public class PaymentResource {

	PaymentServiceFactory factory = new PaymentServiceFactory();
	PaymentService service = factory.getService();

	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response registerPayment(Payment payment) {
		try {
			var p = service.registerPayment(payment);
			return Response.ok(p.join()).build();
		} catch(CompletionException e) {
			if(e.getCause() instanceof TimeoutException) {
				return Response.status(408).build();
			}
			return Response.status(500).build();
		} catch (Exception e) {
			return Response.status(409).build();
		}
	}

}
