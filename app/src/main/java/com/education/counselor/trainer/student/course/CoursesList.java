/*-------------------------------------------------------------------------------------------
|     Its a courseList class which does relevent function related with students              |
 ---------------------------------------------------------------------------------------------
*/package com.education.counselor.trainer.student.course;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.education.counselor.trainer.R;
class CoursesList extends RecyclerView.ViewHolder {
    //initilizing variables
    TextView cname, cid;
    View v;
    CoursesList(@NonNull View itemView) {//constructor
        super(itemView);
        cname = itemView.findViewById(R.id.cname);
        cid = itemView.findViewById(R.id.cid);
        v = itemView.findViewById(R.id.rl);
    }
 //getter method for TextView
    public TextView getCname() {
        return cname;
    }
  
 //setter method for Cname
    public void setCname(TextView cname) {
        this.cname = cname;
    }
  //getter method for id
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
