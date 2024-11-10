package com.example.msa;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TicketViewModel extends ViewModel {
    private final MutableLiveData<TicketResponse> ticketLiveData = new MutableLiveData<>();

    public void setTicket(TicketResponse ticketResponse) {
        ticketLiveData.setValue(ticketResponse);
    }

    public LiveData<TicketResponse> getTicket() {
        return ticketLiveData;
    }
}