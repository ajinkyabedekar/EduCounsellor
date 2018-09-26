package com.education.counselor.trainer.authority.college;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.education.counselor.trainer.R;

class ProjectList extends RecyclerView.ViewHolder {
    TextView sname;
    View v;

    @SuppressLint("SetTextI18n")
    ProjectList(@NonNull View itemView) {
        super(itemView);
        sname = itemView.findViewById(R.id.s_text);
        v = itemView.findViewById(R.id.cd);
    }
}