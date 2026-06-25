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
  Scenario: User retrieve existing booking successfully

    When the user has an existing booking
      | roomid | firstname | lastname | depositpaid | checkin    | checkout   | email         | phone        |
      | 2      | John      | David    | true        | 2027-07-01 | 2027-07-02 | john@test.com | 329876543210 |
    Then the user views the booking
    And the booking details should match:
      | roomid | firstname | lastname | depositpaid | checkin    | checkout   |
      | 2      | John      | David    | true        | 2027-07-01 | 2027-07-02 |
    And the response matches with json schema "getBookingDetails.json"

  # =========================
  # Negative Scenarios
  # =========================

  @retrieve @negative
  Scenario Outline: User attempts to retrieve a booking that does not exist

    When the user views the booking ID "<bookingId>"
    Then the booking should not be found

    Examples:
      | bookingId	|
      | 999999   	|
      | -1       	|

  @retrieve @negative
  Scenario Outline: Unauthenticated user attempts to retrieve a booking <description>
    When the user retrieves an existing booking ID "<bookingId>" with "<action>" token
    Then the booking should not be found

    Examples:
      | description                   | action  | bookingId |
      | Missing authentication cookie | no      | 1         |
      | Invalid authentication cookie | invalid | 1         |

