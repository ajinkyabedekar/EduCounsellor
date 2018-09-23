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
import com.education.counselor.trainer.R;
import java.util.List;

public class spinnerAdapter extends ArrayAdapter<multiList>{
    private LayoutInflater inflater;
    private List<multiList> list;
    public Spinner myspinner;
    public static String dash ="-";

    public spinnerAdapter(@NonNull Context context, int resource, @NonNull List<multiList> objects, Spinner myspinner) {
        super(context, resource, objects);
        this.list=objects;
        this.myspinner=myspinner;
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
        if(position==0) {
            holder.mTextView.setText(list.get(position).getText());
            holder.ck.setVisibility(View.GONE);
        }
        holder.mTextView.setText(list.get(position).getText());
        holder.mTextView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                 myspinner.performClick();
                int getPosition = (Integer) v.getTag();
                list.get(getPosition).setSelected(!list.get(getPosition).isSelected());
                notifyDataSetChanged();

                if (getPosition == 1)
                {
                    clearList();
                }
                else if (getPosition == 2)
                {
                    fillList();
                }
            }
        });
        return convertView;
    }

    public void clearList()
    {
        for (multiList items : list)
        {
            items.setSelected(false);
        }
        notifyDataSetChanged();
    }

    public void fillList()
    {
        for (multiList items : list)
        {
            items.setSelected(true);
        }
        notifyDataSetChanged();
    }

    private class ViewHolder
    {
        private TextView mTextView;
        private CheckBox ck;
    }
}
