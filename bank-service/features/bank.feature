Feature: Bank Service Feature

  Scenario: Successful handling of event
    Given a valid payment with merchantBankId, customerBankId and amount
    When a "BankAccountsAssigned" event is sent
    Then the request has been sent to the "Hubert" bank service
    And a "PaymentSuccessful" event is published

  ##Scenario: Unsuccessful handling of event
    #Given an invalid payment without merchantBankId...
    #When a "BankAccountsAssigned" event is sent
    #Then the request has not been sent to the Hubert bank service
