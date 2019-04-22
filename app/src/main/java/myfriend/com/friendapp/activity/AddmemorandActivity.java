package myfriend.com.friendapp.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import myfriend.com.friendapp.Been.Memorandbeen;
import myfriend.com.friendapp.R;
import myfriend.com.friendapp.Util.CalendarReminderUtils;

public class AddmemorandActivity extends AppCompatActivity {

    @BindView(R.id.text_start_time)
    TextView textStartTime;
    @BindView(R.id.lin_time)
    LinearLayout linTime;
    @BindView(R.id.edt_address)
    EditText edtAddress;
    @BindView(R.id.lin_address)
    LinearLayout linAddress;
    @BindView(R.id.edt_content)
    EditText edtContent;
    @BindView(R.id.lin_content)
    LinearLayout linContent;
    @BindView(R.id.swt_isshow)
    Switch swtIsshow;
    @BindView(R.id.btn_save)
    Button btnSave;
    private Date mdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addmemorand);
        ButterKnife.bind(this);
        inittooler();
    }

    private void inittooler() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("添加备忘");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @OnClick({R.id.lin_time, R.id.btn_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lin_time:
                settime();
                break;
            case R.id.btn_save:
                String time = textStartTime.getText().toString();
                String address = edtAddress.getText().toString();
                String content = edtContent.getText().toString();
                boolean isshow = swtIsshow.isChecked();
                if(TextUtils.isEmpty(time)&&TextUtils.isEmpty(address)&&TextUtils.isEmpty(content)){
                    Toast.makeText(this, "数据不能为空", Toast.LENGTH_SHORT).show();
                }else {
                    if(isshow){
                        CalendarReminderUtils.addCalendarEvent(getApplicationContext(),content,content+" "+address,mdate.getTime(),2);
                    }
                    Memorandbeen memorandbeen = new Memorandbeen();
                    memorandbeen.setAddress(address);
                    memorandbeen.setContent(content);
                    memorandbeen.setIscall(isshow ?"true":"false");
                    memorandbeen.setTime(time);
                    memorandbeen.save();
                    setResult(1000);
                    finish();
                }
                break;
        }
    }
    private String getTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(date);
    }

    private void settime() {
        TimePickerView pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                    textStartTime.setText(getTime(date));
                    mdate = date;
            }
        }).setType(new boolean[]{true, true, true, true, true, false})
                .build();
        // pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
        pvTime.show();
    }
}
