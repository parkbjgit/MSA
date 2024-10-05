package com.example.msa;

public class QueueRequest {
    private String rideId;
    private String userId;

    // 생성자
    public QueueRequest(String rideId, String userId) {
        this.rideId = rideId;
        this.userId = userId;
    }

    // Getter와 Setter 추가 (필요시)
    public String getRideId() {
        return rideId;
    }

    public void setRideId(String rideId) {
        this.rideId = rideId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
