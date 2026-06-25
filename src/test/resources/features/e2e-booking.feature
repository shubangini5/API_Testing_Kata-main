@e2e @crud @booking @hotel-booking-regression
Feature: End-to-end hotel booking journey

  In order to manage a hotel reservation
  As an authenticated user
  So that I can create, view, update, and cancel my booking

  Background:
    Given the user is authenticated

  @e2e @positive @smoke
  Scenario: User completes the full booking journey

    When the user creates a booking with:
      | roomid | firstname | lastname | depositpaid | checkin    | checkout   | email          | phone        |
      | 1      | Peter     | Daemon   | true        | 2028-01-10 | 2028-01-11 | Peter@test.com | 329876543210 |
    Then the booking should be created successfully
    And the user should receive a booking ID

    When the user views the booking
    Then the booking details should match:
      | roomid | firstname | lastname | depositpaid | checkin    | checkout   |
      | 1      | Peter     | Daemon   | true        | 2028-01-10 | 2028-01-11 |

    When the user updates the booking dates:
      | roomid | firstname | lastname | depositpaid | checkin    | checkout   | email         | phone        |
      | 1      | Peter     | Daemon    | true        | 2028-09-03 | 2028-09-04 | John@test.com | 329876543210 |
    Then the booking should be updated successfully

    When the user views the booking
    Then the booking details should match:
      | roomid | firstname | lastname | depositpaid | checkin    | checkout   |
      | 1      | Peter     | Daemon    | true        | 2028-09-03 | 2028-09-04 |

    When the user deletes the booking
    Then the booking should be removed successfully

    When the user views the booking
    Then the booking should not be found