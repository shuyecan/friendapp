package myfriend.com.friendapp.apther;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.List;

import myfriend.com.friendapp.Been.FriendBeen;
import myfriend.com.friendapp.R;
import myfriend.com.friendapp.Util.CalendarReminderUtils;
import myfriend.com.friendapp.Util.Userserver;

public class FriendApther extends SwipeRecyclerView.Adapter<FriendApther.ViewHolder> {
    private List<FriendBeen> list;
    private Context context;

    public FriendApther(List<FriendBeen> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (context == null) {
            context = viewGroup.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.item_myfriend, viewGroup, false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            FriendBeen friendBeen = list.get(i);
            viewHolder.text_username.setText(friendBeen.getUsername());
            Glide.with(context).load(R.drawable.timg).into(viewHolder.img_usehead);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void deleteitem(int p){
        Userserver userserver =new Userserver();
        userserver.delectfriend(list.get(p).getFriendid(),context);
        list.remove(p);
        notifyItemRemoved(p);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_usehead;
        TextView text_username;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text_username = itemView.findViewById(R.id.text_username);
            img_usehead= itemView.findViewById(R.id.img_usehead);
        }
    }
}
