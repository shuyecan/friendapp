package myfriend.com.friendapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import myfriend.com.friendapp.Been.Notbookbeen;
import myfriend.com.friendapp.R;
import myfriend.com.friendapp.apther.Notbookapther;

public class NotbookActivity extends AppCompatActivity {

    @BindView(R.id.not_list)
    RecyclerView notList;
    Notbookapther apther;
    List<Notbookbeen> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notbook);
        ButterKnife.bind(this);
        LitePal.initialize(this);
        inittoorer();
        initdata();
    }

    private void initdata() {
        List<Notbookbeen> notbookbeens = LitePal.findAll(Notbookbeen.class);
        list.addAll(notbookbeens);
        apther = new Notbookapther(list,getApplicationContext());
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        notList.setLayoutManager(layoutManager);
        notList.setAdapter(apther);
    }

    private void inittoorer() {
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("作息管理");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.not_menu, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1000){
            List<Notbookbeen> notbookbeens = LitePal.findAll(Notbookbeen.class);
            list.clear();
            list.addAll(notbookbeens);
            apther.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case  android.R.id.home:
                finish();
                break;
            case R.id.not_add:
                Intent intent =new Intent(NotbookActivity.this,Addnotactivity.class);
                startActivityForResult(intent,1000);
                break;
        }
        return true;
    }
}
