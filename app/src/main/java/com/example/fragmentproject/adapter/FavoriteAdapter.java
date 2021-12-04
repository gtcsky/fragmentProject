package com.example.fragmentproject.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fragmentproject.Bean.MyFavorite;
import com.example.fragmentproject.R;
import com.example.fragmentproject.holder.RecyclerViewHolder;

import java.util.List;

public class FavoriteAdapter extends BasicRecyclerViewAdapter<MyFavorite> {
    private Context mContext;
    private List<MyFavorite> mData;
    private int mLayoutId;
    private OnItemClickListener onItemClickListener;

    public FavoriteAdapter(Context mContext, List mData, int mLayoutId) {
        super(mContext, mData, mLayoutId);
    }

    @Override
    protected void onBindData(RecyclerViewHolder holder, MyFavorite favorite, int position) {
        View favLayout = holder.getView(R.id.favorite_layout);
        favLayout.setTag(position);
        if (onItemClickListener!=null){
            favLayout.setOnClickListener(v -> onItemClickListener.OnItemClick(v,position));
        }
        ((TextView) holder.getView(R.id.favorite_title)).setText(favorite.getTitle());
        ((TextView) holder.getView(R.id.favorite_context)).setText(favorite.getContext());
        ImageView icon=(ImageView) holder.getView(R.id.icon_favorite);
        icon.setColorFilter(Color.YELLOW);
    }

    public interface OnItemClickListener{
        void OnItemClick(View v,int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
