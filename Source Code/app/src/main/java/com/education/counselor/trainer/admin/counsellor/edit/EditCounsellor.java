package com.education.counselor.trainer.admin.counsellor.edit;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.education.counselor.trainer.R;
class EditCounsellor extends RecyclerView.ViewHolder {
    TextView s_name, p_name;
    @SuppressLint("SetTextI18n")
    EditCounsellor(@NonNull View itemView) {
        super(itemView);
        s_name = itemView.findViewById(R.id.s_text);
        p_name = itemView.findViewById(R.id.p_name);
        p_name.setText("null");
    }
    public TextView getS_name() {
        return s_name;
    }
    public void setS_name(TextView s_name) {
        this.s_name = s_name;
    }
    public TextView getP_name() {
        return p_name;
    }
    public void setP_name(TextView p_name) {
        this.p_name = p_name;
    }
}