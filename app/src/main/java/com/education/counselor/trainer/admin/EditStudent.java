package com.education.counselor.trainer.admin;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.education.counselor.trainer.R;

public class EditStudent extends RecyclerView.ViewHolder {
    TextView sname, pname;

    @SuppressLint("SetTextI18n")
    EditStudent(@NonNull View itemView) {
        super(itemView);
        sname = itemView.findViewById(R.id.stext);
        pname = itemView.findViewById(R.id.ptext);
        pname.setText("null");
    }

}
