package myfriend.com.friendapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.LitePal;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import myfriend.com.friendapp.Been.Memorandbeen;
import myfriend.com.friendapp.Been.Userbeen;
import myfriend.com.friendapp.Fragment.LinkmanFragment;
import myfriend.com.friendapp.Fragment.MessageFragment;
import myfriend.com.friendapp.Fragment.PengyouquanFragment;
import myfriend.com.friendapp.activity.LoginActivity;
import myfriend.com.friendapp.activity.Memorandumactivity;
import myfriend.com.friendapp.activity.NotbookActivity;
import myfriend.com.friendapp.activity.SelectFriendActivity;
import myfriend.com.friendapp.activity.UserInfoActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.main_viewpage)
    ViewPager mainViewpage;
    @BindView(R.id.main_botton)
    BottomNavigationView mainBotton;
    List<Fragment> fragmentList = new ArrayList<>();
    View draw;
    TextView text_user,text_phone;
    Userbeen userbeen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();



        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
         draw = navigationView.inflateHeaderView(R.layout.nav_header_main);
        ImageView userImg = draw.findViewById(R.id.imageView);
        try {
             userbeen = LitePal.findAll(Userbeen.class).get(0);
        }catch (Exception e){

        }
        text_user = draw.findViewById(R.id.user);
        text_phone = draw.findViewById(R.id.phone);
        if(userbeen!=null){
            text_user.setText(userbeen.getUsername());
            text_phone.setText(userbeen.getPhone());
        }
        userImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!text_user.getText().equals("未登录")){
                    return;
                }
                Intent intent =new Intent(MainActivity.this,LoginActivity.class);
                startActivityForResult(intent,2000);
            }
        });

            text_phone.setText(getIntent().getStringExtra("phone"));
            text_user.setText(getIntent().getStringExtra("username"));

        initview();
    }

    private void initview() {
        fragmentList.add(new MessageFragment());
        fragmentList.add(new LinkmanFragment());
        fragmentList.add(new PengyouquanFragment());
        mainViewpage.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragmentList.get(i);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        });

        mainViewpage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                mainBotton.getMenu().getItem(i).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        mainBotton.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.tab_one:
                        mainViewpage.setCurrentItem(0,false);
                        return true;
                    case R.id.tab_two:
                        mainViewpage.setCurrentItem(1,false);
                        return true;
                    case R.id.tab_thire:
                        mainViewpage.setCurrentItem(2,false);
                        return true;

                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this,SelectFriendActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent;
        if (id == R.id.nav_zuoxi) {
           intent = new Intent(MainActivity.this,NotbookActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_beiwang) {
            intent = new Intent(MainActivity.this,Memorandumactivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_slideshow) {
            text_user.setText("未登录");
            text_phone.setText("");
            LitePal.deleteAll(Userbeen.class);
            Toast.makeText(this, "退出登陆成功！", Toast.LENGTH_SHORT).show();
            intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_manage) {
            if(!text_user.getText().equals("未登录")){
                Intent intent3 = new Intent(MainActivity.this, UserInfoActivity.class);
                startActivityForResult(intent3,1500);
            }
        } else if (id == R.id.nav_share) {
            Intent intent4 = new Intent(Intent.ACTION_SEND);
            intent4.setType("text/plain");
            List<ResolveInfo> resInfo = MainActivity.this.getPackageManager().queryIntentActivities(intent4,
                    PackageManager.MATCH_DEFAULT_ONLY);
            if (!resInfo.isEmpty()) {
                List<Intent> targetedShareIntents = new ArrayList<Intent>();
                for (ResolveInfo info : resInfo) {
                    Intent targeted = new Intent(Intent.ACTION_SEND);
                    targeted.setType("text/plain");
                    ActivityInfo activityInfo = info.activityInfo;
                    Log.v("logcat", "packageName=" + activityInfo.packageName + "Name=" + activityInfo.name);
                    // 分享出去的内容
                    targeted.putExtra(Intent.EXTRA_TEXT, "校园生活助手");
                    // 分享出去的标题
                    targeted.putExtra(Intent.EXTRA_SUBJECT, "校园生活助手");
                    targeted.setPackage(activityInfo.packageName);
                    targeted.setClassName(activityInfo.packageName, info.activityInfo.name);
                    PackageManager pm = MainActivity.this.getApplication().getPackageManager();
                    //微信有2个怎么区分-。- 朋友圈还有微信
                    String xuna = info.activityInfo.applicationInfo.loadLabel(pm).toString();
                    if (xuna.equals("微信")||xuna.equals("QQ")) {
                        targetedShareIntents.add(targeted);
                    }
                }
                // 选择分享时的标题
                if(targetedShareIntents.size()==0){
                    Toast.makeText(MainActivity.this, "未找到合适的分享app", Toast.LENGTH_SHORT).show();
                    return false;
                }
                Intent chooserIntent = Intent.createChooser(targetedShareIntents.remove(0), "选择分享");
                if (chooserIntent == null) {
                    return false;
                }
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetedShareIntents.toArray(new Parcelable[] {}));
                try {
                    startActivity(chooserIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(MainActivity.this, "分享app未安装", Toast.LENGTH_SHORT).show();
                }}
        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2000){

        }else if(requestCode==1500){
            if(data!=null) {
                text_phone.setText(data.getStringExtra("phone"));
                text_user.setText(data.getStringExtra("username"));
            }
        }
    }
}
