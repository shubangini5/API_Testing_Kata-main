@partial-update @booking @hotel-booking-regression
Feature: Partially update a hotel booking

  In order to modify selected booking information
  As a user
  So that I can keep my reservation details up to date

  Background:
    Given the user is authenticated

  @partial-update @negative
  Scenario: Partial update booking not allowed

    When the user has an existing booking
      | roomid | firstname | lastname | depositpaid | checkin    | checkout   | email         | phone        |
      | 2      | John      | David    | true        | 2027-06-01 | 2027-06-02 | john@test.com | 329876543210 |

    Then the user partially updates the booking with:
      | firstname | lastname |
      | Dan       | Sam      |

    And the booking detail should be updated successfully

