package com.waterfairy.reminder.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.waterfairy.reminder.R;
import com.waterfairy.reminder.database.MemorandumCategoryDB;

import java.util.List;

/**
 * user : water_fairy
 * email:995637517@qq.com
 * date :2018/5/8
 * des  :
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private Context context;
    private List<MemorandumCategoryDB> dataList;
    private View.OnClickListener clickListener;

    public CategoryAdapter(Context context, List<MemorandumCategoryDB> list) {
        this.context=context;
        this.dataList=list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_text, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MemorandumCategoryDB memorandumCategoryDB = dataList.get(position);
        holder.mTVContent.setText(memorandumCategoryDB.getName());
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(clickListener);
    }

    @Override
    public int getItemCount() {
        if (dataList != null) return dataList.size();
        return 0;
    }

    public MemorandumCategoryDB getItem(int pos) {
        return dataList.get(pos);
    }

    public List<MemorandumCategoryDB> getData() {
        return dataList;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTVContent;

        public ViewHolder(View itemView) {
            super(itemView);
            mTVContent = itemView.findViewById(R.id.text);
        }
    }

    public void setOnItemClickListener(View.OnClickListener onClickListener) {
        this.clickListener = onClickListener;
    }
}
