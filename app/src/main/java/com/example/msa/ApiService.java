package com.example.msa;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/enqueue")
    Call<QueueResponse> enqueueUser(@Body QueueRequest queueRequest);

//    @POST("/tickets/api/validate")
//    Call<TicketResponse> createTicket(@Body TicketRequest ticketRequest);

    @POST("api/tickets/validate")
    Call<TicketResponse> validateTicket(@Body TicketRequest request);
}
