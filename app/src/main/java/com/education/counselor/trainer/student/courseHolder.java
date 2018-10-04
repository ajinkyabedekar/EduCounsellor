package com.education.counselor.trainer.student;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.education.counselor.trainer.R;

class courseHolder extends RecyclerView.ViewHolder {
    TextView cname, cid;
    View v;

    courseHolder(@NonNull View itemView) {
        super(itemView);
        cname = itemView.findViewById(R.id.cname);
        cid = itemView.findViewById(R.id.cid);
        v = itemView.findViewById(R.id.rl);
    }
}
