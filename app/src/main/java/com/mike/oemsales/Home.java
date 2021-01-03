package com.mike.oemsales;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Home extends AppCompatActivity {

     String  usernamev,mobilenumberv,otpv,emailv,locationv,businessnamev,businesstypev,productsv,pricev,moqv,localotp;
     EditText username,mobilenumber,otp,email,location,businessname,businesstype,products,price,moq;
     Button submitbutton,getotp;
     private RequestQueue mQueue,mQueue2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        username=findViewById(R.id.username);
        mobilenumber=findViewById(R.id.mobilenumber);
        otp=findViewById(R.id.otp);
        email=findViewById(R.id.email);
        location=findViewById(R.id.location);
        businessname=findViewById(R.id.businessname);
        businesstype=findViewById(R.id.businesstype);
        products=findViewById(R.id.products);
        price=findViewById(R.id.price);
        moq=findViewById(R.id.moq);

        submitbutton=findViewById(R.id.submitbutton);
        getotp=findViewById(R.id.getotp);

        // volley
        mQueue = Volley.newRequestQueue(getApplicationContext());
        mQueue2 = Volley.newRequestQueue(getApplicationContext());

        getotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mobilenumber.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter the mobilenumber", Toast.LENGTH_SHORT).show();
                }else{
                    mobilenumberv    = mobilenumber.getText().toString();

                    sendSms(mobilenumberv);
                }


            }
        });


        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                usernamev        = username.getText().toString();
                mobilenumberv    = mobilenumber.getText().toString();
                otpv             = otp.getText().toString();
                emailv           = email.getText().toString();
                locationv        = location.getText().toString();
                businessnamev    = businessname.getText().toString();
                businesstypev    = businesstype.getText().toString();
                productsv        = products.getText().toString();
                pricev           = price.getText().toString();
                moqv             = moq.getText().toString();


                if (username.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter the username", Toast.LENGTH_SHORT).show();
                }

                if (mobilenumber.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter the mobilenumber", Toast.LENGTH_SHORT).show();
                }
                if (otp.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter the otp", Toast.LENGTH_SHORT).show();
                }

                if (email.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter the email", Toast.LENGTH_SHORT).show();
                }

                if (location.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter the location", Toast.LENGTH_SHORT).show();
                }
                if (businessname.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter the businessname", Toast.LENGTH_SHORT).show();
                }
                if (businesstype.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter the businesstype", Toast.LENGTH_SHORT).show();
                }
                if (products.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter the products", Toast.LENGTH_SHORT).show();
                }
                if (price.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter the price", Toast.LENGTH_SHORT).show();
                }

                if (moq.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter the moq", Toast.LENGTH_SHORT).show();
                }



                if(username.getText().toString().isEmpty()&&mobilenumber.getText().toString().isEmpty()&&otp.getText().toString().isEmpty()&&email.getText().toString().isEmpty()
                &&location.getText().toString().isEmpty()&&businessname.getText().toString().isEmpty()&&businesstype.getText().toString().isEmpty()&&products.getText().toString().isEmpty()
                &&products.getText().toString().isEmpty()&&price.getText().toString().isEmpty()&&moq.getText().toString().isEmpty())

                {
                    // here are fields are important
                }
                else{

                    jsonParse();

                    if(otpv==localotp)
                    {

                    }else
                    {
                        Toast.makeText(getApplicationContext(),"wrongotp",Toast.LENGTH_LONG).show();
                    }


                }

            }
        });

    }
    private void jsonParse() {


        String url="https://android.oemindia.com/businessdetails.php";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.i("response", response);

                        // here we are checking for response cases

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("error",error+"");
            }
        }) {
            //adding parameters to the request
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", usernamev);
                params.put("mobilenumber",mobilenumberv );
                params.put("email",emailv );
                params.put("location",locationv );
                params.put("businessname", businessnamev);
                params.put("businesstype",businesstypev );
                params.put("products",productsv );
                params.put("price",pricev );
                params.put("moq",moqv );
                return params;
            }
        };
        // Add the request to the RequestQueue.
        mQueue.add(stringRequest);

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }





    private void sendSms(String mobilenumber){


        String url="https://android.oemindia.com/sendsms.php";

        // Request a string response from the provided URL.
        StringRequest stringRequest2 = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        localotp=response;
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.i("error",error+"");
            }
        }) {
            //adding parameters to the request
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("mobilenumber",mobilenumberv );
                return params;
            }
        };
        // Add the request to the RequestQueue.
        mQueue.add(stringRequest2);

        stringRequest2.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    };
}