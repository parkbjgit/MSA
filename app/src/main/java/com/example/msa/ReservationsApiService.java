package com.example.msa;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ReservationsApiService {
    @POST("api/reserve/{ride_id}")
    Call<Void> reserveRide(
            @Path("ride_id") String rideId,
            @Body ReservationRequest request
    );
}
