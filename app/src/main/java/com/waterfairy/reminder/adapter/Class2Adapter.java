package com.waterfairy.reminder.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.waterfairy.reminder.R;
import com.waterfairy.reminder.database.ClassDB;

import java.util.List;

/**
 * user : water_fairy
 * email:995637517@qq.com
 * date :2018/2/23
 * des  :课程适配器
 * 注:
 * ----适配器 用户处理数据列表展示的逻辑  用于显示
 */

public class Class2Adapter extends RecyclerView.Adapter<Class2Adapter.ViewHolder> {
    private Context context;
    private List<ClassDB> everyDayDBS;//课程数据集合
    private OnItemClickListener onItemClickListener;//点击课程监听
    private OnItemLongClickListener onItemLongClickListener;//长按课程监听

    public Class2Adapter(Context context, List<ClassDB> everyDayDBS) {
        this.context = context;
        this.everyDayDBS = everyDayDBS;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_remorandum, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ClassDB classDB = everyDayDBS.get(position);
        holder.mTime.setText(classDB.getWeekString() + " " + classDB.getTimeString() + " " + classDB.getLevelString());
        holder.mContent.setText(classDB.getClassName());
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = (int) v.getTag();
                ClassDB tempDb = everyDayDBS.get(pos);
                if (onItemClickListener != null)
                    onItemClickListener.onItemClick(tempDb, pos);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int pos = (int) v.getTag();
                ClassDB tempDb = everyDayDBS.get(pos);
                if (onItemLongClickListener != null)
                    onItemLongClickListener.onItemLongClick(tempDb, pos);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        if (everyDayDBS != null) return everyDayDBS.size();
        return 0;
    }

    public void remove(int pos) {
        everyDayDBS.remove(pos);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTime;
        private TextView mContent;

        public ViewHolder(View itemView) {
            super(itemView);
            mTime = itemView.findViewById(R.id.time);
            mContent = itemView.findViewById(R.id.content);
        }
    }

    public Class2Adapter setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        return this;
    }

    public Class2Adapter setOnItemLongClickListener(OnItemLongClickListener onItemClickListener) {
        this.onItemLongClickListener = onItemClickListener;
        return this;
    }

    public interface OnItemClickListener {
        void onItemClick(ClassDB db, int pos);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(ClassDB db, int pos);
    }
}
