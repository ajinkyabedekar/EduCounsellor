package com.education.counselor.trainer.admin;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.education.counselor.trainer.R;

import java.util.ArrayList;

public class AllStudentsEntryAdapter extends RecyclerView.Adapter<AllStudents> {
    private Context c;
    private ArrayList<AllStudentsEntryVo> details;

    AllStudentsEntryAdapter(Context c, ArrayList<AllStudentsEntryVo> details) {
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
    public AllStudents onCreateViewHolder(@NonNull ViewGroup viewGroup,final int i) {
        View v = LayoutInflater.from(c).inflate(R.layout.layout_all_students_adapter, viewGroup, false);
        return new AllStudents(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final AllStudents holder, int i) {
        holder.sname.setText(details.get(i).getName());
        holder.pname.setText(details.get(i).getPhone());
        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(c, "Hello "+details.get(holder.getAdapterPosition()).getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return details.size();
    }
}