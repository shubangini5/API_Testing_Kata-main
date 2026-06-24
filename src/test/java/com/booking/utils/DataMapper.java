package com.booking.utils;

import com.booking.models.BookingDates;
import com.booking.models.BookingRequest;
import io.cucumber.datatable.DataTable;

import java.util.Map;

public class DataMapper {

    public static BookingRequest mapToBookingRequest(DataTable dataTable) {

        Map<String, String> row = dataTable.asMaps(String.class, String.class).get(0);

        var builder = BookingRequest.builder();

        // Set bookingdates only if checkin or checkout are not null
        BookingDates bookingDates = new BookingDates();
        if (normalize(row.get("checkin")) != null) {
            bookingDates.setCheckin(normalize(row.get("checkin")));
        }
        if (normalize(row.get("checkout")) != null) {
            bookingDates.setCheckout(normalize(row.get("checkout")));
        }
        if (normalize(row.get("checkin")) != null || normalize(row.get("checkout")) != null) {
            builder.bookingdates(bookingDates);
        }

        // Set roomid only if not null
        if (normalize(row.get("roomid")) != null) {
            builder.roomid(Integer.parseInt(row.get("roomid")));
        }

        // Set firstname only if not null
        if (normalize(row.get("firstname")) != null) {
            builder.firstname(normalize(row.get("firstname")));
        }

        // Set lastname only if not null
        if (normalize(row.get("lastname")) != null) {
            builder.lastname(normalize(row.get("lastname")));
        }

        // Set depositpaid only if not null
        if (normalize(row.get("depositpaid")) != null) {
            builder.depositpaid(Boolean.parseBoolean(normalize(row.get("depositpaid"))));
        }

        // Set email only if not null
        if (normalize(row.get("email")) != null) {
            builder.email(normalize(row.get("email")));
        }

        // Set phone only if not null
        if (normalize(row.get("phone")) != null) {
            builder.phone(normalize(row.get("phone")));
        }

        return builder.build();
    }

    private static String normalize(String value) {
        if (value == null ||
                "[null]".equalsIgnoreCase(value)) {
            return null;
        }
        else if(value.trim().isEmpty() ||
                "[empty]".equalsIgnoreCase(value) ){
            return "";
        }

        return value;
    }
}