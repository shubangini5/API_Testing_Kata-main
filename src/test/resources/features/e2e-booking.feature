@e2e @crud @booking @hotel-booking-regression
Feature: End-to-End Booking Flow

  In order to validate complete booking lifecycle
  As an authenticated user
  I want to create, retrieve, update, and delete a booking
  So that the full e2e flow is verified

  Background:
    Given the user is authenticated

  @e2e @crud @smoke
  Scenario: Create retrieve update and delete booking successfully

    When the user creates a booking with:
      | roomid | firstname | lastname | depositpaid | checkin    | checkout   | email         | phone        |
      | 2      | John      | David    | true        | 2028-09-01 | 2028-09-02 | john@test.com | 329876543210 |

    Then the booking should be created successfully

    When the user retrieves the created booking

    Then the response status code should be 200

    And the booking details should match:
      | roomid | firstname | lastname | depositpaid | checkin    | checkout   |
      | 2      | John      | David    | true        | 2028-09-01 | 2028-09-02 |

    When the user updates the booking with:
      | roomid | firstname | lastname | depositpaid | checkin    | checkout   | email         | phone        |
      | 2      | Jane      | Smith    | false       | 2028-09-03 | 2028-09-04 | jane@test.com | 329876543211 |

    Then the response status code should be 200

    When the user retrieves the created booking

    Then the response status code should be 200

    And the booking details should match:
      | roomid | firstname | lastname | depositpaid | checkin    | checkout   |
      | 2      | Jane      | Smith    | false       | 2028-09-03 | 2028-09-04 |

    When the user deletes the booking

    Then the response status code should be 202

    When the user retrieves the created booking

    Then the response status code should be 404