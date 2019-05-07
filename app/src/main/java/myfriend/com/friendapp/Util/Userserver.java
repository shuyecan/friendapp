package myfriend.com.friendapp.Util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.litepal.LitePal;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import myfriend.com.friendapp.Been.FriendBeen;
import myfriend.com.friendapp.Been.Userbeen;
import myfriend.com.friendapp.R;

public class Userserver {
    public void addfriend(String friendid, final Context content){
        RequestParams params = new RequestParams(content.getResources().getString(R.string.ip)+"/MybatisDemo/demo/addFriend");
        List<Userbeen> userbeenList = LitePal.findAll(Userbeen.class);
        params.addQueryStringParameter("oneselfId",userbeenList.get(0).getUserid());
        params.addQueryStringParameter("teamFriend",friendid);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(content, "添加成功！", Toast.LENGTH_SHORT).show();
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

    public void delectfriend( String friendid, Context content){
        RequestParams params = new RequestParams(content.getResources().getString(R.string.ip)+"/MybatisDemo/demo/delFriend");
        List<Userbeen> userbeenList = LitePal.findAll(Userbeen.class);
        params.addQueryStringParameter("oneselfId",userbeenList.get(0).getUserid());
        params.addQueryStringParameter("teamFriend",friendid);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("ss",result);
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
