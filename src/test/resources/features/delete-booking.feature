@delete @booking @hotel-booking-regression
Feature: Delete Booking

  In order to remove unwanted reservations
  As an authenticated user
  I want to delete bookings
  So that obsolete reservations can be removed

  Background:
    Given the user is authenticated

  # =========================
  # Positive Scenarios
  # =========================

  @delete @positive @smoke
  Scenario: Delete booking - Existing booking

    When the user creates a booking with:
      | roomid | firstname | lastname | depositpaid | checkin    | checkout   | email            | phone        |
      | 2      | Patrick   | David    | true        | 2028-08-01 | 2028-08-02 | patrick@test.com | 329876543210 |

    Then the booking should be created successfully

    When the user deletes the booking
    Then the response status code should be 202

  # =========================
  # Negative Scenarios
  # =========================

  @delete @negative
  Scenario: Delete booking failure - Already deleted booking

    When the user creates a booking with:
      | roomid | firstname | lastname | depositpaid | checkin    | checkout   | email            | phone        |
      | 2      | Patrick   | David    | true        | 2028-08-05 | 2028-08-06 | patrick@test.com | 329876543210 |

    Then the booking should be created successfully

    When the user deletes the booking
    Then the response status code should be 202

    When the user attempts to delete the booking again
    Then the response status code should be 404

  @delete @negative
  Scenario Outline: Delete booking failures - <description>

    When the user performs delete "<action>" on booking "<bookingId>"

    Then the response status code should be <statusCode>

    Examples:
      | description                   | action       | bookingId | statusCode |
      | Missing authentication cookie | noToken      | 1         | 403        |
      | Invalid authentication cookie | invalidToken | 1         | 403        |
      | Non-existing booking          | validToken   | 999999    | 404        |
      | Invalid booking ID            | validToken   | -1        | 404        |