@partial-update @booking @hotel-booking-regression
Feature: Partial Update Booking

  Background:
    Given the user is authenticated

  @partial-update @negative
  Scenario: Partial update booking not allowed

    When the user creates a booking with:
      | roomid | firstname | lastname | depositpaid | checkin    | checkout   | email         | phone        |
      | 2      | John      | David    | true        | 2027-06-01 | 2027-06-02 | john@test.com | 329876543210 |

    Then the booking should be created successfully

    When the user partially updates the booking with:
      | firstname | lastname |
      | Dan       | Sam      |

    Then the response status code should be 405

