@authentication @login
Feature: Hotel booking login

  In order to access the hotel booking system
  As a guest or hotel manager
  So that I can manage the bookings

  # =========================
  # Positive Scenarios
  # =========================

  @positive @smoke
  Scenario: User logs in with valid credentials
    Given the user has valid credentials
    When the user logs in
    Then the user should be authenticated
    And the response contains an authentication token

  # =========================
  # Negative Scenarios
  # =========================
  @negative
  Scenario Outline: User logs in with invalid credentials <delimiter> <testCase>
    Given the user has credentials with username "<username>" and password "<password>"
    When the user logs in
    Then the authentication should fail
    And the user should see the error message "<message>"

    Examples:
      | testCase         |username | password      | message             | delimiter |
      | Invalid password |admin    | wrongpassword | Invalid credentials | -         |
      | Invalid username |wrong    | password      | Invalid credentials | -         |
      | Empty username   |         | password      | Invalid credentials | -         |
      | Empty password   |admin    |               | Invalid credentials | -         |

  @negative
  Scenario: User attempts to authenticate using an unsupported operation
    When the user attempts to authenticate using an unsupported request method
    Then the authentication request should be rejected
    And no response should be returned