package payment.service.adapter.rest;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import payment.service.Payment;
import payment.service.PaymentService;

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
			return Response.ok(p).build();
		} catch (Exception e) {
			return Response.status(409).build();
		}
	}

}
