package com.example.msa;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/enqueue")
    Call<QueueResponse> enqueueUser(@Body QueueRequest queueRequest);
}
