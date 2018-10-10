package com.education.counselor.trainer.student.course;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.education.counselor.trainer.R;

import java.util.ArrayList;

public class CoursesListEntryAdapter extends RecyclerView.Adapter<CoursesList> {

    private Context c;
    private ArrayList<CoursesListEntryVo> details;
    private String name, id;

    CoursesListEntryAdapter(Context c, ArrayList<CoursesListEntryVo> details, String name, String id) {
        this.c = c;
        this.details = details;
        this.name = name;
        this.id = id;
    }

    @NonNull
    @Override
    public CoursesList onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(c).inflate(R.layout.course_recycler_layout, viewGroup, false);
        return new CoursesList(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final CoursesList holder, int i) {
        holder.cname.setText(details.get(i).getCourse_name());
        holder.cid.setText(details.get(i).getCourse_id());
        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cdata[] = new String[]{details.get(holder.getAdapterPosition()).getCourse_id(), name, id};
                Intent in = new Intent(c, CourseDetailsActivity.class);
                in.putExtra("cdata", cdata);
                c.startActivity(in);
            }
        });

    }

    @Override
    public int getItemCount() {
        return details.size();
    }
}