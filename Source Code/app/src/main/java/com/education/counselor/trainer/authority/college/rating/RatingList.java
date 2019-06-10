package com.education.counselor.trainer.authority.college.rating;

/*-------------------------------------------------------------------------------------------
 |     Its a course rating class which has relevent function for rating by students  |
 |---------------------------------------------------------------------------------------------
*/
//importing packages

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.education.counselor.trainer.R;
class RatingList extends RecyclerView.ViewHolder {
    TextView s_name;
    RatingBar rating;
    private View v;
    @SuppressLint("SetTextI18n")
    RatingList(@NonNull View itemView) {
        super(itemView);
        s_name = itemView.findViewById(R.id.s_text);
        rating = itemView.findViewById(R.id.rating);
        v = itemView.findViewById(R.id.cd);
    }
    public TextView getS_name() {
        return s_name;
    }
    public void setS_name(TextView s_name) {
        this.s_name = s_name;
    }
    public RatingBar getRating() {
        return rating;
    }
    public void setRating(RatingBar rating) {
        this.rating = rating;
    }
    public View getV() {
        return v;
    }
    public void setV(View v) {
        this.v = v;
    }
}
