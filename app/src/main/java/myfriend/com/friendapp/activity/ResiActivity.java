package myfriend.com.friendapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import myfriend.com.friendapp.Been.BaseBeen;
import myfriend.com.friendapp.Been.Userbeen;
import myfriend.com.friendapp.MainActivity;
import myfriend.com.friendapp.R;

public class ResiActivity extends AppCompatActivity {

    @BindView(R.id.username)
    AutoCompleteTextView username;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.btn_save)
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resi);
        ButterKnife.bind(this);
        initview();
    }

    @OnClick(R.id.btn_save)
    public void onViewClicked() {
        String name = username.getText().toString();
        String pwd =password.getText().toString();
        String pho = phone.getText().toString();
        if(TextUtils.isEmpty(name)){
            username.setError("用户名不能为空！");

        }else if(TextUtils.isEmpty(pwd)){
            password.setError("密码不能为空！");
        }else if(TextUtils.isEmpty(pho)){
            phone.setError("电话号码不能为空！");
        }else {
            onresi(name,pwd,pho);
        }


    }

    private void onresi(String name, String pwd, String pho) {
        RequestParams params = new RequestParams("http://10.0.2.2:8080/MybatisDemo/demo/addUserInfo");
        params.addQueryStringParameter("Username",name);
        params.addQueryStringParameter("Password",pwd);
        params.addQueryStringParameter("Phone",pho);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if(!"[]".equals(result)){
                    Gson gs = new Gson();
                    List<BaseBeen> jsonObject =  gs.fromJson(result,new TypeToken<List<BaseBeen>>(){}.getType());
                    BaseBeen baseBeen = jsonObject.get(0);
                    if(baseBeen.getStatus().equals("200")){
                        Toast.makeText(ResiActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
                            finish();
                    }else {
                        Toast.makeText(ResiActivity.this, "注册失败！", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(ResiActivity.this, "注册失败！", Toast.LENGTH_SHORT).show();
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

    private void initview() {
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("注册");
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
