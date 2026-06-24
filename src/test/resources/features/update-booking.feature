@update @booking @hotel-booking-regression
Feature: Update Booking

  In order to modify reservation details
  As an authenticated user
  I want to update existing bookings
  So that reservation information can be maintained

  Background:
    Given the user is authenticated

  @update @positive @smoke
  Scenario: Update booking - Successful update

    When the user creates a booking with:
      | roomid | firstname | lastname | depositpaid | checkin    | checkout   | email         | phone        |
      | 2      | Johny     | David    | true        | 2027-06-01 | 2027-06-02 | john@test.com | 329876543210 |

    Then the booking should be created successfully

    When the user updates the booking with:
      | roomid | firstname | lastname | depositpaid | checkin    | checkout   | email         | phone        |
      | 2      | Jam       | Martin   | false       | 2027-06-03 | 2027-06-04 | jane@test.com | 329876543211 |

    Then the response status code should be 200

  @update @negative
  Scenario Outline: Update booking failures - <description>

    When the user performs update "<action>" on booking "<bookingId>" with:
      | roomid | firstname | lastname | depositpaid | checkin    | checkout   | email         | phone        |
      | 2      | Jane      | Smith    | false       | 2027-06-01 | 2027-06-02 | jane@test.com | 329876543211 |

    Then the response status code should be <statusCode>

    Examples:
      | description                   | action       | bookingId | statusCode |
      | Invalid booking ID            | validToken   | -1        | 404        |
      | Missing authentication cookie | noToken      | 1         | 403        |
      | Invalid authentication cookie | invalidToken | 1         | 403        |

  @update @negative
  Scenario: Update booking failures - Overlapping dates

    When the user creates a booking with:
      | roomid | firstname | lastname | depositpaid | checkin    | checkout   | email         | phone        |
      | 2      | John      | David    | true        | 2027-06-10 | 2027-06-12 | john@test.com | 329876543210 |

    Then the booking should be created successfully

    When the user updates the booking with:
      | roomid | firstname | lastname | depositpaid | checkin    | checkout   | email         | phone        |
      | 2      | Jane      | Smith    | false       | 2027-06-11 | 2027-06-13 | jane@test.com | 329876543211 |

    Then the response status code should be 409