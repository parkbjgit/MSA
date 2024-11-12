package com.example.msa;

import android.util.Base64;
import java.util.List;

public class TicketResponse {
    private String status;
    private String ticket_id;
    private String message;
    private String encoded_response;  // encoded_response 필드 추가
    private TicketData data;

    public String getStatus() {
        return status;
    }

    public String getTicket_id() {
        return ticket_id;
    }

    public String getMessage() {
        return message;
    }

    public String getEncodedResponse() {
        return encoded_response;
    }

    public void setEncodedResponse(String encoded_response) {
        this.encoded_response = encoded_response;
    }

    public String getDecodedQRCode() {
        if (encoded_response != null) {
            byte[] decodedBytes = Base64.decode(encoded_response, Base64.DEFAULT);
            return new String(decodedBytes);
        }
        return null;
    }

    public TicketData getData() {
        return data;
    }

    @Override
    public String toString() {
        return "TicketResponse{" +
                "status='" + status + '\'' +
                ", ticket_id='" + ticket_id + '\'' +
                ", message='" + message + '\'' +
                ", encoded_response='" + encoded_response + '\'' +
                ", data=" + data +
                '}';
    }

    public static class TicketData {
        private String ticket_type;
        private List<String> allowed_facilities;
        private double amount;

        public String getTicket_type() {
            return ticket_type;
        }

        public List<String> getAllowed_facilities() {
            return allowed_facilities;
        }

        public double getAmount() {
            return amount;
        }
    }
}
