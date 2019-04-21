package myfriend.com.friendapp.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import myfriend.com.friendapp.Been.Notbookbeen;
import myfriend.com.friendapp.R;

public class Addnotactivity extends AppCompatActivity {

    @BindView(R.id.text_start_time)
    TextView textStartTime;
    @BindView(R.id.lin_start_time)
    LinearLayout linStartTime;
    @BindView(R.id.text_end_time)
    TextView textEndTime;
    @BindView(R.id.lin_end_time)
    LinearLayout linEndTime;
    @BindView(R.id.edt_content)
    EditText edtContent;
    @BindView(R.id.btn_save)
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnotactivity);
        ButterKnife.bind(this);
        inittooler();
    }

    private void inittooler() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("添加作息");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    private String getTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(date);
    }

    @OnClick({R.id.lin_start_time, R.id.lin_end_time,R.id.btn_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lin_start_time:
                settime(0);
                break;
            case R.id.lin_end_time:
                settime(1);
                break;
            case R.id.btn_save:
                String connent = edtContent.getText().toString().trim();
                if(TextUtils.isEmpty(textStartTime.getText())&&TextUtils.isEmpty(textEndTime.getText())){
                    Toast.makeText(this, "时间不能为空", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(connent)){
                    Toast.makeText(this, "事件内容不能为空", Toast.LENGTH_SHORT).show();
                }else {
                    Notbookbeen notbookbeen = new Notbookbeen();
                    notbookbeen.setData(textStartTime.getText().toString()+"-"+textEndTime.getText().toString());
                    notbookbeen.setConent(edtContent.getText().toString());
                    notbookbeen.save();
                    setResult(1000);
                    finish();
                }
                break;
        }
    }

    private void settime(final int t) {
        TimePickerView pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                if (t == 0) {
                    textStartTime.setText(getTime(date));
                } else {
                    textEndTime.setText(getTime(date));
                }
            }
        }).setType(new boolean[]{false, false, false, true, true, false})
                .build();
        // pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
        pvTime.show();
    }
}
