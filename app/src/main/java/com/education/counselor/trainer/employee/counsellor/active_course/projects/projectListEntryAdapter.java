package com.education.counselor.trainer.employee.counsellor.active_course.projects;

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

public class projectListEntryAdapter extends RecyclerView.Adapter<projectCourseList> {
    private Context c;
    private ArrayList<projectListEntryVo> details;

    projectListEntryAdapter(Context c, ArrayList<projectListEntryVo> details) {
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
    public projectCourseList onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(c).inflate(R.layout.layout_internship_list_adapter4, viewGroup, false);
        return new projectCourseList(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final projectCourseList holder, int i) {
        holder.s_name.setText(details.get(i).getName());
        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(c, studentProjectDetails.class);
                in.putExtra("pname", details.get(holder.getAdapterPosition()).getName());
                in.putExtra("name", details.get(holder.getAdapterPosition()).getPhone());
                c.startActivity(in);
            }
        });
    }

    @Override
    public int getItemCount() {
        return details.size();
    }
}