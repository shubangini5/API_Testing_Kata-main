@delete @booking @hotel-booking-regression
Feature: Delete Booking

  In order to cancel a reservation
  As an authenticated user
  I want to delete bookings
  So that I can manage my bookings

  Background:
    Given the user is authenticated

  # =========================
  # Positive Scenarios
  # =========================

  @delete @positive @smoke
  Scenario: User deletes an existing booking
    When the user has an existing booking
      | roomid | firstname | lastname | depositpaid | checkin    | checkout   | email            | phone        |
      | 2      | Patrick   | David    | true        | 2028-08-01 | 2028-08-02 | patrick@test.com | 329876543210 |
    Then the user deletes the booking
    And the booking should be removed successfully

  # =========================
  # Negative Scenarios
  # =========================

  @delete @negative
  Scenario: User attempts to delete a booking that does not exist

    When the user has an existing booking
      | roomid | firstname | lastname | depositpaid | checkin    | checkout   | email            | phone        |
      | 2      | Patrick   | David    | true        | 2028-08-05 | 2028-08-06 | patrick@test.com | 329876543210 |
    Then the user deletes the booking
    And the booking should be removed successfully
    Then the user deletes the booking again
    And the deletion should fail


  @delete @negative
  Scenario Outline: Unauthenticated user attempts to delete a booking <description>
    When the user deletes an existing booking ID "<bookingId>" with "<action>" token
    Then the deletion should be denied

    Examples:
      | description                   | action  | bookingId | statusCode |
      | Missing authentication cookie | no      | 1         | 403        |
      | Invalid authentication cookie | invalid | 1         | 403        |
      | Non-existing booking          | valid   | 999999    | 404        |
      | Invalid booking ID            | valid   | -1        | 404        |

