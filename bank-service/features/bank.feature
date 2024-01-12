Feature: Bank Transfer Feature

  Scenario: Successfully transferring money from customer to merchant
    Given a customer has a valid bank account with sufficient funds,
    And the merchant has a valid bank account,
    When the customer initiates a payment to the merchant,
    Then the .... processes the bank transfer,
    And the payment is successful,
    And a "PaymentSuccessful" event is published with a payment ID.