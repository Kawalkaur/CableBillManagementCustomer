package com.kawal.cablebillmanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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
    UserBean uBean;

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_registration);
        ButterKnife.inject(this);
        _signupButton.setOnClickListener(this);
        uBean = new UserBean();

        requestQueue = Volley.newRequestQueue(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_signup) {
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

    public void insertIntoCloud() {
        StringRequest request = new StringRequest(Request.Method.POST, Util.INSERT_USER_PHP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("TEST", response);
                Toast.makeText(CustomerRegistration.this, "Success", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(CustomerRegistration.this, CustomerHomeActivity.class);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CustomerRegistration.this, "Some Error", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("uName", uBean.getuName());
                map.put("uPhone", uBean.getuPhone());
                map.put("uEmail", uBean.getuEmail());
                map.put("uPassword", uBean.getuPassword());
                map.put("uAddress", uBean.getuAddress());
                map.put("userType", String.valueOf(uBean.getUserType()));

                return map;

            }
        };
        requestQueue.add(request);

    }
}