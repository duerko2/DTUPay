package behaviourtests;

import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceException_Exception;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PaymentSteps {

    String customerName = "name";
    String customerLastName = "lastname";
    String customerCPR = "987";
    String merchantName = "merchantName";
    String merchantLastName = "merchantlastname";
    String merchantCPR = "654";
    String customerBankId;
    String customerDTUPayId;
    String merchantBankId;
    String merchantDTUPayId;
    boolean successful;
    String errorMessage;

    AccountRegistrationService accountRegistrationService = new AccountRegistrationService();
    DTUPayService dtuPayService = new DTUPayService();
    private Token token;

    @Given("a customer with a bank account with balance {int}")
    public void aCustomerWithABankAccountWithBalance(int balance) {
        // CreateBankAccount
        customerBankId = dtuPayService.registerBankAccount(customerName , customerLastName, customerCPR, balance );


    }

    @And("that the customer is registered with DTU Pay")
    public void thatTheCustomerIsRegisteredWithDTUPay() throws AccountAlreadyExists, NoSuchAccountException{
        Account account = new Account();
        account.setName(customerName);
        account.setLastname(customerLastName);
        account.setCpr(customerCPR);
        account.setBankId(customerBankId);
        account.setType(new AccountType("customer"));

        customerDTUPayId = accountRegistrationService.register(account).getAccountId();

        assertEquals(customerDTUPayId,accountRegistrationService.getAccount(customerDTUPayId).getAccountId());
    }

    @Given("a merchant with a bank account with balance {int}")
    public void aMerchantWithABankAccountWithBalance(int balance) {
        // CreateBankAccount mock
        customerBankId = dtuPayService.registerBankAccount(merchantName , merchantLastName, merchantCPR, balance );
    }

    @And("that the merchant is registered with DTU Pay")
    public void thatTheMerchantIsRegisteredWithDTUPay() throws AccountAlreadyExists, NoSuchAccountException{
        Account account = new Account();
        account.setName(merchantName);
        account.setLastname(merchantLastName);
        account.setCpr(merchantCPR);
        account.setBankId(merchantBankId);
        account.setType(new AccountType("merchant"));

        merchantDTUPayId = accountRegistrationService.register(account).getAccountId();

        assertEquals(merchantDTUPayId,accountRegistrationService.getAccount(merchantDTUPayId).getAccountId());
    }

    @When("the merchant initiates a payment for {int} kr with the customer token")
    public void theMerchantInitiatesAPaymentForKrByTheCustomer(int arg0) throws Throwable {
        Payment p = new Payment();
        p.setAmount(arg0);
        p.setMerchantId(merchantDTUPayId);
        p.setToken(token);

        successful = dtuPayService.pay(p);
    }

    @Then("the payment is successful")
    public void thePaymentIsSuccessful() {
        assertTrue(successful);
    }

    @And("the balance of the customer at the bank is {int} kr")
    public void theBalanceOfTheCustomerAtTheBankIsKr(int balance) {

        try {
            assertEquals(balance ,dtuPayService.getBankAccount( customerBankId).getBalance().intValue());
        } catch (BankServiceException_Exception e) {
            throw new RuntimeException(e);
        }

    }

    @And("the balance of the merchant at the bank is {int} kr")
    public void theBalanceOfTheMerchantAtTheBankIsKr(int balance) {
        try {
            assertEquals(balance, dtuPayService.getBankAccount(merchantBankId).getBalance().intValue());
        }catch (BankServiceException_Exception e){

        }

    }

    @Given("a customer has at least {int} token")
    public void aCustomerHasAtLeastToken(int arg0) throws NoSuchAccountException{
        List<Token> tokens = accountRegistrationService.getAccount(customerDTUPayId).getTokens();
        assertTrue(tokens.size() >= arg0);
        token = tokens.get(0);
    }

    @After
    public void tearDown(){
        accountRegistrationService.deleteAccount(customerDTUPayId);
        accountRegistrationService.deleteAccount(merchantDTUPayId);
        // Delete bank accounts
        try {
            dtuPayService.deleteBankAccount(customerBankId);
        }catch (Exception e){

        }
        try {
            dtuPayService.deleteBankAccount(merchantBankId);
        }catch (Exception e){

        }
    }
}
