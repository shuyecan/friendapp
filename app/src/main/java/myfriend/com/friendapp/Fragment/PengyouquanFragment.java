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
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import myfriend.com.friendapp.Been.FriendBeen;
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

    }

    @OnClick(R.id.btn_save)
    public void onViewClicked() {
        String content = editContent.getText().toString();
        if(!"".endsWith(content)) {
            editContent.setText("");
            FriendBeen friendBeen = new FriendBeen();
            friendBeen.setUsername("我");
            friendBeen.setContent(content);
            apther.addData(friendBeen);
        }
    }
}
