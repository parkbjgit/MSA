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
}
