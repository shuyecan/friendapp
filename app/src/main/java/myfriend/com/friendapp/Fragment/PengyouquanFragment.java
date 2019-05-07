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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;
import org.litepal.LitePal;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import myfriend.com.friendapp.Been.BaseBeen;
import myfriend.com.friendapp.Been.FriendBeen;
import myfriend.com.friendapp.Been.PyqBeen;
import myfriend.com.friendapp.Been.Userbeen;
import myfriend.com.friendapp.R;
import myfriend.com.friendapp.apther.Memoraapther;
import myfriend.com.friendapp.apther.PyqApther;

public class PengyouquanFragment extends Fragment {


    View view;
    @BindView(R.id.recy_pyq)
    RecyclerView recyPyq;
    @BindView(R.id.edit_content)
    EditText editContent;
    @BindView(R.id.btn_save)
    Button btnSave;
    PyqApther apther;
    List<FriendBeen> list = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_pengyouquan, container, false);
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
        apther = new PyqApther(list,getActivity());
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
        recyPyq.setLayoutManager(layoutManager);
        recyPyq.setAdapter(apther);
        getdata();
    }

    private void getdata() {
        RequestParams params = new RequestParams(getResources().getString(R.string.ip)+"/MybatisDemo/demo/getComments");
        List<Userbeen> userbeenList = LitePal.findAll(Userbeen.class);
        params.addQueryStringParameter("oneselfId",userbeenList.get(0).getUserid());
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("sss",result);
                    Gson gson = new Gson();
                    List<PyqBeen> pyqlist = gson.fromJson(result,new TypeToken<List<PyqBeen>>(){}.getType());
                    for (int i =0;i<pyqlist.size();i++){
                        FriendBeen friendBeen = new FriendBeen();
                        friendBeen.setUsername(pyqlist.get(i).getTitle());
                        friendBeen.setContent(pyqlist.get(i).getMsg());
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
    public void onResume() {
        super.onResume();
        getdata();
    }

    @OnClick(R.id.btn_save)
    public void onViewClicked() {
        String content = editContent.getText().toString();
        if(!"".endsWith(content)) {
            editContent.setText("");
            List<Userbeen> userbeenList = LitePal.findAll(Userbeen.class);
            FriendBeen friendBeen = new FriendBeen();
            friendBeen.setUsername(userbeenList.get(0).getUsername());
            friendBeen.setFriendid(userbeenList.get(0).getUserid());
            friendBeen.setContent(content);
            apther.addData(friendBeen);
            setdata(friendBeen);
        }
    }

    private void setdata(FriendBeen friendBeen) {
        RequestParams params = new RequestParams(getResources().getString(R.string.ip)+"/MybatisDemo/demo/addCommentMsg");

        params.addQueryStringParameter("msg",friendBeen.getContent());
        params.addQueryStringParameter("title",friendBeen.getUsername());
        params.addQueryStringParameter("senUserId",friendBeen.getFriendid());
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

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
