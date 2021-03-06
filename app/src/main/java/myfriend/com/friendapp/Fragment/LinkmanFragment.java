package myfriend.com.friendapp.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
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
import butterknife.Unbinder;
import myfriend.com.friendapp.Been.FriendBeen;
import myfriend.com.friendapp.Been.Userbeen;
import myfriend.com.friendapp.R;
import myfriend.com.friendapp.Util.Userserver;
import myfriend.com.friendapp.apther.FriendApther;
import myfriend.com.friendapp.apther.Mian_messageApther;

public class LinkmanFragment extends Fragment {
    View view;
    @BindView(R.id.rec_friend)
    SwipeRecyclerView recFriend;
    List<FriendBeen> list = new ArrayList<>();
    FriendApther apther;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_linkman, container, false);
            ButterKnife.bind(this, view);
            initView();
        } else {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (null != parent) {
                parent.removeView(view);
            }
        }

        return view;
    }

    private void initView() {

        SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int position) {
                int width = getResources().getDimensionPixelSize(R.dimen.dp_75);
                int height = getResources().getDimensionPixelSize(R.dimen.dp_70);
                SwipeMenuItem deleteItem = new SwipeMenuItem(getActivity());
                deleteItem.setText("删除好友")
                        .setBackground(R.color.red)
                        .setTextColor(getResources().getColor(R.color.white))
                        .setWidth(width)
                        .setHeight(height);
                rightMenu.addMenuItem(deleteItem); // 在Item右侧添加一个菜单。
                // 注意：哪边不想要菜单，那么不要添加即可。
            }
        };
        recFriend.setSwipeMenuCreator(mSwipeMenuCreator);

        recFriend.setOnItemMenuClickListener(new OnItemMenuClickListener() {
            @Override
            public void onItemClick(SwipeMenuBridge menuBridge, final int adapterPosition) {
                menuBridge.closeMenu();
                new MaterialDialog.Builder(getActivity())
                        .title("删除")
                        .content("确定删除该好友？")
                        .positiveText("确认")
                        .negativeText("取消")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                apther.deleteitem(adapterPosition);

                            }
                        })
                        .show();
            }
        });

//        FriendBeen friendBeen = new FriendBeen();
//        friendBeen.setFriendid(1);
//        friendBeen.setUsername("的货物");
//        list.add(friendBeen);

//        FriendBeen friendBeen2 = new FriendBeen();
//        friendBeen2.setFriendid(1);
//        friendBeen2.setUsername("哈哈哈");
//        list.add(friendBeen2);
        apther = new FriendApther(list,getContext());
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
        recFriend.setLayoutManager(layoutManager);
        recFriend.setAdapter(apther);
        getdata();
    }

    @Override
    public void onResume() {
        super.onResume();
        getdata();
    }

    private void getdata() {
        RequestParams params = new RequestParams(getResources().getString(R.string.ip)+"/MybatisDemo/demo/getUserFriend");
        List<Userbeen> userbeenList = LitePal.findAll(Userbeen.class);
        params.addQueryStringParameter("userId",userbeenList.get(0).getUserid());
        params.addQueryStringParameter("type","1");
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        LitePal.deleteAll(Userbeen.class);
    }
}