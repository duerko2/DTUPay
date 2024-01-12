package bank.service;

import dtu.ws.fastmoney.BankServiceService;
import messaging.implementations.RabbitMqQueue;

public class StartUp {
    public static void main(String[] args) throws Exception {
        new StartUp().startUp();
    }

    private void startUp() throws Exception {
        System.out.println("startup");
        var mq = new RabbitMqQueue("rabbitMq");
        var bank = new BankServiceService().getBankServicePort();
        new DTUPayBankService(mq,bank);
    }
}
