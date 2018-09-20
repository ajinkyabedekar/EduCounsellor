package com.education.counselor.trainer.admin;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.education.counselor.trainer.R;

class PlacementCenters extends RecyclerView.ViewHolder {
    TextView sname;

    @SuppressLint("SetTextI18n")
    PlacementCenters(@NonNull View itemView) {
        super(itemView);
        sname = itemView.findViewById(R.id.ctext);
    }
}