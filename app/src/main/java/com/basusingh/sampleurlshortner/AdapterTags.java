package com.basusingh.sampleurlshortner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.basusingh.throwat.db.URLItems;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class AdapterTags extends RecyclerView.Adapter<AdapterTags.ViewHolder> {

    private Context context;
    private String[] list;

    public AdapterTags(Context mContext, String[] mItems){
        this.context = mContext;
        this.list = mItems;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tagName;
        public ViewHolder(View v){
            super(v);
            tagName = v.findViewById(R.id.tagName);
        }
    }

    @NonNull
    @Override
    public AdapterTags.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position){
        View v = LayoutInflater.from(context).inflate(R.layout.item_url_tags, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull final AdapterTags.ViewHolder viewHolder, int position){
        viewHolder.tagName.setText(list[position]);
    }

    @Override
    public int getItemCount(){
        return list.length;
    }
}

