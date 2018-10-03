package com.education.counselor.trainer.student;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.education.counselor.trainer.R;
import com.education.counselor.trainer.admin.StudentsAcademicsActivity;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class courseAdapter extends RecyclerView.Adapter<courseHolder> {

    private Context c;
    private ArrayList<courses> details;
    private String name,id;
    public courseAdapter(Context c, ArrayList<courses> details,String name,String id) {
        this.c = c;
        this.details=details;
        this.name=name;
        this.id=id;
    }

    @NonNull
    @Override
    public courseHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(c).inflate(R.layout.course_recycler_layout, viewGroup, false);
        return new courseHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final courseHolder holder, int i) {
        holder.cname.setText(details.get(i).getCourse_name());
        holder.cid.setText(details.get(i).getCourse_id());
        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cdata[]=new String[]{details.get(holder.getAdapterPosition()).getCourse_id(),name,id};
                Intent in = new Intent(c, CourseDetails.class);
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
