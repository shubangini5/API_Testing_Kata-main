@retrieve @booking @hotel-booking-regression
Feature: Retrieve Booking

  In order to view reservation details
  As an authenticated user
  I want to retrieve booking information
  So that I can verify existing reservations

  Background:
    Given the user is authenticated
  # =========================
  # Positive Scenarios
  # =========================

  @retrieve @positive @smoke
  Scenario: Retrieve existing booking successfully

    When the user creates a booking with:
      | roomid | firstname | lastname | depositpaid | checkin    | checkout   | email         | phone        |
      | 2      | John      | David    | true        | 2027-07-01 | 2027-07-02 | john@test.com | 329876543210 |

    Then the booking should be created successfully

    When the user retrieves the created booking

    Then the booking details should match:
      | roomid | firstname | lastname | depositpaid | checkin    | checkout   |
      | 2      | John      | David    | true        | 2027-07-01 | 2027-07-02 |

  # =========================
  # Negative Scenarios
  # =========================

  @retrieve @negative
  Scenario Outline: Retrieve booking failures - <description>

    When the user performs "<action>" on booking "<bookingId>"

    Then the response status code should be <statusCode>

    And the error message contains "<message>"

    Examples:
      | description                   | action       | bookingId | statusCode | message   |
      | Non-existing booking          | retrieve     | 999999    | 404        |           |
      | Invalid booking ID            | retrieve     | -1        | 404        | Not Found |
      | Missing authentication cookie | noToken      | 1         | 403        |           |
      | Invalid authentication cookie | invalidToken | 1         | 403        |           |

