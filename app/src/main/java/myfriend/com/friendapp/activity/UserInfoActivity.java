package myfriend.com.friendapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.litepal.LitePal;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import myfriend.com.friendapp.Been.BaseBeen;
import myfriend.com.friendapp.Been.Userbeen;
import myfriend.com.friendapp.MainActivity;
import myfriend.com.friendapp.R;

public class UserInfoActivity extends AppCompatActivity {

    @BindView(R.id.edt_user)
    EditText edtUser;
    @BindView(R.id.edt_pwd)
    EditText edtPwd;
    @BindView(R.id.edt_phone)
    EditText edtPhone;
    @BindView(R.id.text_xinbie)
    TextView textXinbie;
    @BindView(R.id.edt_age)
    EditText edtAge;
    @BindView(R.id.edt_address)
    EditText edtAddress;
    @BindView(R.id.btn_save)
    Button btnSave;
    Userbeen userbeen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        initview();
        initdata();
    }

    private void initdata() {
        try {
            userbeen = LitePal.findAll(Userbeen.class).get(0);
        }catch (Exception e){

        }
         if(userbeen!=null) {
             edtUser.setText(userbeen.getUsername());
             edtPwd.setText(userbeen.getPwd());
             edtPhone.setText(userbeen.getPhone());
             edtAddress.setText(userbeen.getAddress());
             edtAge.setText(userbeen.getAge());
             textXinbie.setText(userbeen.getGender());
         }
    }

    @OnClick({R.id.text_xinbie, R.id.btn_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.text_xinbie:
                final List<String> list =new ArrayList<>();
                list.add("男");
                list.add("女");
                OptionsPickerView pickerView =new OptionsPickerBuilder(UserInfoActivity.this, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                            textXinbie.setText(list.get(options1));
                    }
                })
                        .build();
                pickerView.setPicker(list,null,null);
                pickerView.show();
                break;
            case R.id.btn_save:
                String user = edtUser.getText().toString();
                String pwd = edtPwd.getText().toString();
                String age = edtAge.getText().toString();
                String xinbie = textXinbie.getText().toString();
                String address = edtAddress.getText().toString();
                String phone = edtPhone.getText().toString();
                onupdate(user,pwd,age,xinbie,address,phone);
                break;
        }
    }

    private void onupdate(final String user, final String pwd, final String age, final String xinbie, final String address, final String phone) {
        if(TextUtils.isEmpty(user)&&TextUtils.isEmpty(pwd)&&TextUtils.isEmpty(phone)){
            Toast.makeText(this, "不能为空！", Toast.LENGTH_SHORT).show();
        }else {
            RequestParams params = new RequestParams("http://10.0.2.2:8080/MybatisDemo/demo/updaMarkUser");
            params.addQueryStringParameter("Username",user);
            params.addQueryStringParameter("Password",pwd);
            params.addQueryStringParameter("Phone",phone);
            params.addQueryStringParameter("Address",address);
            params.addQueryStringParameter("Gender",xinbie);
            params.addQueryStringParameter("Age",age);
            params.addQueryStringParameter("Userid",userbeen.getUserid());
            x.http().get(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    if(!"[]".equals(result)){
                        Gson gs = new Gson();
                        List<BaseBeen> jsonObject =  gs.fromJson(result,new TypeToken<List<BaseBeen>>(){}.getType());
                        BaseBeen baseBeen = jsonObject.get(0);
                        if(baseBeen.getStatus().equals("200")){
                            Toast.makeText(UserInfoActivity.this, "更新成功！", Toast.LENGTH_SHORT).show();
                            LitePal.deleteAll(Userbeen.class);
                           Userbeen userd = new Userbeen();
                            userd.setUserid(userbeen.getUserid());
                            userd.setAddress(address);
                            userd.setUsername(user);
                            userd.setAge(age);
                            userd.setPhone(phone);
                            userd.setPwd(pwd);
                            userd.setGender(xinbie);
                            userd.save();
                            Intent intent = new Intent(UserInfoActivity.this, MainActivity.class);
                            intent.putExtra("username",user);
                            intent.putExtra("phone",phone);
                            setResult(1500,intent);
                            finish();
                        }else {
                            Toast.makeText(UserInfoActivity.this, "更新失败！", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(UserInfoActivity.this, "更新失败！", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Toast.makeText(x.app(), ex.toString(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {

                }
            });
        }
    }

    private void initview() {
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("修改信息");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case  android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
