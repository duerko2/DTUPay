package accountregistration.service.adapter.rest;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import accountregistration.service.Account;
import accountregistration.service.AccountDeletionService;
import accountregistration.service.AccountRegistrationService;
import accountregistration.service.Student;

@Path("/accounts")
public class AccountResource {

	AccountServiceFactory factory = new AccountServiceFactory();
	AccountRegistrationService registrationService = factory.getRegistrationService();
	AccountDeletionService deletionService = factory.getDeletionService();


	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Account registerAccount(Account account) {
		return registrationService.register(account);
	}

	@DELETE
	@Path("/{accountId}")
	public Response deleteAccount(@PathParam("accountId") String accountId) {

		deletionService.deleteAccount(accountId);
		return Response.ok().build();
	}
}
