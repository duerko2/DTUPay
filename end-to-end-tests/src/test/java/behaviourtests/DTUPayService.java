package behaviourtests;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;

public class DTUPayService {

    Client client = ClientBuilder.newClient();
    WebTarget r = client.target("http://localhost:9090/");

    public boolean pay(Payment p) {
        var response = r.path("payments").request().post(Entity.entity(p, "application/json"));
        if(response.getStatus() == 200) {
            return true;
        } else {
            return false;
        }
    }
}
