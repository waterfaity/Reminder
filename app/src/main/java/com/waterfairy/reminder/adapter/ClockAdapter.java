package com.waterfairy.reminder.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.waterfairy.reminder.R;
import com.waterfairy.reminder.database.ClockDB;
import com.waterfairy.reminder.utils.WeekUtils;

import java.util.List;

/**
 * user : water_fairy
 * email:971409587@qq.com
 * date :2017/11/25
 * des  :
 */

public class ClockAdapter extends RecyclerView.Adapter<ClockAdapter.ViewHolder> {
    private Context context;
    List<ClockDB> mData;
    private View.OnClickListener onClickListener;
    private CompoundButton.OnCheckedChangeListener checkedChangeListener;
    private OnItemClickListener onItemClickListener;

    public ClockAdapter(Context context, List<ClockDB> mData) {
        this.context = context;
        this.mData = mData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_clock, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ClockDB clockDB = mData.get(position);
        holder.mCheckBox.setOnCheckedChangeListener(null);
        holder.mCheckBox.setChecked(clockDB.getOpen());
        holder.mTime.setText(clockDB.getTime());
        holder.mDate.setText(WeekUtils.getWeeks(clockDB.getWeek()));
        holder.mCheckBox.setTag(position);
        holder.mCheckBox.setOnCheckedChangeListener(checkedChangeListener);
        holder.mRootView.setTag(position);
        holder.mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = 0;
                ClockDB clockDBClick = mData.get(pos = (Integer) v.getTag());
                if (onItemClickListener != null) onItemClickListener.onItemClick(pos, clockDBClick);
            }
        });
        holder.mRootView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int pos = 0;
                ClockDB clockDBClick = mData.get(pos = (Integer) v.getTag());
                if (onItemClickListener != null)
                    onItemClickListener.onItemDeleteClick(pos, clockDBClick);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mData != null) return mData.size();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTime;
        private TextView mDate;
        private View mRootView;
        private CheckBox mCheckBox;

        public ViewHolder(View itemView) {
            super(itemView);
            mRootView = itemView.findViewById(R.id.root_item);
            mTime = itemView.findViewById(R.id.time);
            mDate = itemView.findViewById(R.id.date);
            mCheckBox = itemView.findViewById(R.id.open);
        }
    }

    public void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener checkedChangeListener) {
        this.checkedChangeListener = checkedChangeListener;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int pos, ClockDB clockDB);

        void onItemDeleteClick(int pos, ClockDB clockDB);
    }
}
