package com.basusingh.sampleurlshortner;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.basusingh.throwat.ThrwAt;
import com.basusingh.throwat.ThrwAtShortener;
import com.basusingh.throwat.ThrwAtShortenerTask;
import com.basusingh.throwat.ThrwAtSyncManager;
import com.basusingh.throwat.ThrwAtTask;
import com.basusingh.throwat.onSignupCompleteListener;
import com.basusingh.throwat.onUrlShortListener;

public class MainActivity extends AppCompatActivity{

    LinearLayout doneLayout;
    TextView tinyUrl;
    EditText custom;
    ThrwAtShortenerTask mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        doneLayout = findViewById(R.id.doneLayout);
        tinyUrl = findViewById(R.id.tinyUrl);
        custom = findViewById(R.id.custom);


        Button viewAll = findViewById(R.id.viewAll);
        viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ViewAll.class));
            }
        });

        Button sync = findViewById(R.id.sync);
        sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(getApplicationContext(), ThrwAtSyncManager.class));
            }
        });

        Button btnCustom = findViewById(R.id.btnCustom);
        btnCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!custom.getText().toString().isEmpty()){
                    ThrwAtShortener.getInstance(getApplicationContext()).updateCustomShortURL(mTask.getUrlId(), mTask.getUrl(), custom.getText().toString().trim(), new onUrlShortListener() {
                        @Override
                        public void onComplete(ThrwAtShortenerTask task) {
                            if(task.isSuccessful()){
                                tinyUrl.setText("http://thrw.at/" + task.getTinyUrl());
                            } else {
                                Toast.makeText(getApplicationContext(), task.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });


        Button register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ThrwAt.getInstance(getApplicationContext()).isRegistered()){
                    Toast.makeText(getApplicationContext(), "Already registered", Toast.LENGTH_LONG).show();
                } else {
                    register();
                }
            }
        });

        final EditText editText = findViewById(R.id.edit);
        Button create = findViewById(R.id.create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editText.getText().toString().isEmpty()){
                    createShortUrl(editText.getText().toString().trim());
                } else {
                    Toast.makeText(getApplicationContext(), "Enter URL", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @SuppressLint("StaticFieldLeak")
    private void dosome(){
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... p){
                //String url = ThrwAtURLManager.getInstance(getApplicationContext()).getQRCodeURL("78");
                
                //ThrwAtURLManager.getInstance(getApplicationContext()).addTag("78", "Lolva");
                //ThrwAtURLManager.getInstance(getApplicationContext()).addTag("78", "Basu");
                //ThrwAtURLManager.getInstance(getApplicationContext()).addTag("78", "Ashish");
                /**
                List<URLItems> mList = ThrwAtURLManager.getInstance(getApplicationContext()).getAllShortenURLS();
                for(int i = 0; i<mList.size(); i++){
                    URLItems items = mList.get(i);
                    Log.e("ID", items.getUrlId());
                    String[] mT = ThrwAtURLManager.getInstance(getApplicationContext()).parseTags(items.getTags());
                    for(int j = 0; j<mT.length; j++){
                        Log.e("Tags", mT[j]);
                    }
                }
                **/
                return null;
            }
        }.execute();
    }

    private void createShortUrl(String url){
        ThrwAtShortener.getInstance(getApplicationContext()).createShortURL(url, new onUrlShortListener() {
            @Override
            public void onComplete(ThrwAtShortenerTask task) {
                if(task.isSuccessful()){
                    Log.e("ID", task.getUrlId());
                    mTask = task;
                    doneLayout.setVisibility(View.VISIBLE);
                    tinyUrl.setText("thrw.at/" + task.getTinyUrl());
                } else {
                    Toast.makeText(getApplicationContext(), task.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void register(){
        ThrwAt.getInstance(getApplicationContext()).registerUser("Thrw At", "no-reply@thrw.at", "HowIsThisAPassword", new onSignupCompleteListener() {
            @Override
            public void onComplete(ThrwAtTask task) {
                Toast.makeText(getApplicationContext(), task.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


}