package com.kawal.cablebillmanagement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class CustomerRegistration extends AppCompatActivity implements View.OnClickListener {
    @InjectView(R.id.input_name)
    EditText _nameText;
    @InjectView(R.id.input_address)
    EditText _addressText;
    @InjectView(R.id.input_email)
    EditText _emailText;
    @InjectView(R.id.input_mobile)
    EditText _mobileText;
    @InjectView(R.id.input_password)
    EditText _passwordText;
    @InjectView(R.id.btn_signup)
    Button _signupButton;
    @InjectView(R.id.link_login)
    TextView _loginLink;
    @InjectView(R.id.spinner)
    Spinner connectionType;
    ArrayAdapter<String> adapter;
    UserBean uBean, rcvUser;
    int pos=0;
    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    boolean updateMode;
    String selected;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_registration);
        ButterKnife.inject(this);
        preferences = getSharedPreferences(Util.PREFS_NAME, MODE_PRIVATE);
        editor = preferences.edit();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait....");
        progressDialog.setCancelable(false);

        _signupButton.setOnClickListener(this);
        uBean = new UserBean();
        Intent rcv = getIntent();
        updateMode = rcv.hasExtra("keyUser");

        adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item);
        adapter.add("--Connection Type--");
        adapter.add("SD-Rs.300");
        adapter.add("HD-Rs.450");
        connectionType.setAdapter(adapter);
        connectionType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position>0){
                     selected = connectionType.getSelectedItem().toString();
                    Log.i("spinner", selected);
                    pos=position;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        requestQueue = Volley.newRequestQueue(this);

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CustomerLogin.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        if (updateMode) {
            rcvUser = (UserBean) rcv.getSerializableExtra("keyUser");
            _nameText.setText(rcvUser.getuName());
            _mobileText.setText(rcvUser.getuPhone());
            _emailText.setText(rcvUser.getuEmail());
            _passwordText.setText(rcvUser.getuPassword());
            _addressText.setText(rcvUser.getuAddress());
            _signupButton.setText("Update");

        }

    }
//    boolean isNetworkConnected() {
//        connectivityManager  = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
//
//        networkInfo = connectivityManager.getActiveNetworkInfo();
//
//        return (networkInfo != null && networkInfo.isConnected());
//
//    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_signup) {
            Toast.makeText(this, "Button Clicked", Toast.LENGTH_SHORT).show();
            insertCustomer();
            if (validateFields()) {
              //  if (isNetworkConnected())
                    insertIntoCloud();
                //else
                 //   Toast.makeText(this, "Please connect to internet", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Please correct input", Toast.LENGTH_LONG).show();
            }
        }
        }


    void insertCustomer() {
        uBean.setUserType(2);
        uBean.setuName(_nameText.getText().toString().trim());
        uBean.setuPhone(_mobileText.getText().toString().trim());
        uBean.setuEmail(_emailText.getText().toString().trim());
        uBean.setuPassword(_passwordText.getText().toString().trim());
        uBean.setuAddress(_addressText.getText().toString().trim());
       // insertIntoCloud();
    }

    public void insertIntoCloud()
    {
        final String token = FirebaseInstanceId.getInstance().getToken();
        Log.i("TOKEN",token);

        Log.i("TEST", uBean.toString());

        String url = "";
        if (!updateMode) {
            url = Util.INSERT_USER_PHP;

        } else {
            Log.e("user", rcvUser.toString());
            url = Util.UPDATE_USER_PHP;
        }
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("TEST", response);
                progressDialog.show();
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    if (jsonObject.getInt("success")==1) {
                        Toast.makeText(CustomerRegistration.this, "Success", Toast.LENGTH_LONG).show();
                        if (!updateMode) {
                            editor.putString(Util.KEY_NAME, uBean.getuName());
                            editor.putString(Util.KEY_PHONE, uBean.getuPhone());
                            editor.putString(Util.KEY_EMAIL, uBean.getuEmail());
                            editor.putString(Util.KEY_PASSWORD, uBean.getuPassword());
                            editor.putString(Util.KEY_ADDRESS, uBean.getuAddress());
                        editor.putInt("id",jsonObject.getInt("insertId"));
                        editor.commit();
                        Intent intent = new Intent(CustomerRegistration.this, CustomerHomeActivity.class);
                        startActivity(intent);
                        Log.i("test",preferences.getInt("id",0)+"");
                            finish();
                    }if (updateMode)
                            finish();
                    } else {
                        Toast.makeText(CustomerRegistration.this, "Error", Toast.LENGTH_SHORT).show();           }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(CustomerRegistration.this, "Some Exception", Toast.LENGTH_SHORT).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(CustomerRegistration.this, "Some Error "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                if (updateMode)
                    map.put("id", String.valueOf(rcvUser.getId()));
                map.put("uName", uBean.getuName());
                Log.i("uname",uBean.getuName());
                map.put("uPhone", uBean.getuPhone());
                Log.i("uPhone", uBean.getuPhone());
                map.put("uEmail", uBean.getuEmail());
                Log.i("uEmail", uBean.getuEmail());
                map.put("uPassword", uBean.getuPassword());
                Log.i("uPassword", uBean.getuPassword());
                map.put("connectionType",selected);
                map.put("uAddress", uBean.getuAddress());
                Log.i("uAddress", uBean.getuAddress());
                map.put("userType", String.valueOf(uBean.getUserType()));
                Log.i("userType", String.valueOf(uBean.getUserType()));
                map.put("token", token);
//                map.put("status", String.valueOf(status));


                return map;

            }
        };
        requestQueue.add(request);
//        clearFields();
    }
    boolean validateFields() {
        boolean flag = true;

        if (uBean.getuName().isEmpty()) {

            flag = false;
            _nameText.setError("Please Enter Name");
        }
        if (uBean.getuPhone().isEmpty()) {

            flag = false;
            _mobileText.setError("Please Enter Phone");
        } else {
            if (uBean.getuPhone().length() < 10) {

                flag = false;
                _mobileText.setError("Please Enter 10 didgits phone number");
            }
        }

        if(uBean.getuEmail().isEmpty()) {
            flag = false;
            _emailText.setError("Please Enter Email");
        } else {
            if (!uBean.getuEmail().contains("@") && uBean.getuEmail().contains(".")) {

                flag = false;
                _emailText.setError("Please Enter Correct Email");
            }
        }
        if(uBean.getuPassword().isEmpty()) {
            flag = false;
            _passwordText.setError("Please Enter Password");
        }


        if(uBean.getuAddress().isEmpty()) {
            flag = false;
            _addressText.setError("Please Enter Address");
        }

        return flag;
    }
    void clearFields() {
        _nameText.setText("");
        _mobileText.setText("");
        _emailText.setText("");
        _passwordText.setText("");
        _addressText.setText("");

    }
}