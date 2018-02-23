package com.waterfairy.reminder.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.waterfairy.reminder.R;
import com.waterfairy.reminder.activity.ClassAddActivity;

/**
 * user : water_fairy
 * email:995637517@qq.com
 * date :2018/2/23
 * des  :
 */

public class ClassSpinnerAdapter implements SpinnerAdapter {
    private Context context;
    private String[] strings;

    public ClassSpinnerAdapter(Context context, String[] strings) {
        this.context = context;
        this.strings = strings;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.item_spinner, parent, false);
        TextView textView = convertView.findViewById(R.id.text);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setTextColor(Color.WHITE);
        textView.setText(strings[position]);
        return convertView;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        if (strings != null) return strings.length;
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.item_spinner, parent, false);
        TextView textView = convertView.findViewById(R.id.text);
        textView.setTextColor(Color.parseColor("#515151"));
        textView.setText(strings[position]);
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
