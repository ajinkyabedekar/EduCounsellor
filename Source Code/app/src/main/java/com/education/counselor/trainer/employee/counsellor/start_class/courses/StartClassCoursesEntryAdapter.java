package com.education.counselor.trainer.employee.counsellor.start_class.courses;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.education.counselor.trainer.R;
import com.education.counselor.trainer.employee.counsellor.start_class.batches.StartClassBatchesActivity;

import java.util.ArrayList;
public class StartClassCoursesEntryAdapter extends RecyclerView.Adapter<StartClassCourses> {
    private Context c;
    private ArrayList<StartClassCoursesEntryVo> details;
    private String center;
    StartClassCoursesEntryAdapter(Context c, ArrayList<StartClassCoursesEntryVo> details, String center) {
        setHasStableIds(true);
        this.c = c;
        this.details = details;
        this.center = center;
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
    public StartClassCourses onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(c).inflate(R.layout.layout_start_class_courses_adapter, viewGroup, false);
        return new StartClassCourses(v);
    }
    @Override
    public void onBindViewHolder(@NonNull final StartClassCourses holder, int i) {
        holder.s_name.setText(details.get(i).getName());
        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(c, StartClassBatchesActivity.class);
                in.putExtra("course", details.get(holder.getAdapterPosition()).getName());
                in.putExtra("center", center);
                c.startActivity(in);
            }
        });
    }
    @Override
    public int getItemCount() {
        return details.size();
    }
}