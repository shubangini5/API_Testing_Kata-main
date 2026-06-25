@create @booking @hotel-booking-regression
Feature: Create Booking

  In order to reserve hotel rooms
  As a hotel guest
  So that I can stay at the hotel on my selected dates

  Background:
    Given the user is authenticated

  # =========================
  # Positive Scenarios
  # =========================

  @create @positive @smoke
  Scenario: User creates a booking with valid details
    When the user creates a booking with:
      | roomid | firstname | lastname | depositpaid | checkin    | checkout   | email         | phone        |
      | 1      | John      | David    | true        | 2027-01-10 | 2027-01-11 | john@test.com | 329876543210 |
    Then the booking should be created successfully
    And the user should receive a booking ID

  @create @positive
  Scenario Outline: User creates bookings for different guests
    When the user creates a booking with:
      | roomid   | firstname   | lastname   | depositpaid | checkin   | checkout   | email   | phone   |
      | <roomid> | <firstname> | <lastname> | true        | <checkin> | <checkout> | <email> | <phone> |
    Then the booking should be created successfully
    And the user should receive a booking ID

    Examples:
      | description                  | roomid | firstname | lastname | checkin    | checkout   | email          | phone        |
      | First user books room        | 1      | John      | David    | 2027-02-01 | 2027-02-02 | john@test.com  | 329876543210 |
      | Second user books room       | 2      | Jane      | Smith    | 2027-02-03 | 2027-02-04 | jane@test.com  | 329876543211 |
      | Third user books room        | 3      | Peter     | Parker   | 2027-02-05 | 2027-02-06 | peter@test.com | 329876543212 |

  @create @positive @boundary
  Scenario: User creates consecutive bookings for the same room
    When the user creates a booking with:
      | roomid | firstname | lastname | depositpaid | checkin    | checkout   | email         | phone        |
      | 1      | John      | David    | true        | 2027-02-10 | 2027-02-11 | john@test.com | 329876543210 |
    Then the booking should be created successfully
    And the user should receive a booking ID
    When the user creates a booking with:
      | roomid | firstname | lastname | depositpaid | checkin    | checkout   | email         | phone        |
      | 1      | John      | David    | true        | 2027-02-11 | 2027-02-12 | john@test.com | 329876543210 |
    Then the booking should be created successfully
    And the user should receive a booking ID

  # =========================
  # Negative Scenarios
  # =========================

  @create @negative @validation
  Scenario Outline: User cannot create a booking with invalid details <description>
    When the user creates a booking with invalid data:
      | roomid   | firstname   | lastname   | depositpaid   | checkin   | checkout   | email   | phone   |
      | <roomid> | <firstname> | <lastname> | <depositpaid> | <checkin> | <checkout> | <email> | <phone> |
    Then the booking should not be created
    And the user should see the error message "<message>"

    Examples:
      | description                       | roomid | firstname            | lastname                          | depositpaid | checkin    | checkout   | email         | phone                  | statusCode | message                             |
      | (Missing room id)                 | [null] | John                 | David                             | true        | 2027-03-01 | 2027-03-02 | john@test.com | 329876543210           | 409        | Failed to create booking            |
      | (Invalid room id)                 | -1     | John                 | David                             | true        | 2027-03-01 | 2027-03-02 | john@test.com | 329876543210           | 400        | must be greater than or equal to 1  |
      | (First name below minimum length) | 1      | Jo                   | David                             | true        | 2027-03-05 | 2027-03-06 | john@test.com | 329876543210           | 400        | size must be between 3 and 18       |
      | First name above maximum length   | 1      | ThisNameIsTooLongNow | David                             | true        | 2027-03-07 | 2027-03-08 | john@test.com | 329876543210           | 400        | size must be between 3 and 18       |
      | Last name below minimum length    | 1      | John                 | Da                                | true        | 2027-03-11 | 2027-03-12 | john@test.com | 329876543210           | 400        | size must be between 3 and 30       |
      | Last name above maximum length    | 1      | John                 | ThisLastNameIsMoreThanThirtyChars | true        | 2027-03-13 | 2027-03-14 | john@test.com | 329876543210           | 400        | size must be between 3 and 30       |
      | Empty email                       | 1      | John                 | David                             | true        | 2027-03-15 | 2027-03-16 | [empty]       | 329876543210           | 400        | must not be empty                   |
      | Invalid email address             | 1      | John                 | David                             | true        | 2027-03-17 | 2027-03-18 | invalidEmail  | 329876543210           | 400        | must be a well-formed email address |
      | Empty phone number                | 1      | John                 | David                             | true        | 2027-03-19 | 2027-03-20 | john@test.com | [empty]                | 400        | must not be empty                   |
      | Phone below minimum length        | 1      | John                 | David                             | true        | 2027-03-21 | 2027-03-22 | john@test.com | 123                    | 400        | size must be between 11 and 21      |
      | Phone above maximum length        | 1      | John                 | David                             | true        | 2027-03-23 | 2027-03-24 | john@test.com | 1234567890123456789012 | 400        | size must be between 11 and 21      |
      | Checkout before checkin           | 1      | John                 | David                             | true        | 2027-03-27 | 2027-03-25 | john@test.com | 329876543210           | 409        | Failed to create booking            |
      | Empty checkin date                | 1      | John                 | David                             | true        | [empty]    | 2027-03-28 | john@test.com | 329876543210           | 400        | must not be null                    |
      | Empty checkout date               | 1      | John                 | David                             | true        | 2027-03-29 | [empty]    | john@test.com | 329876543210           | 400        | must not be null                    |

  @create @negative @boundary
  Scenario: User cannot create an overlapping booking for the same room
    When the user creates a booking with:
      | roomid | firstname | lastname | depositpaid | checkin    | checkout   | email         | phone        |
      | 2      | John      | David    | true        | 2027-04-01 | 2027-04-03 | john@test.com | 329876543210 |
    Then the booking should be created successfully
    And the user should receive a booking ID
    When the user tries to book the same room again on overlapping days
      | roomid | firstname | lastname | depositpaid | checkin    | checkout   | email          | phone        |
      | 2      | Peter     | Parker   | true        | 2027-04-02 | 2027-04-04 | peter@test.com | 329876543211 |
    Then the booking request should be rejected
    And the user should see the error message "Failed to create booking"

  @create @negative
  Scenario: User attempts to create a booking using an unsupported operation
    When the user attempts to create a booking using an unsupported request method
      | roomid | firstname | lastname | depositpaid | checkin    | checkout   | email           | phone        |
      | 2      | Parker    | Peter    | true        | 2027-01-21 | 2027-01-22 | parker@test.com | 329876543211 |
    Then the booking request should be rejected
    And no response should be returned