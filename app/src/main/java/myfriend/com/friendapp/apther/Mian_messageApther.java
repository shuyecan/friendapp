package myfriend.com.friendapp.apther;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import myfriend.com.friendapp.Been.Memorandbeen;
import myfriend.com.friendapp.R;
import myfriend.com.friendapp.Util.CalendarReminderUtils;

public class Mian_messageApther extends RecyclerView.Adapter<Mian_messageApther.ViewHolder>{
    private List<Memorandbeen> list;
    private Context context;

    public Mian_messageApther(List<Memorandbeen> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public Mian_messageApther.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (context == null) {
            context = viewGroup.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.item_message, viewGroup, false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Mian_messageApther.ViewHolder viewHolder, final int i) {
            final Memorandbeen memorandbeen = list.get(i);
            viewHolder.text_liaotian.setText(memorandbeen.getContent());
            viewHolder.text_username.setText(memorandbeen.getTime());
            Glide.with(context).load(memorandbeen.getImg()).into(viewHolder.img_usehead);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView text_username,text_liaotian;
        ImageView img_usehead;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card);
            text_username = itemView.findViewById(R.id.text_username);
            text_liaotian = itemView.findViewById(R.id.text_liaotian);
            img_usehead = itemView.findViewById(R.id.img_usehead);
        }
    }
}
