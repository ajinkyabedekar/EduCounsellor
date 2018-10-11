package com.education.counselor.trainer.admin.courses.list;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.education.counselor.trainer.R;
import com.education.counselor.trainer.admin.courses.CoursesDetailsActivity;

import java.util.ArrayList;

public class CoursesListEntryAdapter extends RecyclerView.Adapter<CoursesList> {
    private Context c;
    private ArrayList<CoursesListEntryVo> details;
    private String n;

    CoursesListEntryAdapter(Context c, ArrayList<CoursesListEntryVo> details, String n) {
        setHasStableIds(true);
        this.c = c;
        this.details = details;
        this.n = n;
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
    public CoursesList onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(c).inflate(R.layout.layout_courses_list_adapter, viewGroup, false);
        return new CoursesList(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final CoursesList holder, int i) {
        holder.s_name.setText(details.get(i).getName());
        holder.p_name.setText(details.get(i).getPhone());
        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(c, CoursesDetailsActivity.class);
                in.putExtra("name", details.get(holder.getAdapterPosition()).getName());
                in.putExtra("center", n);
                c.startActivity(in);
                ((Activity) c).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return details.size();
    }
}