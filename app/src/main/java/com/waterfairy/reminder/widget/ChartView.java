package com.waterfairy.reminder.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Size;
import android.view.View;

import com.waterfairy.reminder.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * user : water_fairy
 * email:995637517@qq.com
 * date :2018/5/9
 * des  :
 */

public class ChartView extends View {
    private List<ChartEntity> chartEntities;
    private Paint mLinePaint;
    private int perHeight;
    private Rect rect;
    private Paint chartPaint;
    private Paint textPaint;
    private float textSize;

    public ChartView(Context context) {
        this(context, null);
    }

    public ChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        initData();
    }

    private void initData() {
        chartEntities = new ArrayList<>();
        chartEntities.add(new ChartEntity("ha", new Random().nextInt(100)));
        chartEntities.add(new ChartEntity("ha", new Random().nextInt(100)));
        chartEntities.add(new ChartEntity("ha", new Random().nextInt(100)));
        chartEntities.add(new ChartEntity("ha", new Random().nextInt(100)));
        chartEntities.add(new ChartEntity("ha", new Random().nextInt(100)));
        chartEntities.add(new ChartEntity("ha", new Random().nextInt(100)));
        rect = new Rect();
    }

    private void initPaint() {
        mLinePaint = new Paint();
        mLinePaint.setColor(getResources().getColor(R.color.colorAccent));
        mLinePaint.setStrokeWidth(2);
        mLinePaint.setAntiAlias(true);

        chartPaint = new Paint();
        chartPaint.setStyle(Paint.Style.FILL);
        chartPaint.setAntiAlias(true);
        chartPaint.setColor(Color.parseColor("#32C9BA"));

        textPaint = new Paint();
        textSize = getResources().getDisplayMetrics().density * 13;
        textPaint.setTextSize(textSize);
        textPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getHeight();
        int width = getWidth();
        if (chartEntities != null) {

            int maxValue = 0;
            for (int i = 0; i < chartEntities.size(); i++) {
                ChartEntity chartEntity = chartEntities.get(i);
                if (chartEntity.getNum() > maxValue) maxValue = chartEntity.getNum();
            }
            if (maxValue != 0) {
                perHeight = (int) ((getHeight()-2*textSize - 40) / maxValue);
            }
            int size = chartEntities.size();
            int perWidth = width / (2 * size - 1);
            for (int i = 0; i < chartEntities.size(); i++) {
                ChartEntity chartEntity = chartEntities.get(i);
                rect.bottom = (int) (height - 2 * textSize);
                rect.left = (perWidth * (2 * i));
                rect.right = (perWidth * (2 * i + 1));
                rect.top = (int) (height - perHeight * chartEntity.getNum() - 2 * textSize);
                canvas.drawRect(rect, chartPaint);

                String numString = chartEntity.getNum() + "";
                float numStringLen = getTextLen(numString, textSize);
                canvas.drawText(numString, (2 * i + 0.5f) * perWidth - numStringLen / 2F, rect.top - 10, textPaint);

                String titleString = chartEntity.getTitle() + "";
                float titleStringLen = getTextLen(titleString, textSize);
                canvas.drawText(titleString, (2 * i + 0.5f) * perWidth - titleStringLen / 2F, rect.bottom + textSize, textPaint);

            }
        }
        canvas.drawLine(0, (int) (height - 2 * textSize) + 2, width, (int) (height - 2 * textSize) + 2, mLinePaint);
    }

    public void setData(List<ChartEntity> chartEntities) {
        this.chartEntities = chartEntities;
        calc();
        invalidate();
    }

    private void calc() {

    }


    public static class ChartEntity {
        private String title;
        private int num;

        public ChartEntity(String title, int num) {
            this.title = title;
            this.num = num;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }

    public float getTextLen(String content, float textSize) {
        if (TextUtils.isEmpty(content) || textSize <= 0) return 0;
        Paint paint = new Paint();
        paint.setTextSize(textSize);
        return paint.measureText(content);
    }

}
