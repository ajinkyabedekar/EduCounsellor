package com.education.counselor.trainer.authority.school;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.education.counselor.trainer.R;

class SuggestionList extends RecyclerView.ViewHolder {
    TextView s_name;
    private View v;

    @SuppressLint("SetTextI18n")
    SuggestionList(@NonNull View itemView) {
        super(itemView);
        s_name = itemView.findViewById(R.id.s_text);
        v = itemView.findViewById(R.id.cd);
    }
}