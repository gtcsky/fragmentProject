package com.example.fragmentproject.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.fragmentproject.R;

import java.util.List;

public class MessageAdapter extends ArrayAdapter {
    private String TAG=getClass().getSimpleName();
    private Context context;
    private int resource;
    private OnItemClickListener onItemClickListener;

    public MessageAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        this.context=context;
        this.resource=resource;
    }

    public void setData(List<String> list){
        clear();
        addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row=convertView;
        MessageHolder holder;
        if(row==null){
            row = LayoutInflater.from(context).inflate(resource,parent,false);
            holder=new MessageHolder();
            holder.message_text=row.findViewById(R.id.message_item);
            holder.icon=row.findViewById(R.id.message_icon);
            row.setTag(holder);
        }
        holder=(MessageHolder) row.getTag();

        holder.message_text.setText((String)getItem(position));
        holder.icon.setColorFilter(context.getResources().getColor(R.color.yellow_900));

        LinearLayout linearLayout=row.findViewById(R.id.message_grid);
        if(onItemClickListener!=null){
            linearLayout.setOnClickListener(v -> onItemClickListener.onItemClickListener(v,position));
        }
//        if(position%2!=0){
//            Log.d(TAG, "getView: "+position+"\t:"+holder.message_text.getText().toString());
//            linearLayout.setBackgroundColor(Color.GRAY);
//        }
        return row;
    }

    class MessageHolder{
        TextView message_text;
        ImageView icon;
    }

    public interface OnItemClickListener{
         void onItemClickListener(View v,int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
