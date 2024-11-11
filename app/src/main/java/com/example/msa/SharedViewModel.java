package com.example.msa;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<Integer> adultCount = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> adultCount2 = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> teenCount = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> teenCount2 = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> childCount = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> childCount2 = new MutableLiveData<>(0);
    public void setAdultCount(int count) { adultCount.postValue(count); }
    public LiveData<Integer> getAdultCount() { return adultCount; }

    public void setAdultCount2(int count) { adultCount2.postValue(count); }
    public LiveData<Integer> getAdultCount2() { return adultCount2; }

    public void setTeenCount(int count) { teenCount.postValue(count); }
    public LiveData<Integer> getTeenCount() { return teenCount; }

    public void setTeenCount2(int count) { teenCount2.postValue(count); }
    public LiveData<Integer> getTeenCount2() { return teenCount2; }

    public void setChildCount(int count) { childCount.postValue(count); }
    public LiveData<Integer> getChildCount() { return childCount; }

    public void setChildCount2(int count) { childCount2.postValue(count); }
    public LiveData<Integer> getChildCount2() { return childCount2; }


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
