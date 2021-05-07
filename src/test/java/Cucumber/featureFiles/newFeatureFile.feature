Feature: New Feature

  Scenario Outline: test scenario_<TestCase_ID>
  Given User Login to the Appilcation "<data_source>"
  
  Examples:
  | TestCase_ID | data_source     |
  |  TC001      | testData.data_1 |
  |  TC002      | testData.data_2 |
  
  