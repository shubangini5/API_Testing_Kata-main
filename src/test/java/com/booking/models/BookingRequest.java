package com.booking.models;

public class BookingRequest {

    private Integer roomid;
    private String firstname;
    private String lastname;
    private Boolean depositpaid;
    private BookingDates bookingdates;
    private String email;
    private String phone;

    private BookingRequest() {

    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final BookingRequest request;

        private Builder() {
            this.request = new BookingRequest();
        }

        public Builder roomid(Integer roomid) {
            request.roomid = roomid;
            return this;
        }

        public Builder firstname(String firstname) {
            request.firstname = firstname;
            return this;
        }

        public Builder lastname(String lastname) {
            request.lastname = lastname;
            return this;
        }

        public Builder depositpaid(Boolean depositpaid) {
            request.depositpaid = depositpaid;
            return this;
        }

        public Builder bookingdates(BookingDates bookingdates) {
            request.bookingdates = bookingdates;
            return this;
        }

        public Builder email(String email) {
            request.email = email;
            return this;
        }

        public Builder phone(String phone) {
            request.phone = phone;
            return this;
        }

        public BookingRequest build() {
            return request;
        }
    }


    public Integer getRoomid() {
        return roomid;
    }

    public void setRoomid(Integer roomid) {
        this.roomid = roomid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Boolean getDepositpaid() {
        return depositpaid;
    }

    public void setDepositpaid(Boolean depositpaid) {
        this.depositpaid = depositpaid;
    }

    public BookingDates getBookingdates() {
        return bookingdates;
    }

    public void setBookingdates(BookingDates bookingdates) {
        this.bookingdates = bookingdates;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}