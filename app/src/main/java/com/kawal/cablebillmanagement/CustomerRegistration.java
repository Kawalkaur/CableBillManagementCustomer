package com.kawal.cablebillmanagement;

import android.content.Intent;
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
    @InjectView(R.id.spinner)
    Spinner connectionType;
    ArrayAdapter<String> adapter;
    UserBean uBean;
    int pos=0;
//    int status =1;

    RequestQueue requestQueue;
    String selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_registration);
        ButterKnife.inject(this);
        _signupButton.setOnClickListener(this);
        uBean = new UserBean();

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
    }

    @Override
    public void onClick(View v) {
int id=v.getId();
        if(id==R.id.btn_signup) {
            Toast.makeText(this, "Button Clicked", Toast.LENGTH_SHORT).show();
            insertCustomer();
        }
        }


    void insertCustomer() {
        uBean.setUserType(2);
        uBean.setuName(_nameText.getText().toString().trim());
        uBean.setuPhone(_mobileText.getText().toString().trim());
        uBean.setuEmail(_emailText.getText().toString().trim());
        uBean.setuPassword(_passwordText.getText().toString().trim());
        uBean.setuAddress(_addressText.getText().toString().trim());
        insertIntoCloud();
    }

    public void insertIntoCloud()
    {
        final String token = FirebaseInstanceId.getInstance().getToken();
        Log.i("TOKEN",token);
        StringRequest request = new StringRequest(Request.Method.POST, Util.INSERT_USER_PHP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("TEST", response);
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    if (jsonObject.getInt("success")==1) {
                        Toast.makeText(CustomerRegistration.this, "Success", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(CustomerRegistration.this, CustomerHomeActivity.class);
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CustomerRegistration.this, "Some Error "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("uName", uBean.getuName());
                Log.i("uname",uBean.getuName());
                map.put("uPhone", uBean.getuPhone());
                Log.i("uPhone", uBean.getuPhone());
                map.put("uEmail", uBean.getuEmail());
                Log.i("uEmail", uBean.getuEmail());
                map.put("uPassword", uBean.getuPassword());
                Log.i("uPassword", uBean.getuPassword());
                map.put("connectionType",selected);
            //  Log.i("conn", selected);
                map.put("uAddress", uBean.getuAddress());
                Log.i("uAddress", uBean.getuAddress());
                map.put("userType", String.valueOf(uBean.getUserType()));
                map.put("token", token);
//                map.put("status", String.valueOf(status));


                return map;

            }
        };
        requestQueue.add(request);
//        clearFields();
    }
    void clearFields() {
        _nameText.setText("");
        _mobileText.setText("");
        _emailText.setText("");
        _passwordText.setText("");
        _addressText.setText("");

    }
}