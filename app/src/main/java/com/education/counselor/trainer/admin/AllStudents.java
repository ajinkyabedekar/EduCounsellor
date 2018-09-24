package com.education.counselor.trainer.admin;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.education.counselor.trainer.R;

public class AllStudents extends RecyclerView.ViewHolder {
    TextView s_name, p_name;
    View v;

    @SuppressLint("SetTextI18n")
    AllStudents(@NonNull View itemView) {
        super(itemView);
        s_name = itemView.findViewById(R.id.s_text);
        p_name = itemView.findViewById(R.id.p_text);
        v = itemView.findViewById(R.id.cd);
        p_name.setText("null");
    }
}