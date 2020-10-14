package com.basusingh.sampleurlshortner;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.basusingh.throwat.ThrwAtURLManager;
import com.basusingh.throwat.db.URLItems;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private Context context;
    private List<URLItems> list;

    public Adapter(Context mContext, List<URLItems> mItems){
        this.context = mContext;
        this.list = mItems;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tinyUrl, timestamp, url;
        RecyclerView recyclerView;
        public ViewHolder(View v){
            super(v);
            tinyUrl = v.findViewById(R.id.tinyUrl);
            timestamp = v.findViewById(R.id.timestamp);
            url = v.findViewById(R.id.url);
            recyclerView = v.findViewById(R.id.recyclerView);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position){
        View v = LayoutInflater.from(context).inflate(R.layout.item_url_viewer, parent, false);
        final ViewHolder holder = new ViewHolder(v);
        holder.recyclerView.setHasFixedSize(true);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position){
        final URLItems i = list.get(position);
        viewHolder.tinyUrl.setText("thrw.at/" + i.getTinyUrl());
        viewHolder.url.setText(i.getUrl());
        viewHolder.tinyUrl.setTextColor(context.getResources().getColor(getRandomColor()));
        if(i.getTags() == null || i.getTags().isEmpty()){
            viewHolder.recyclerView.setVisibility(View.GONE);
        } else {
            viewHolder.recyclerView.setVisibility(View.VISIBLE);
            AdapterTags adapterTags = new AdapterTags(context, ThrwAtURLManager.getInstance(context).parseTags(i.getTags()));
            viewHolder.recyclerView.setAdapter(adapterTags);
        }

        try{
            DateFormat df=new SimpleDateFormat("dd-mm-yyyy");
            Date date=df.parse(i.getTimestamp());
            viewHolder.timestamp.setText(df.format(date));
        } catch (Exception e){
            e.printStackTrace();
            viewHolder.timestamp.setText(i.getTimestamp());
        }
    }

    @Override
    public int getItemCount(){
        return list.size();
    }

    private int getRandomColor(){
        int[] color = {R.color.blue_grey, R.color.purple, R.color.home_gradient_2_2, R.color.home_gradient_2, R.color.home_gradient_2_2_light,
                        R.color.home_gradient_5_5, R.color.gradient_red_cream_dark, R.color.deep_blue};
        return color[new Random().nextInt(color.length)];
    }
}

