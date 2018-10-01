package com.education.counselor.trainer.student;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.education.counselor.trainer.R;

class QueryList extends RecyclerView.ViewHolder {
    TextView s_name;
    private View v;

    @SuppressLint("SetTextI18n")
    QueryList(@NonNull View itemView) {
        super(itemView);
        s_name = itemView.findViewById(R.id.s_text);
        v = itemView.findViewById(R.id.cd);
    }
}