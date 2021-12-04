package com.example.fragmentproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.fragmentproject.Bean.OtaFileInfo;
import com.example.fragmentproject.R;

import org.w3c.dom.ls.LSException;

import java.util.List;

public class OtaFileInfoAdapter extends ArrayAdapter {
    private Context context;
    private int resource;

    public OtaFileInfoAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
    }

    public void setData(List<OtaFileInfo> data){
        clear();
        addAll(data);
        notifyDataSetChanged();

    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View row=convertView;
        OtaInfoHolder holder;
        if(row==null){
            row= LayoutInflater.from(context).inflate(resource,parent,false);
            holder=new OtaInfoHolder();
            holder.versionText=row.findViewById(R.id.id_version);
            holder.descText=row.findViewById(R.id.id_description);
            row.setTag(holder);
        }

        holder=(OtaInfoHolder) row.getTag();
        OtaFileInfo otaFileInfo=(OtaFileInfo) getItem(position);
        holder.versionText.setText(otaFileInfo.getVersion());
        holder.descText.setText(otaFileInfo.getDescription());

        return row;
    }

    class OtaInfoHolder{
        TextView versionText;
        TextView descText;
    }

}
