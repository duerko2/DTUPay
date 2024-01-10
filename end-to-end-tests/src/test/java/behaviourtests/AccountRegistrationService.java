package behaviourtests;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;

public class AccountRegistrationService {

	public Account register(Account c) {
		Client client = ClientBuilder.newClient();
		WebTarget r = client.target("http://localhost:8080/");
		var response = r.path("accounts").request().post(Entity.json(c), Account.class);
		return response;
	}

	public void deleteAccount(String accountId) {
		Client client = ClientBuilder.newClient();
		WebTarget r = client.target("http://localhost:8080/");
		r.path("accounts").path(accountId).request().delete();
	}
}
