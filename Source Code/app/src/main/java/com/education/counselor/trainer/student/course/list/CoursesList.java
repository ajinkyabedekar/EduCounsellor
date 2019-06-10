package com.education.counselor.trainer.student.course.list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.education.counselor.trainer.R;
class CoursesList extends RecyclerView.ViewHolder {
    TextView cname, cid;
    private View v;
    CoursesList(@NonNull View itemView) {
        super(itemView);
        cname = itemView.findViewById(R.id.cname);
        cid = itemView.findViewById(R.id.cid);
        v = itemView.findViewById(R.id.rl);
    }
    public TextView getCname() {
        return cname;
    }
    public void setCname(TextView cname) {
        this.cname = cname;
    }
    public TextView getCid() {
        return cid;
    }
    public void setCid(TextView cid) {
        this.cid = cid;
    }
    public View getV() {
        return v;
    }
    public void setV(View v) {
        this.v = v;
    }
}