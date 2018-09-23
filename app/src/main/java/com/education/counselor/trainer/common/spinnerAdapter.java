package com.education.counselor.trainer.common;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.education.counselor.trainer.R;

import java.util.ArrayList;
import java.util.List;

public class spinnerAdapter extends ArrayAdapter<multiList>{
    private LayoutInflater inflater;
    private List<multiList> list;
    public Spinner myspinner;
    List<String> center=new ArrayList<>();
    Context c;
    public static String dash ="-";

    public spinnerAdapter(@NonNull Context context, int resource, @NonNull List<multiList> objects, Spinner myspinner) {
        super(context, resource, objects);
        this.list=objects;
        this.myspinner=myspinner;
        this.c=context;
        inflater=LayoutInflater.from(context);
    }
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(final int position, View convertView, ViewGroup parent)
    {
        String text = "";
        final ViewHolder holder;
        if (convertView == null)
        {
            holder = new ViewHolder();
            inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.spinnerholder, null, false);
            holder.mTextView = convertView.findViewById(R.id.tvSpinnerItem);
            holder.ck= convertView.findViewById(R.id.ckbox);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        if(list.get(position).getText().equals("Centres")) {
            holder.mTextView.setText(list.get(position).getText());
            holder.ck.setVisibility(View.GONE);
        }
        holder.mTextView.setText(list.get(position).getText());
        holder.ck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                multiList s=list.get(position);
                s.setSelected(!s.isSelected());
                if(s.isSelected())
                    center.add(s.getText());
                else
                    center.remove(s.getText());
                //Toast.makeText(c,center+"" , Toast.LENGTH_SHORT).show();

            }
        });
        return convertView;
    }
    public List<String> getCenter()
    {
        return center;
    }
    private class ViewHolder
    {
        private TextView mTextView;
        private CheckBox ck;
    }
}

