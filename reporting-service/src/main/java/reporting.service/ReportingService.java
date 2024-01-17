package reporting.service;

import java.util.ArrayList;
import java.util.List;

import messaging.Event;
import messaging.MessageQueue;




public class ReportingService {

    private MessageQueue queue;

    public ManagerReport CreateManagerReport() {

        ManagerReport managerReport = new ManagerReport();
        List<Payment> paymentList = new ArrayList<>();
        int sum = 0;

        //TODO:retrieve the full payment list via payment repo???, go through all payments, add up sum, think about how to make summary
        //A reporting function for the manager or DTU Pay to see all payments and a summary of all
        //the money being transfered. For simplicity it is assumed that there is only one manager. That
        //means, there is no need for managing manager accounts.

        managerReport.setPaymentList(paymentList);

        for (int i = 0; i < paymentList.size(); i++) {
            sum = sum + paymentList.get(i).amount;
        }



        return managerReport;
    }

    public CustomerReport CreateCustomerReport(String CustomerId) {

        CustomerReport customerReport = new CustomerReport();
        //TODO: retrieve the full payment list via payment repo
        List<Payment> paymentList = null;

        for (int i = 0; i < paymentList.size(); i++) {

        }

        customerReport.setPaymentList(paymentList);



        return customerReport;
    }

    public MerchantReport CreateMerchantReport(String merchantId) {

        MerchantReport merchantReport = new MerchantReport();
        //TODO: retrieve the full payment list via payment repo
        List<Payment> paymentList = null;



        return merchantReport;
    }

}