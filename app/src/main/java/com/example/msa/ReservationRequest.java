package com.example.msa;

public class ReservationRequest {
    private int number_of_people;
    private String reservation_time;
    private String user_id;

    public ReservationRequest(int numberOfPeople, String reservationTime, String userId) {
        this.number_of_people = numberOfPeople;
        this.reservation_time = reservationTime;
        this.user_id = userId;
    }
}
