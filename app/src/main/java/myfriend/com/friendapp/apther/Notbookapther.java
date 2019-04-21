package myfriend.com.friendapp.apther;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.litepal.LitePal;
import org.w3c.dom.Text;

import java.util.List;

import myfriend.com.friendapp.Been.Notbookbeen;
import myfriend.com.friendapp.R;

public class Notbookapther extends RecyclerView.Adapter<Notbookapther.ViewHolder> {
    private List<Notbookbeen> list;
    private Context context;

    public Notbookapther(List<Notbookbeen> list, Context context) {
        this.list = list;
        this.context = context;
        LitePal.initialize(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (context == null) {
            context = viewGroup.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.item_notbook, viewGroup, false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        final Notbookbeen notbookbeen = list.get(i);
        viewHolder.content_text.setText(notbookbeen.getConent());
        viewHolder.time_text.setText(notbookbeen.getData());
        viewHolder.img_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notbookbeen.delete();
                deleteitem(i);
            }
        });
    }

    public void deleteitem(int p){
        list.remove(p);
        notifyItemRemoved(p);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView time_text,content_text;
        LinearLayout linearLayout;
        ImageView img_del;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = (LinearLayout) itemView;
            time_text = itemView.findViewById(R.id.time_text);
            content_text = itemView.findViewById(R.id.content_text);
            img_del = itemView.findViewById(R.id.img_del);
        }
    }
}
