package myfriend.com.friendapp.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

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
    Memorandbeen apther;
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
//        apther = new Memoraapther(list,getApplicationContext());
//        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
//        memoranList.setLayoutManager(layoutManager);
//        memoranList.setAdapter(apther);
    }

    private void inittoorer() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("行程备忘");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
}
