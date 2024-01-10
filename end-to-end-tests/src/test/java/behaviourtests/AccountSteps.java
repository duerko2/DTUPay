package behaviourtests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AccountSteps {
    private Account account;
    private String name = "FirstName";
    private String lastName = "LastName";
    private String cpr = "1234567890";
    private String error;

    AccountRegistrationService accountRegistrationService = new AccountRegistrationService();



    @Given("an unregistered {string}")
    public void anUnregisteredCustomer(String string) {
        // Write code here that turns the phrase above into concrete actions
        account = new Account();
        account.setName(name);
        account.setLastname(lastName);
        account.setType(new AccountType(string));
        account.setCpr(cpr);

    }
    @When("the account is being registered")
    public void theAccountIsBeingRegistered() {
        // Write code here that turns the phrase above into concrete actions
        try {
            account = accountRegistrationService.register(account);
        } catch (Exception e) {
            error = e.getMessage();
        }
    }
    @Then("the account is registered")
    public void theAccountIsRegistered() {
        // Write code here that turns the phrase above into concrete actions
        assertNotNull(account.getAccountId());
    }
    @Then("has {int} tokens")
    public void hasTokens(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        assertEquals(int1,account.getTokens().size());
    }
    @After
    public void cleanUp(){
        if(account==null) return;
        accountRegistrationService.deleteAccount(account.getAccountId());
    }


    @Then("should get an error saying {string}")
    public void shouldGetAnErrorSaying(String error) {
        // Write code here that turns the phrase above into concrete actions
        assertEquals(error,this.error);
    }
}

