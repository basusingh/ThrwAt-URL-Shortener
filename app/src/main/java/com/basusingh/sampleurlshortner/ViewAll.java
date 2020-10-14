package com.basusingh.sampleurlshortner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;

import com.basusingh.throwat.ThrwAtURLManager;
import com.basusingh.throwat.db.URLItems;

import java.util.ArrayList;
import java.util.List;

public class ViewAll extends AppCompatActivity {

    private RecyclerView recyclerView;
    List<URLItems> list;
    Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);

        list = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        loadItem();
    }

    @SuppressLint("StaticFieldLeak")
    private void loadItem(){
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... v){
                list = ThrwAtURLManager.getInstance(getApplicationContext()).getAllShortenURLS();
                return null;
            }
            @Override
            public void onPostExecute(Void v){
                mAdapter = new Adapter(getApplicationContext(), list);
                recyclerView.setAdapter(mAdapter);
            }
        }.execute();
    }
}