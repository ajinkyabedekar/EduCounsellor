package com.education.counselor.trainer.admin;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.education.counselor.trainer.R;

public class AllStudents extends RecyclerView.ViewHolder {
    TextView sname, pname;
    View v;
    @SuppressLint("SetTextI18n")
    AllStudents(@NonNull View itemView) {
        super(itemView);
        sname = itemView.findViewById(R.id.stext);
        pname = itemView.findViewById(R.id.ptext);
        v=itemView.findViewById(R.id.cd);
        pname.setText("null");
    }
}