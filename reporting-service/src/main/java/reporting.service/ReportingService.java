package reporting.service;
public class ReportingService {

    public ManagerReport getManagerReport() {

        return new ManagerReport();
    }

    public CustomerReport getCustomerReport(String CustomerId) {

        return new CustomerReport();
    }

    public MerchantReport getMerchantReport(String merchantId) {

        return new MerchantReport();
    }

}