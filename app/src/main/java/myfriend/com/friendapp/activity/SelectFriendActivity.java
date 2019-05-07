package myfriend.com.friendapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import org.litepal.LitePal;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import myfriend.com.friendapp.Been.FriendBeen;
import myfriend.com.friendapp.Been.Userbeen;
import myfriend.com.friendapp.MainActivity;
import myfriend.com.friendapp.R;
import myfriend.com.friendapp.apther.ResultApther;

public class SelectFriendActivity extends AppCompatActivity {


    @BindView(R.id.search_query_byname)
    SearchView searchQueryByname;
    @BindView(R.id.rec_select_friend)
    RecyclerView rec_select_friend;
    ResultApther apther;
    List<FriendBeen> list = new ArrayList<>();
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
                getdata(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        apther = new ResultApther(list,getApplicationContext());
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        rec_select_friend.setLayoutManager(layoutManager);
        rec_select_friend.setAdapter(apther);
    }

    private void getdata(String s) {
        RequestParams params = new RequestParams(getResources().getString(R.string.ip)+"/MybatisDemo/demo/getUserFriend");
        List<Userbeen> userbeenList = LitePal.findAll(Userbeen.class);
        params.addQueryStringParameter("userId",userbeenList.get(0).getUserid());
        params.addQueryStringParameter("Username",s);
        params.addQueryStringParameter("type","2");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                    Gson gs = new Gson();
                    List<Userbeen> jsonObject =  gs.fromJson(result,new TypeToken<List<Userbeen>>(){}.getType());
                    list.clear();
                    for (int i=0;i<jsonObject.size();i++){
                        FriendBeen friendBeen = new FriendBeen();
                        friendBeen.setFriendid(jsonObject.get(i).getUserid());
                        friendBeen.setUsername(jsonObject.get(i).getUsername());
                        list.add(friendBeen);
                    }
                    apther.notifyDataSetChanged();
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
