package com.education.counselor.trainer.admin.employee.multiselectionspinner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.education.counselor.trainer.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MultiSelectionSpinnerAdapter extends ArrayAdapter<MultiSelectionSpinner> {
    public static String dash = "-";
    private List<String> center = new ArrayList<>();
    private LayoutInflater inflater;
    private List<MultiSelectionSpinner> list;

    public MultiSelectionSpinnerAdapter(@NonNull Context context, int resource, @NonNull List<MultiSelectionSpinner> objects) {
        super(context, resource, objects);
        this.list = objects;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView);
    }

    @SuppressLint("InflateParams")
    private View getCustomView(final int position, View convertView) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = Objects.requireNonNull(inflater).inflate(R.layout.spinnerholder, null, false);
            holder.mTextView = convertView.findViewById(R.id.tvSpinnerItem);
            holder.ck = convertView.findViewById(R.id.ck_box);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (list.get(position).getText().equals("Centres")) {
            holder.mTextView.setText(list.get(position).getText());
            holder.ck.setVisibility(View.GONE);
        }
        holder.mTextView.setText(list.get(position).getText());
        holder.ck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MultiSelectionSpinner s = list.get(position);
                s.setSelected(!s.isSelected());
                if (s.isSelected())
                    center.add(s.getText());
                else
                    center.remove(s.getText());
                //Toast.makeText(c,center+"" , Toast.LENGTH_SHORT).show();

            }
        });
        return convertView;
    }

    public List<String> getCenter() {
        return center;
    }

    private class ViewHolder {
        private TextView mTextView;
        private CheckBox ck;
    }
}

