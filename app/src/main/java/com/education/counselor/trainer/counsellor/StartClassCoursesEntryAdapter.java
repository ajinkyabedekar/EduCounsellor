package com.education.counselor.trainer.counsellor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.education.counselor.trainer.R;

import java.util.ArrayList;

public class StartClassCoursesEntryAdapter extends RecyclerView.Adapter<StartClassCourses> {
    private Context c;
    private ArrayList<StartClassCoursesEntryVo> details;

    StartClassCoursesEntryAdapter(Context c, ArrayList<StartClassCoursesEntryVo> details) {
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
    public StartClassCourses onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(c).inflate(R.layout.layout_start_class_courses_adapter, viewGroup, false);
        return new StartClassCourses(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final StartClassCourses holder, int i) {
        holder.sname.setText(details.get(i).getName());
        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return details.size();
    }
}