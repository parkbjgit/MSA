package com.example.msa;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {

    private final MutableLiveData<String> selectedParkName = new MutableLiveData<>();

    public void setSelectedParkName(String parkName) {
        selectedParkName.setValue(parkName);
    }

    public LiveData<String> getSelectedParkName() {
        return selectedParkName;
    }
}
