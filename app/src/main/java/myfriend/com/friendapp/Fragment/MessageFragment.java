package myfriend.com.friendapp.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import myfriend.com.friendapp.Been.Memorandbeen;
import myfriend.com.friendapp.R;
import myfriend.com.friendapp.apther.Memoraapther;
import myfriend.com.friendapp.apther.Mian_messageApther;

public class MessageFragment extends Fragment {
    View view;
    List<Memorandbeen> list = new ArrayList<>();
    @BindView(R.id.rec_main)
    RecyclerView recMain;
    Mian_messageApther apther;
    Field field = null;
    int r_id;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragmnet_message, container, false);
            ButterKnife.bind(this, view);
            initdata();
        } else {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (null != parent) {
                parent.removeView(view);
            }
        }

        return view;
    }

    private void initdata() {
        Memorandbeen memorandbeen = new Memorandbeen();
        memorandbeen.setTime("小七");
        memorandbeen.setContent("今天去不去后街？");
        memorandbeen.setImg(R.drawable.touxiang1jpg);
        list.add(memorandbeen);

        Memorandbeen memorandbeen2 = new Memorandbeen();
        memorandbeen2.setTime("夜夜");
        memorandbeen2.setContent("好无聊啊");
        memorandbeen2.setImg(R.drawable.touxiang2);
        list.add(memorandbeen2);

        Memorandbeen memorandbeen3 = new Memorandbeen();
        memorandbeen3.setTime("无敌小海疼");
        memorandbeen3.setContent("嘿嘿，今天老刘摔了一跤");
        memorandbeen3.setImg(R.drawable.touxiang3);
        list.add(memorandbeen3);

        Memorandbeen memorandbeen4 = new Memorandbeen();
        memorandbeen4.setTime("constellation");
        memorandbeen4.setContent("我也觉得");
        memorandbeen4.setImg(R.drawable.touxiang4);
        list.add(memorandbeen4);


        Memorandbeen memorandbeen5 = new Memorandbeen();
        memorandbeen5.setTime("不羁");
        memorandbeen5.setContent("快点出来啊，等你好久了");
        memorandbeen5.setImg(R.drawable.touxiang5);
        list.add(memorandbeen5);

        Memorandbeen memorandbeen6 = new Memorandbeen();
        memorandbeen6.setTime("露露");
        memorandbeen6.setContent("我今天血亏！！[愤怒]");
        memorandbeen6.setImg(R.drawable.touxiang6);
        list.add(memorandbeen6);


        Memorandbeen memorandbeen7= new Memorandbeen();
        memorandbeen7.setTime("卡哇伊");
        memorandbeen7.setContent("那个游戏一点也不好玩啊，没意思");
        memorandbeen7.setImg(R.drawable.touxiang7);
        list.add(memorandbeen7);

        Memorandbeen memorandbeen8 = new Memorandbeen();
        memorandbeen8.setTime("蛋蛋");
        memorandbeen8.setContent("在不去买楼下便利店关门了");
        memorandbeen8.setImg(R.drawable.touxiang8);
        list.add(memorandbeen8);

        Memorandbeen memorandbeen9 = new Memorandbeen();
        memorandbeen9.setTime("戒烟");
        memorandbeen9.setContent("我在三号机这里");
        memorandbeen9.setImg(R.drawable.touxiang9);
        list.add(memorandbeen9);

        apther = new Mian_messageApther(list,getContext());
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
        recMain.setLayoutManager(layoutManager);
        recMain.setAdapter(apther);
    }
}
