Feature: Token assignment to account when created

  Scenario: Token Assignment
    When a "InitialTokensRequested" event for an account is received
    Then the "InitialTokensAssigned" event is sent
    And the account has 6 tokens

  Scenario: Token removal
    Given a valid payment with a valid token that exist
    When a "PaymentRequestSent" for a payment
    Then the "PaymentRequestValidated" event is sent with a payment
    And the token is deleted