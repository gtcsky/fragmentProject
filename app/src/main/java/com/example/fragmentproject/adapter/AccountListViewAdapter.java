package com.example.fragmentproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.fragmentproject.Bean.Account;
import com.example.fragmentproject.R;

import java.util.List;

public class AccountListViewAdapter extends ArrayAdapter<Account> {
    private String TAG=getClass().getSimpleName();
    private Context context;
    private int resource;
    public AccountListViewAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        this.context=context;
        this.resource=resource;
    }

    public void setData(List<Account> list){
        if(list!=null){
            clear();
            addAll(list);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        return super.getView(position, convertView, parent);
        AccountHolder holder;
        View row=convertView;
        if(row==null){
            row= LayoutInflater.from(context).inflate(resource,parent,false);
            holder=new AccountHolder();
            holder.idView=row.findViewById(R.id.column_id);
            holder.nameView=row.findViewById(R.id.column_name);
            holder.amountView=row.findViewById(R.id.column_amount);
            row.setTag(holder);
        }else{
            holder=(AccountHolder)row.getTag();
        }
        Account account=getItem(position);
        holder.idView.setText(account.getId()+"");
        holder.nameView.setText(account.getName());
        holder.amountView.setText(account.getAmount()+"");

        return row;

    }


    class AccountHolder{
         TextView idView;
         TextView nameView;
         TextView amountView;

    }
}
