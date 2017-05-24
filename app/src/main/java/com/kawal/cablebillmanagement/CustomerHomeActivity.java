package com.kawal.cablebillmanagement;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CustomerHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Button SD, HD;
    RequestQueue requestQueue;
    ProgressDialog progressdialog;
    int status=1;

    void views(){
        preferences=getSharedPreferences(Util.PREFS_NAME,MODE_PRIVATE);
        editor = preferences.edit();
        requestQueue= Volley.newRequestQueue(this);
        progressdialog = new ProgressDialog(this);
        progressdialog.setMessage("Please wait...");
        progressdialog.setCancelable(false);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

//        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:9888777921"));
                if (ActivityCompat.checkSelfPermission(CustomerHomeActivity.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);
            }

        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        views();

        SD = (Button) findViewById(R.id.button6);
        SD.setOnClickListener(this);

        HD = (Button)findViewById(R.id.button7);
        HD.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.customer_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_logout) {

            AlertDialog.Builder ad =  new  AlertDialog.Builder(CustomerHomeActivity.this);
            ad.setTitle("Do You Wish to Logout");
            ad.setCancelable(false);
            ad.setNegativeButton("Yes", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    editor.clear();
                    editor.commit();
                    Intent i=new Intent(CustomerHomeActivity.this,CustomerRegistration.class);
                    startActivity(i);
                    // finishAffinity();

                }
            });
            ad.setPositiveButton("No",null);
            ad.create().show();

        } else if (id == R.id.nav_Disconnect) {
            AlertDialog.Builder ad =  new  AlertDialog.Builder(CustomerHomeActivity.this);
            ad.setTitle("Really wish to disconnect your Service ?");
            ad.setCancelable(false);
            ad.setNegativeButton("Yes", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    changeStatus();


                    // finishAffinity();

                }
            });
            ad.setPositiveButton("No",null);
            ad.create().show();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void changeStatus() {
        StringRequest request = new StringRequest(Request.Method.POST, Util.CHANGE_STATUS_PHP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("TEST", response);
                progressdialog.show();

//                Toast.makeText(AdminRegistration.this, "Success", Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(AdminRegistration.this, AdminHomeActivity.class);
//                startActivity(intent);
//

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    int success = jsonObject.getInt("success");
                    String message = jsonObject.getString("message");

                    if (success == 1) {
                        Toast.makeText(CustomerHomeActivity.this, message, Toast.LENGTH_SHORT).show();
                        editor.clear();
                        editor.commit();
                        Intent i=new Intent(CustomerHomeActivity.this,CustomerRegistration.class);
                        startActivity(i);
                            finish();

                    } else {
                        Toast.makeText(CustomerHomeActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                    progressdialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(CustomerHomeActivity.this, "Some Exception", Toast.LENGTH_SHORT).show();
                }
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //       progressDialog.dismiss();
                Toast.makeText(CustomerHomeActivity.this, "Some Error" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();


                map.put("cid",String.valueOf(preferences.getInt("id",0)) );
                map.put("status","0");
                Log.i("test",preferences.getInt("id",0)+"");
                return map;
            }
        };
        requestQueue.add(request);


    }

    @Override
    public void onClick(View v) {
        if (v.getId()== R.id.button6){
            Intent intent = new Intent(CustomerHomeActivity.this, SDChannelActivity.class);
            startActivity(intent);
        }else
        if (v.getId()==R.id.button7){
            Intent intent1 = new Intent(CustomerHomeActivity.this,HDChannelActivity.class);
            startActivity(intent1);

        }
    }
}
