package com.example.msa;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit;

    //내 컴퓨터의 ip 주소 입력 명령어 ifconfig로 en0 inet 주소 확인******

    //private static final String BASE_URL = "http://localhost:8000/docs/";
    //private static final String BASE_URL = "http://10.0.2.2:8000/docs/";
    private static final String BASE_URL = "http://192.168.219.185:8000/";   //서버 주소


    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
