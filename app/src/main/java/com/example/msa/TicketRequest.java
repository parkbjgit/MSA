package com.example.msa;

import java.util.List;

public class TicketRequest {
    private String user_id;
    private String park_id;
    private String ticket_type_name;
    private List<String> facility_ids;

    // 생성자, getter, setter 메서드
    public TicketRequest(String user_id, String park_id, String ticket_type_name, List<String> facility_ids) {
        this.user_id = user_id;
        this.park_id = park_id;
        this.ticket_type_name = ticket_type_name;
        this.facility_ids = facility_ids;
    }

    // Getters and Setters
}
