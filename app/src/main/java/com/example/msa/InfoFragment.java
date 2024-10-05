package com.example.msa;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class InfoFragment extends Fragment implements View.OnClickListener {

    RecyclerView recyclerView;
    String lists[], times[];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        //예약하러 가기 버튼
        Button go_reservation = view.findViewById(R.id.btn_go_reservation);
        Button reservation_cancel = view.findViewById(R.id.reservation_cancel);

        reservation_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCancelDialog();
            }
        });
        // 버튼 클릭 이벤트 리스너
        go_reservation.setOnClickListener(this);

        return view;
    }

    // 버튼 클릭 이벤트 리스너
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_go_reservation) {
            // ChildFragment 전환
            InfoFragment2 childFragment = new InfoFragment2();
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, childFragment)
                    .addToBackStack(null) // 뒤로 가기 시 이전 프래그먼트로 돌아가기 가능
                    .commit();
        }
    }

    private void showCancelDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()); // 여기서 this 대신 getActivity() 사용
        builder.setTitle("예약 취소");
        builder.setMessage("예약을 취소하시겠습니까?");

        // "예" 버튼
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 예약 취소 처리 로직
                Toast.makeText(getActivity(), "예약이 취소되었습니다.", Toast.LENGTH_SHORT).show(); // getActivity() 사용
            }
        });

        // "아니오" 버튼
        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 대화 상자를 닫기만 함
                dialog.dismiss();
            }
        });

        // 대화 상자를 보여줌
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
