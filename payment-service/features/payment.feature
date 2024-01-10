Feature: Payment

  Scenario: Successful Payment
    Given there is a payment with 1 token merchantId "merchant" and amount 100
    When the payment is requested
    Then the "PaymentRequestSent" event is sent
    When the "PaymentRequestValidated" event is sent with a customerId
    Then the payment is registered and customerId is set
