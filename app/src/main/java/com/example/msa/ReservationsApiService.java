package com.example.msa;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ReservationsApiService {
    @POST("api/reserve/{ride_id}")
    Call<Void> reserveRide(
            @Path("ride_id") String rideId,
            @Body ReservationRequest request
    );

    @POST("api/cancel/{ride_id}")
    Call<Void> cancelRide(
            @Path("ride_id") String rideId,
            @Query("user_id") String userId,
            @Body CancelRequest reason
    );
}
