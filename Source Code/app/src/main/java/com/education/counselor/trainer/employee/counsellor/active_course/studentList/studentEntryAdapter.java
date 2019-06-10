package com.education.counselor.trainer.employee.counsellor.active_course.studentList;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.education.counselor.trainer.R;
import com.education.counselor.trainer.employee.counsellor.active_course.studentProjectDetails;

import java.util.ArrayList;
public class studentEntryAdapter extends RecyclerView.Adapter<studentCourseList> {
    private Context c;
    private ArrayList<studentListEntryVo> details;
    studentEntryAdapter(Context c, ArrayList<studentListEntryVo> details) {
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
    public studentCourseList onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(c).inflate(R.layout.layout_internship_list_adapter4, viewGroup, false);
        return new studentCourseList(v);
    }
    @Override
    public void onBindViewHolder(@NonNull final studentCourseList holder, int i) {
        holder.s_name.setText(details.get(i).getName());
        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(c, studentProjectDetails.class);
                in.putExtra("name", details.get(holder.getAdapterPosition()).getName());
                in.putExtra("phone", details.get(holder.getAdapterPosition()).getPhone());
                c.startActivity(in);
            }
        });
    }
    @Override
    public int getItemCount() {
        return details.size();
    }
}