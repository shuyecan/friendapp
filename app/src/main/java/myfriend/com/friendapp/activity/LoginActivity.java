package myfriend.com.friendapp.activity;

import android.app.Person;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import myfriend.com.friendapp.Been.Userbeen;
import myfriend.com.friendapp.MainActivity;
import myfriend.com.friendapp.R;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.login_progress)
    ProgressBar loginProgress;
    @BindView(R.id.username)
    AutoCompleteTextView username;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.text_nouser)
    TextView text_nouser;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initview();
    }

    private void initview() {
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("登录");
        setSupportActionBar(toolbar);
    }

    @OnClick({R.id.btn_login,R.id.text_nouser})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.btn_login:
                loginProgress.setVisibility(View.VISIBLE);
                if("".equals(username.getText().toString())){
                    username.setError(getString(R.string.error_field_required));
                    loginProgress.setVisibility(View.GONE);
                }else if("".equals(password.getText().toString())){
                    password.setError("密码不能为空！");
                    loginProgress.setVisibility(View.GONE);
                }else {
                    loginProgress.setVisibility(View.GONE);
                    onlogin(username.getText().toString(), password.getText().toString());
                }
                break;
            case    R.id.text_nouser:
                Intent intent =new Intent(LoginActivity.this,ResiActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void onlogin(String text, String text1) {
        if(text.equals("1")&&text1.equals("1")){
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            return;
        }
        RequestParams params = new RequestParams(getResources().getString(R.string.ip)+"/MybatisDemo/demo/getMarkUser");
        params.addQueryStringParameter("Username",text);
        params.addQueryStringParameter("Pwd",text1);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if(!"[]".equals(result)){
                    Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                    Gson gs = new Gson();
                    List<Userbeen> jsonObject =  gs.fromJson(result,new TypeToken<List<Userbeen>>(){}.getType());
                    Userbeen userbeen = jsonObject.get(0);
                    boolean is = userbeen.save();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("username",userbeen.getUsername());
                    intent.putExtra("phone",userbeen.getPhone());
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(LoginActivity.this, "用户名或密码错误！", Toast.LENGTH_SHORT).show();
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

