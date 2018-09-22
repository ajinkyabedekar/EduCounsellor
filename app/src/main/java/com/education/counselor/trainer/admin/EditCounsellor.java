package com.education.counselor.trainer.admin;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.education.counselor.trainer.R;

public class EditCounsellor extends RecyclerView.ViewHolder {
    TextView sname, pname;

    @SuppressLint("SetTextI18n")
    EditCounsellor(@NonNull View itemView) {
        super(itemView);
        sname = itemView.findViewById(R.id.s_text);
        pname = itemView.findViewById(R.id.p_text);
        pname.setText("null");
    }
}