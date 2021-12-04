package com.example.fragmentproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fragmentproject.holder.RecyclerViewHolder;

import java.util.List;

public abstract class BasicRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder>  implements View.OnClickListener {
    private String TAG=getClass().getSimpleName();
    private Context mContext;
    private List<T> mData;
    private int mLayoutId;
    private OnItemClickListener mListener;

    public BasicRecyclerViewAdapter(Context mContext, List<T> mData, int mLayoutId) {
        this.mContext = mContext;
        this.mData = mData;
        this.mLayoutId = mLayoutId;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(mLayoutId,parent,false);
        view.setOnClickListener(this);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.itemView.setTag(position);
        T bean=mData.get(position);
        onBindData(holder,bean,position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onClick(View v) {

        if(mListener!=null){
            mListener.onItemClick(this,v,(Integer)v.getTag());
        }
    }

    /**
     * 数据绑定，由实现类实现
     *
     * @param holder   The reference of the all view within the item.
     * @param bean     The data bean related to the position.
     * @param position The position to bind data.
     */
    protected abstract void onBindData(RecyclerViewHolder holder, T bean, int position);

    /**
     * item点击监听器
     */
    public interface OnItemClickListener {
        /**
         * item点击回调
         *
         * @param adapter  The Adapter where the click happened.
         * @param v        The view that was clicked.
         * @param position The position of the view in the adapter.
         */
        void onItemClick(RecyclerView.Adapter adapter, View v, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mListener = onItemClickListener;
    }

}
