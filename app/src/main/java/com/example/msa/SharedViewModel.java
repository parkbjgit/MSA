package com.example.msa;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private MutableLiveData<String> selectedTicketName = new MutableLiveData<>("롯데월드"); // 기본값 설정

    public void setSelectedTicketName(String name) {
        selectedTicketName.setValue(name);
    }

    public LiveData<String> getSelectedTicketName() {
        return selectedTicketName;
    }


    //티켓 qr 임시 생성 코드
    private final MutableLiveData<Boolean> isPaymentCompleted = new MutableLiveData<>(false);

    public LiveData<Boolean> getPaymentStatus() {
        return isPaymentCompleted;
    }

    public void setPaymentCompleted(boolean completed) {
        isPaymentCompleted.postValue(completed);
    }

    private final MutableLiveData<String> selectedTicket = new MutableLiveData<>();
    private final MutableLiveData<String> selectedDate = new MutableLiveData<>();

    public LiveData<String> getSelectedTicket() {
        return selectedTicket;
    }

    public void setSelectedTicket(String ticket) {
        selectedTicket.postValue(ticket);
    }

    public LiveData<String> getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(String date) {
        selectedDate.postValue(date);
    }

}
