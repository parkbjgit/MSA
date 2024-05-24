package com.example.msa;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

public class TicketSelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_select);

        ImageButton ticket1 = (ImageButton) findViewById(R.id.ticket1);
        ImageButton ticket2 = (ImageButton) findViewById(R.id.ticket2);

        //티켓 예매 시설 이름 전달
        Bundle bundle = new Bundle();

        ticket1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle.putString("ticket_name", "에버랜드");
                DialogFragment dialogFragment = new DatePickerFragment();
                dialogFragment.setArguments(bundle);
                dialogFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        ticket2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle.putString("ticket_name", "롯데월드");
                DialogFragment dialogFragment = new DatePickerFragment();
                dialogFragment.setArguments(bundle);
                dialogFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

    }
}