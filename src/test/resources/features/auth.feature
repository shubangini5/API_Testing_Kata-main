@authentication @login
Feature: Hotel booking login

  In order to access the hotel booking system
  As a guest or hotel manager
  I want to log in using valid credentials

  # =========================
  # Positive Scenarios
  # =========================

  @positive @smoke
  Scenario: Login successfully with valid credentials
    Given the user has valid admin credentials
    When the user sends a login request
    Then the response status code should be 200
    And the response contains an authentication token

  # =========================
  # Negative Scenarios
  # =========================
  @negative
  Scenario Outline: Invalid login scenarios <delimiter> <testCase>
    Given the user provides username "<username>" and password "<password>"
    When the user sends a login request
    Then the response status code should be <statusCode>
    And the error message contains "<message>"

    Examples:
      | testCase         |  | username | password      | statusCode | message             | delimiter |
      | Invalid password |  | admin    | wrongpassword | 401        | Invalid credentials | -		  |
      | Invalid username |  | wrong    | password      | 401        | Invalid credentials | -		  |
      | Empty username   |  |          | password      | 401        | Invalid credentials | -		  |
      | Empty password   |  | admin    |               | 401        | Invalid credentials | -		  |

  @negative
  Scenario: Login with invalid HTTP method
    When the user sends a GET request to the login endpoint
    Then the response status code should be 405
    And the response body should be empty