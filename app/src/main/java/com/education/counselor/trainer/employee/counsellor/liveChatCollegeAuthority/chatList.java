package com.education.counselor.trainer.employee.counsellor.liveChatCollegeAuthority;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.education.counselor.trainer.R;

class chatList extends RecyclerView.ViewHolder {
    TextView name, date, message;

    @SuppressLint("SetTextI18n")
    chatList(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.name);
        date = itemView.findViewById(R.id.cid);
        message = itemView.findViewById(R.id.cname);

    }

    public TextView getDate() {
        return date;
    }

    public void setDate(TextView date) {
        this.date = date;
    }

    public TextView getMessage() {
        return message;
    }

    public void setMessage(TextView message) {
        this.message = message;
    }

    public TextView getName() {
        return name;
    }

    public void setName(TextView name) {
        this.name = name;
    }
}