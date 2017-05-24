package com.kawal.cablebillmanagement;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

public class HDChannelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hdchannel);
        GridView gridview = (GridView) findViewById(R.id.gridviewhd);
        gridview.setAdapter(new HDImageAdapter(this));

    }
    }

