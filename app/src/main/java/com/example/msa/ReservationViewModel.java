package com.example.msa;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class ReservationViewModel extends AndroidViewModel {

    private final MutableLiveData<String> rideName = new MutableLiveData<>("");
    private final MutableLiveData<String> rideTime = new MutableLiveData<>("");
    private final MutableLiveData<String> ridePeople = new MutableLiveData<>("");
    private final SharedPreferences sharedPreferences;

    private static final String PREF_NAME = "reservation_pref";
    private static final String KEY_RIDE_NAME = "ride_name";
    private static final String KEY_RIDE_TIME = "ride_time";
    private static final String KEY_RIDE_PEOPLE = "ride_people";

    public ReservationViewModel(@NonNull Application application) {
        super(application);
        sharedPreferences = application.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        loadReservationData();
    }

    public LiveData<String> getRideName() {
        return rideName;
    }

    public LiveData<String> getRideTime() {
        return rideTime;
    }

    public LiveData<String> getRidePeople() {
        return ridePeople;
    }

    public void setReservation(String name, String time, String people) {
        rideName.setValue(name);
        rideTime.setValue(time);
        ridePeople.setValue(people);
        saveReservationData(name, time, people);
    }

    public void clearReservation() {
        rideName.setValue("");
        rideTime.setValue("");
        ridePeople.setValue("");
        clearReservationData();
    }

    private void saveReservationData(String name, String time, String people) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_RIDE_NAME, name);
        editor.putString(KEY_RIDE_TIME, time);
        editor.putString(KEY_RIDE_PEOPLE, people);
        editor.apply();
    }

    private void loadReservationData() {
        String name = sharedPreferences.getString(KEY_RIDE_NAME, "");
        String time = sharedPreferences.getString(KEY_RIDE_TIME, "");
        String people = sharedPreferences.getString(KEY_RIDE_PEOPLE, "");
        rideName.setValue(name);
        rideTime.setValue(time);
        ridePeople.setValue(people);
    }

    private void clearReservationData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_RIDE_NAME);
        editor.remove(KEY_RIDE_TIME);
        editor.remove(KEY_RIDE_PEOPLE);
        editor.apply();
    }
}
