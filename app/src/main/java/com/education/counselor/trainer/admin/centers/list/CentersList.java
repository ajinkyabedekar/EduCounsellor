//Model class for centers list

//list package
package com.education.counselor.trainer.admin.centers.list;


//importing necessary packages
import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
/***importing android resources***/
import com.education.counselor.trainer.R;

//This is a view holder class 
class CentersList extends RecyclerView.ViewHolder {
   //Variable initialization//
    TextView s_name;
    View v;
    
    //**Using annotation***//
    @SuppressLint("SetTextI18n")
    
    //Class constructor
    CentersList(@NonNull View itemView) {
        super(itemView);
        s_name = itemView.findViewById(R.id.c_text);
        v = itemView.findViewById(R.id.cd);
    }
    
    // Getter method for retrieving Center's name
    public TextView getS_name() {
        return s_name;
    }
    // Setter method for setting Center's name
    public void setS_name(TextView s_name) {
        this.s_name = s_name;
    }
    // Getter method for retrieving layout's views
    public View getV() {
        return v;
    }
    // Setter method for setting layout's view
    public void setV(View v) {
        this.v = v;
    }
}
