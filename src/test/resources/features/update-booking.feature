@update @booking @hotel-booking-regression
Feature: Update Booking

  In order to keep my reservation details up to date
  As an authenticated user
  I want to update existing bookings
  So that reservation information can be maintained

  Background:
    Given the user is authenticated

  # =========================
  # Positive Scenarios
  # =========================

  @update @positive @smoke
  Scenario: User updates an existing booking
    Given the user has an existing booking
      | roomid | firstname | lastname | depositpaid | checkin    | checkout   | email        | phone        |
      | 2      | Sam       | Max      | true        | 2027-06-11 | 2027-06-12 | Sam@test.com | 329876543210 |
    When the user updates the booking dates:
      | roomid | firstname | lastname | depositpaid | checkin    | checkout   | email        | phone        |
      | 2      | Sam       | Max      | true        | 2027-06-28 | 2027-06-29 | Sam@test.com | 329876543210 |
    Then the booking should be updated successfully

  @update @positive
  Scenario: User updates the guest information
    Given the user has an existing booking
      | roomid | firstname | lastname | depositpaid | checkin    | checkout   | email      | phone        |
      | 2      | Sam       | Max      | true        | 2027-06-15 | 2027-06-16 | Sam@test.com | 329876543210 |
    When the user updates the guest information:
      | roomid | firstname | lastname | depositpaid | checkin    | checkout   | email        | phone        |
      | 2      | Jam       | Martin   | true        | 2027-06-26 | 2027-06-27 | Jam@test.com | 329876543999 |
    Then the booking should be updated successfully
  # =========================
  # Negative Scenarios
  # =========================

  @update @negative
  Scenario Outline: User updates a booking with invalid details <description>

    Given the user has an existing booking
      | roomid | firstname | lastname | depositpaid | checkin    | checkout  | email         | phone        |
      | 2      | Janet     | Smith    | false       | 2027-02-01 | 2027-02-02 | jane@test.com | 329876543211 |
    When the user provides invalid details:
      | roomid   | firstname   | lastname   | depositpaid   | checkin   | checkout   | email   | phone   |
      | <roomid> | <firstname> | <lastname> | <depositpaid> | <checkin> | <checkout> | <email> | <phone> |
    Then the update should fail

    Examples:
      | description       | roomid | firstname | lastname | depositpaid | checkin    | checkout   | email         | phone        | statusCode | message                            |
      | (Invalid room id) | -1     | Johney    | David    | true        | 2027-03-01 | 2027-03-02 | john@test.com | 329876543210 | 400        | must be greater than or equal to 1 |


  @update @negative
  Scenario Outline: Unauthenticated user attempts to update a booking <description>
    When the user updates an existing booking ID "<bookingId>" with "<action>" token
      | roomid   | firstname   | lastname   | depositpaid   | checkin   | checkout   | email   | phone   |
      | <roomid> | <firstname> | <lastname> | <depositpaid> | <checkin> | <checkout> | <email> | <phone> |
    Then the update should be denied
    Examples:
      | description                   | action  | bookingId | roomid | firstname | lastname | depositpaid | checkin    | checkout   | email        | phone        |
      | Missing authentication cookie | no      | 1         | 2      | Jam       | Martin   | true        | 2027-06-26 | 2027-06-27 | Jam@test.com | 329876543999 |
      | Invalid authentication cookie | invalid | 1         | 2      | Jam       | Martin   | true        | 2027-06-26 | 2027-06-27 | Jam@test.com | 329876543999 |
