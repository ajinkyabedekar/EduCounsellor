package com.education.counselor.trainer.admin;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.education.counselor.trainer.R;

public class holder extends RecyclerView.ViewHolder {
    TextView sname, pname;

    public holder(@NonNull View itemView) {
        super(itemView);
        sname = (TextView) itemView.findViewById(R.id.stext);
        pname = (TextView) itemView.findViewById(R.id.ptext);
        pname.setText("null");
    }

}
