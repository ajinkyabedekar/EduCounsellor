/*
*************************************
*      Author:Yogesh Sharma         *
*************************************
 -------------------------------------------------------------------------------------------
|     Indevidual component of the internship  list  related with students are listed here   |
 --------------------------------------------------------------------------------------------

*/
package com.education.counselor.trainer.student.internship;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.education.counselor.trainer.R;
class InternshipList extends RecyclerView.ViewHolder {
    TextView s_name;
    View v;
    @SuppressLint("SetTextI18n")
    InternshipList(@NonNull View itemView) {
        super(itemView);
        s_name = itemView.findViewById(R.id.s_text);
        v = itemView.findViewById(R.id.cd);
    }
    public TextView getS_name() {
        return s_name;
    }
    public void setS_name(TextView s_name) {
        this.s_name = s_name;
    }
    public View getV() {
        return v;
    }
    public void setV(View v) {
        this.v = v;
    }
}
