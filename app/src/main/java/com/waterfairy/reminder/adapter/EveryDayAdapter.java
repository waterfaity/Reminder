package com.waterfairy.reminder.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.waterfairy.reminder.R;
import com.waterfairy.reminder.database.EveryDayDB;
import com.waterfairy.reminder.database.MemorandumDB;
import com.waterfairy.reminder.utils.TimeUtils;

import java.util.Date;
import java.util.List;

/**
 * user : water_fairy
 * email:995637517@qq.com
 * date :2018/2/23
 * des  :
 */

public class EveryDayAdapter extends RecyclerView.Adapter<EveryDayAdapter.ViewHolder> {
    private Context context;
    private List<EveryDayDB> everyDayDBS;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    public EveryDayAdapter(Context context, List<EveryDayDB> everyDayDBS) {
        this.context = context;
        this.everyDayDBS = everyDayDBS;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_remorandum, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        EveryDayDB memorandumDB = everyDayDBS.get(position);
        holder.mTime.setText(TimeUtils.format(new Date(memorandumDB.getTime()),"yyyy-MM-dd"));
        holder.mContent.setText(memorandumDB.getContent());
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = (int) v.getTag();
                EveryDayDB tempDb = everyDayDBS.get(pos);
                if (onItemClickListener != null)
                    onItemClickListener.onItemClick(tempDb, pos);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int pos = (int) v.getTag();
                EveryDayDB tempDb = everyDayDBS.get(pos);
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

    public EveryDayAdapter setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        return this;
    }

    public EveryDayAdapter setOnItemLongClickListener(OnItemLongClickListener onItemClickListener) {
        this.onItemLongClickListener = onItemClickListener;
        return this;
    }

    public interface OnItemClickListener {
        void onItemClick(EveryDayDB db, int pos);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(EveryDayDB db, int pos);
    }
}
