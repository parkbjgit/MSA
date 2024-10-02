package com.example.msa;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TicketSelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_select);

        bindList();

    }
    private void bindList() {
        //입장권 리스트 생성
        final List<ReservationTicketRecyclerItem> itemList = new ArrayList<>();
        //임시 시설 리스트 생성
        itemList.add(new ReservationTicketRecyclerItem("에버랜드"));
        itemList.add(new ReservationTicketRecyclerItem("롯데월드"));
        itemList.add(new ReservationTicketRecyclerItem("캐리비안 베이"));
        itemList.add(new ReservationTicketRecyclerItem("어린이대공원"));
        itemList.add(new ReservationTicketRecyclerItem("서울랜드"));

        RecyclerView recyclerView = findViewById(R.id.ticket_list_recycler);

        ReservationTicketAdapter adapter = new ReservationTicketAdapter(itemList);
        recyclerView.setAdapter(adapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //입장권 클릭 이벤트
        Bundle bundle = new Bundle();
        adapter.setOnItemClickListener(new ReservationTicketAdapter.OnItemClickEventListener() {
            @Override
            public void onItemClick(View view, int a_position) {
                final ReservationTicketRecyclerItem item = itemList.get(a_position);
                bundle.putString("ticket_name", item.getName());
                DialogFragment dialogFragment = new DatePickerFragment();
                dialogFragment.setArguments(bundle);
                dialogFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });
    }

}