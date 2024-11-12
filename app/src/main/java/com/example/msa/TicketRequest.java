package com.example.msa;

import java.util.List;

public class TicketRequest {
    private String user_id;
    private String park_id;
    private String ticket_type_name;
    private List<String> facility_ids;

    public TicketRequest(String user_id, String park_id, String ticket_type_name, List<String> facility_ids) {
        this.user_id = user_id;
        this.park_id = park_id;
        this.ticket_type_name = ticket_type_name;
        this.facility_ids = facility_ids;
    }

    @Override
    public String toString() {
        return "TicketRequest{" +
                "user_id='" + user_id + '\'' +
                ", park_id='" + park_id + '\'' +
                ", ticket_type_name='" + ticket_type_name + '\'' +
                ", facility_ids=" + facility_ids +
                '}';
    }

}
