package com.example.msa;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {

    //qr코드 이미지 표시를 위한 임시 코드
    private final MutableLiveData<Boolean> isImageVisible = new MutableLiveData<>(false);

    public LiveData<Boolean> getImageVisibility(){
        return isImageVisible;
    }

    public void setImageVisibility(boolean visible) {
        isImageVisible.setValue(visible);
    }

}
