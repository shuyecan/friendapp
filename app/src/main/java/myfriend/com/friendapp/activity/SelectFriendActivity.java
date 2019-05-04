package myfriend.com.friendapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.ImageView;

import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import myfriend.com.friendapp.R;

public class SelectFriendActivity extends AppCompatActivity {


    @BindView(R.id.search_query_byname)
    SearchView searchQueryByname;
    @BindView(R.id.rec_select_friend)
    RecyclerView rec_select_friend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_friend);
        ButterKnife.bind(this);
        initview();
    }

    private void initview() {
        searchQueryByname.setIconifiedByDefault(false);
        searchQueryByname.setSubmitButtonEnabled(true);
        ImageView iv_submit = searchQueryByname.findViewById(R.id.search_go_btn);
        iv_submit.setImageResource(R.drawable.ic_check_black_24dp);

        searchQueryByname.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });



    }
}
