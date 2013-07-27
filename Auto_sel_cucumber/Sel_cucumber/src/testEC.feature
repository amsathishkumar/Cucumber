Feature: Source Creation

Scenario: Create Source with CH

Given EC is open in CH
When I Navigate to Source Page and enter valid values
And I click on Save button
Then I See success message
