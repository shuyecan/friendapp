package myfriend.com.friendapp.apther;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import myfriend.com.friendapp.Been.FriendBeen;
import myfriend.com.friendapp.R;
import myfriend.com.friendapp.Util.Userserver;

public class ResultApther extends RecyclerView.Adapter<ResultApther.ViewHolder>{
    private List<FriendBeen> list;
    private Context context;

    public ResultApther(List<FriendBeen> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public ResultApther.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (context == null) {
            context = viewGroup.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.item_friend_result, viewGroup, false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ResultApther.ViewHolder viewHolder, int i) {
            final FriendBeen friendBeen = list.get(i);
            viewHolder.text_username.setText(friendBeen.getUsername());
            Glide.with(context).load(R.drawable.timg).into(viewHolder.img_usehead);
            viewHolder.button_shenqing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Userserver userserver= new Userserver();
                    userserver.addfriend(friendBeen.getFriendid(),context);
                }
            });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text_username;
        ImageView img_usehead;
        Button button_shenqing;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text_username = itemView.findViewById(R.id.text_username);
            img_usehead = itemView.findViewById(R.id.img_usehead);
            button_shenqing = itemView.findViewById(R.id.button_shenqing);
        }
    }
}
