package myfriend.com.friendapp.apther;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import myfriend.com.friendapp.Been.Memorandbeen;
import myfriend.com.friendapp.R;
import myfriend.com.friendapp.Util.CalendarReminderUtils;

public class Memoraapther extends RecyclerView.Adapter<Memoraapther.ViewHolder>{
    private List<Memorandbeen> list;
    private Context context;

    public Memoraapther(List<Memorandbeen> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public Memoraapther.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (context == null) {
            context = viewGroup.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.item_beiwang, viewGroup, false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Memoraapther.ViewHolder viewHolder, final int i) {
            final Memorandbeen memorandbeen = list.get(i);
            viewHolder.text_address.setText(memorandbeen.getAddress());
            viewHolder.text_content.setText(memorandbeen.getContent());
            viewHolder.text_time.setText(memorandbeen.getTime());
            viewHolder.text_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    memorandbeen.delete();
                    deleteitem(i,memorandbeen.getContent());
                }
            });
    }

    public void deleteitem(int p,String t){
        list.remove(p);
        CalendarReminderUtils.deleteCalendarEvent(context,t);
        notifyItemRemoved(p);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView text_content,text_address,text_time,text_delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card);
            text_content = itemView.findViewById(R.id.text_content);
            text_address = itemView.findViewById(R.id.text_address);
            text_time = itemView.findViewById(R.id.text_time);
            text_delete = itemView.findViewById(R.id.text_delete);
        }
    }
}
