Feature: Account Registration feature

	Scenario: Customer Account Registration
		Given an unregistered "customer"
		When the account is being registered
		Then the account is registered
		And has 6 tokens

	Scenario: Merchant Account Registration
		Given an unregistered "merchant"
		When the account is being registered
		Then the account is registered


	Scenario: Double account registration
		Given an unregistered "customer"
		When the account is being registered
		And the account is being registered
		Then should get an error saying "that account already exists"
		And the account is registered
