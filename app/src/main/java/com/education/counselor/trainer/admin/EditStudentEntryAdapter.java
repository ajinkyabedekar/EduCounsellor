package com.education.counselor.trainer.admin;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.education.counselor.trainer.R;

import java.util.ArrayList;

public class EditStudentEntryAdapter extends RecyclerView.Adapter<EditStudent> {
    private Context c;
    private ArrayList<EditStudentEntryVo> details;

    EditStudentEntryAdapter(Context c, ArrayList<EditStudentEntryVo> details) {
        setHasStableIds(true);
        this.c = c;
        this.details = details;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @NonNull
    @Override
    public EditStudent onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(c).inflate(R.layout.layout_edit_student_adapter, viewGroup, false);
        return new EditStudent(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final EditStudent holder, int i) {
        holder.s_name.setText(details.get(i).getName());
        holder.p_name.setText(details.get(i).getPhone());
        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(c, StudentDetailsActivity.class);
                in.putExtra("name", details.get(holder.getAdapterPosition()).getName());
                c.startActivity(in);
            }
        });
    }

    @Override
    public int getItemCount() {
        return details.size();
    }
}