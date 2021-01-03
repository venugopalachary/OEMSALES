package com.mike.oemsales;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
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

public class Login extends AppCompatActivity {
    // MY_PREFS_NAME - a static String variable like:
    public static final String MY_PREFS_NAME = "userDetails";
       EditText empid;
       Button loginbutton;
       private RequestQueue mQueue;
       String employeeId, device_id;
       SharedPreferences sp,settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Hooks
         empid =findViewById(R.id.empid);
         loginbutton=findViewById(R.id.loginbutton);




        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }





        // volley
        mQueue = Volley.newRequestQueue(getApplicationContext());



         loginbutton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if(empid.getText().toString().isEmpty())
                 {
                     Toast.makeText(getApplicationContext(),"Please Enter Employeeid",Toast.LENGTH_LONG).show();
                 }
                 else{
                     employeeId = empid.getText().toString();

                     jsonParse();
                 }
             }
         });


    }


    private void jsonParse() {


        String subdomainurl="https://android.oemindia.com/checkuser.php";


        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, subdomainurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    //    Log.i("response", response);

                        // here we are checking for response cases
                        Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();

                        // HERE WE WILL DONE UPDATING IMEI
                        if(response.equalsIgnoreCase("success")) {
                            //here we are first deleting past progress dialog to show new dialog

                            // here we are getting employee id from edit text box
                            employeeId = empid.getText().toString();
                            //#1 getting shared preferences
                            sp = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

                            //#2 initializing editor
                            SharedPreferences.Editor editor = sp.edit();

                            //#3 Put the values
                            editor.putString("employeeId", employeeId);
                            //   editor.putString("imeino", imeino);

                            // #4 Apply the changes Then from the Editor instance we will simply call the apply() method to save the changes.
                            editor.apply();



                            // HERE WE ARE SETTING THE VALUE USER LOGINNED AS TRUE

                            settings = getSharedPreferences("MyPrefsFile", MODE_PRIVATE);

                            SharedPreferences.Editor settingsEditor = settings.edit();

                            settingsEditor.putBoolean("hasLoggedIn", true);

                            settingsEditor.apply();


                            Intent i = new Intent(getApplicationContext(), Home.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);



                            // Display the response string.

                        }else{
                            Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                        }


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
                params.put("empid", employeeId);
                return params;
            }
        };
        // Add the request to the RequestQueue.
        mQueue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(  500000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
      //  stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 10, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }


}