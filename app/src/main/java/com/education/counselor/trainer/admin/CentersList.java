package com.education.counselor.trainer.admin;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.education.counselor.trainer.R;

class CentersList extends RecyclerView.ViewHolder {
    TextView sname;
    View v;

    @SuppressLint("SetTextI18n")
    CentersList(@NonNull View itemView) {
        super(itemView);
        sname = itemView.findViewById(R.id.c_text);
        v = itemView.findViewById(R.id.cd);
    }
}