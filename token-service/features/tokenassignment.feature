Feature: Token assignment to account when created

  Scenario: Token Assignment
    When a "InitialTokensRequested" event for an account is received
    Then the "InitialTokensAssigned" event is sent
    And the account has 6 tokens

