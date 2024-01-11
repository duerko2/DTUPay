package behaviourtests;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class DTUPayService {

    ClientBuilder builder = ClientBuilder.newBuilder().connectTimeout(2000, TimeUnit.MILLISECONDS).readTimeout(5000, TimeUnit.MILLISECONDS);
    Client client = builder.build();
    WebTarget r = client.target("http://localhost:9090/");

    public boolean pay(Payment p) {

        Response response;
        try {
            response = r.path("payments").request().post(Entity.entity(p, "application/json"));
        } catch (Exception e) {
            return false;
        }

        if (response.getStatus() == 200) {
            return true;
        } else {
            return false;
        }
    }
}
