Feature: Student Registration feature

  Scenario: Customer Account Registration
  	Given an unregistered customer
  	When the account is being registered
  	Then the account is registered
  	And has 6 tokens