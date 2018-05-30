package kodwell.com.mydigitalclock;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextClock;
import android.widget.TextView;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by PC on 5/30/2018.
 */

public class MyDigiClock extends LinearLayout {
        private Calendar mTime;
        private TextView tvHour;
        private TextView tvMinute;
        private TextView tvDay;
        private TextView tvMonth;
        private TextView tvSecond;
        private TextView tvMeridian;
        private static final int DALEY = 1000;

        public MyDigiClock(Context context) {
            super(context);
        }

        public MyDigiClock(Context context, AttributeSet attrs) {
            this(context, attrs, 0);
        }

        public MyDigiClock(Context context, AttributeSet attrs, int defStyleAttr) {
            this(context, attrs, defStyleAttr, 0);
        }

        public MyDigiClock(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyDigiClock, defStyleAttr, 0);
            LayoutInflater.from(context).inflate(R.layout.text_clock_layout, this);
            this.tvHour = (TextView)this.findViewById(R.id.hour);
            this.tvMinute = (TextView)this.findViewById(R.id.minute);
            this.tvDay = (TextView)this.findViewById(R.id.day);
            this.tvMonth = (TextView)this.findViewById(R.id.month);
            this.tvMeridian = (TextView)this.findViewById(R.id.meridian);
            this.tvSecond = (TextView)this.findViewById(R.id.second);

            try {
                int color = a.getColor(R.styleable.MyDigiClock_color, -16777216);
                this.tvHour.setTextColor(color);
                this.tvMinute.setTextColor(color);
                this.tvDay.setTextColor(color);
                this.tvMonth.setTextColor(color);
                this.tvMeridian.setTextColor(color);
                this.tvSecond.setTextColor(color);
                this.findViewById(R.id.separador).setBackgroundColor(color);
            } finally {
                a.recycle();
            }

            this.init();
        }

        private void init() {
            this.createTime(TimeZone.getDefault().getID());
            this.setTime();
            (new Handler()).postDelayed(new Runnable() {
                public void run() {
                    MyDigiClock.this.setTime();
                    MyDigiClock.this.getHandler().postDelayed(this, 1000L);
                }
            }, 1000L);
        }

        private void createTime(String timeZone) {
            if(timeZone != null) {
                this.mTime = Calendar.getInstance(TimeZone.getTimeZone(timeZone));
            } else {
                this.mTime = Calendar.getInstance();
            }

        }

        private void setTime() {
            this.mTime.setTimeInMillis(System.currentTimeMillis());
            this.tvHour.setText(DateFormat.format("hh", this.mTime));
            this.tvMinute.setText(DateFormat.format("mm", this.mTime));
            this.tvSecond.setText(":" + DateFormat.format("ss", this.mTime));
            this.tvMeridian.setText(DateFormat.format("a", this.mTime));
            this.tvDay.setText(DateFormat.format("dd", this.mTime));
            this.tvMonth.setText(DateFormat.format("MMM", this.mTime));
        }
}
