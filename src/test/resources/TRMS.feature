Feature: The entire app works

Scenario Outline: Logging in as an employee works
	Given I am on the login page
	When I enter my "<username>" and "<password>"
	And I click the login button
	Then the navbar should appear and I should be able to make new claims
	
		Examples:
			| username | password |
			| testE    | password |
			
Scenario Outline: As an employee I can make new claims
	Given I am logged in as an employee
	When I click make a claim
	And I complete the form with "<eventName>", "<eventTypeSelection>", "<eventDate>", "<eventTime>", "<eventLocation>", "<passingPercentage>", "<passingLetterSelection>", "<hasPresentation>", "<description>", "<justification>", "<hoursMissed>", "<cost>"
	And I click submit
	Then The claim should exist
	
		Examples:
			| eventName | eventTypeSelection | eventDate | eventTime | eventLocation | passingPercentage | passingLetterSelection | hasPresentation | description | justification | hoursMissed | cost |
			| test event | 1 | 04092021 | 200p |test location | 50 | 1 | Y | test description | test justification | 80 | 120 |
			| test event 2 | 2 | 12172020 | 300p | test location 2 | 80 | 3 | N | test description 2 | test justification 2 | 40 | 5000 |
			
#Scenario Outline: As a direct supervisor I can login and comment on claims
#	Given I am on the login page
#	When I enter my "<username>" and "<password>"
#	And I click the login button
#	And I select an urgent claim
#	And I click accept
#	Then the claim should no longer be there
#	
#		Examples:
#			| username | password |
#			| testDS   | password |
			
