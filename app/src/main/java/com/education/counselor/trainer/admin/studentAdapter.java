package com.education.counselor.trainer.admin;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.education.counselor.trainer.R;

import java.util.ArrayList;

public class studentAdapter extends RecyclerView.Adapter<holder>{
    Context c;
    ArrayList<studentDetails> details;
    public studentAdapter(Context c, ArrayList<studentDetails> details) {
        this.c = c;
        this.details = details;
    }
    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(c).inflate(R.layout.student_recycler_layout, viewGroup, false);
        return new holder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int i) {
        holder.sname.setText(details.get(i).getName());
        holder.pname.setText(details.get(i).getPhone());

    }

    @Override
    public int getItemCount() {
        return details.size();
    }
}
