package myfriend.com.friendapp.apther;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import myfriend.com.friendapp.Been.FriendBeen;
import myfriend.com.friendapp.R;

public class PyqApther extends RecyclerView.Adapter<PyqApther.ViewHolder>{
    private List<FriendBeen> list;
    private Context context;

    public PyqApther(List<FriendBeen> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public PyqApther.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (context == null) {
            context = viewGroup.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.item_pyq, viewGroup, false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(@NonNull PyqApther.ViewHolder viewHolder, int i) {
            FriendBeen friendBeen = list.get(i);
            viewHolder.text_name .setText( friendBeen.getUsername());
            viewHolder.text_content.setText(friendBeen.getContent());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addData(FriendBeen friendBeen) {
        //   在list中添加数据，并通知条目加入一条
        list.add(friendBeen);
        //添加动画
        notifyItemInserted(list.size()-1);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text_name,text_content;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text_name = itemView.findViewById(R.id.text_name);
            text_content = itemView.findViewById(R.id.text_content);
        }
    }
}
