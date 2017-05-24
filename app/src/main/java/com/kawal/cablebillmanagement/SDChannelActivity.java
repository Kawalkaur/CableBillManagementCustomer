package com.kawal.cablebillmanagement;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

public class SDChannelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sdchannel);
        GridView gridview = (GridView) findViewById(R.id.gridviewsd);
        gridview.setAdapter(new SDImageAdapter(this));

    }
}
