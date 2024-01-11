Feature: Payment

  Scenario: Successful Payment
    Given there is a payment with 1 token merchantId "merchant" and amount 100
    When the payment is requested
    Then the "PaymentRequestSent" event is sent
    And the "PaymentSuccessful" event is sent with a merchantId and customerId and a paymentId
    Then the payment is stored in the successful payments ledger

