package myfriend.com.friendapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import myfriend.com.friendapp.Been.Memorandbeen;
import myfriend.com.friendapp.R;
import myfriend.com.friendapp.apther.Memoraapther;

public class Memorandumactivity extends AppCompatActivity {

    @BindView(R.id.memoran_list)
    RecyclerView memoranList;
    Memoraapther apther;
    List<Memorandbeen> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memorandumactivity);
        ButterKnife.bind(this);
        LitePal.initialize(this);
        inittoorer();
        initdata();
    }

    private void initdata() {
        List<Memorandbeen> memorandbeens = LitePal.findAll(Memorandbeen.class);
        list.addAll(memorandbeens);
        apther = new Memoraapther(list,getApplicationContext());
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        memoranList.setLayoutManager(layoutManager);
        memoranList.setAdapter(apther);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.not_menu, menu);
        return true;
    }

    private void inittoorer() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("行程备忘");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1000){
            List<Memorandbeen> memorandbeens = LitePal.findAll(Memorandbeen.class);
            list.clear();
            list.addAll(memorandbeens);
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
                Intent intent =new Intent(Memorandumactivity.this,AddmemorandActivity.class);
                startActivityForResult(intent,1000);
                break;
        }
        return true;
    }
}
