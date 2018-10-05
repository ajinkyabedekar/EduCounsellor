package com.education.counselor.trainer.counsellor;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.education.counselor.trainer.R;

class StartClassCenters extends RecyclerView.ViewHolder {
    TextView sname;
    View v;

    @SuppressLint("SetTextI18n")
    StartClassCenters(@NonNull View itemView) {
        super(itemView);
        sname = itemView.findViewById(R.id.c_text);
        v = itemView.findViewById(R.id.cd);
    }
}