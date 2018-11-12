package com.education.counselor.trainer.employee.trainer.start_class.batches;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.education.counselor.trainer.R;
class StartClassBatches extends RecyclerView.ViewHolder {
    TextView s_name, time;
    Button start, stop;
    private View v;
    @SuppressLint("SetTextI18n")
    StartClassBatches(@NonNull View itemView) {
        super(itemView);
        s_name = itemView.findViewById(R.id.c_text);
        time = itemView.findViewById(R.id.timeText);
        start = itemView.findViewById(R.id.start);
        stop = itemView.findViewById(R.id.stop);
        v = itemView.findViewById(R.id.cd);
    }
    public TextView getS_name() {
        return s_name;
    }
    public void setS_name(TextView s_name) {
        this.s_name = s_name;
    }
    public TextView getTime() {
        return time;
    }
    public void setTime(TextView time) {
        this.time = time;
    }
    public Button getStart() {
        return start;
    }
    public void setStart(Button start) {
        this.start = start;
    }
    public Button getStop() {
        return stop;
    }
    public void setStop(Button stop) {
        this.stop = stop;
    }
    public View getV() {
        return v;
    }
    public void setV(View v) {
        this.v = v;
    }
}