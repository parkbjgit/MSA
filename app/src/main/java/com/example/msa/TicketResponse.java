package com.example.msa;

import java.util.List;

public class TicketResponse {
    private String status;
    private String ticket_id;
    private String message;
    private TicketData data;

    // 기본 생성자와 getter 메서드 추가

    public String getStatus() {
        return status;
    }

    public String getTicket_id() {
        return ticket_id;
    }

    public String getMessage() {
        return message;
    }

    public TicketData getData() {
        return data;
    }

    public static class TicketData {
        private String ticket_type;
        private List<String> allowed_facilities;
        private double amount;

        // TicketData에 대한 getter 메서드
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