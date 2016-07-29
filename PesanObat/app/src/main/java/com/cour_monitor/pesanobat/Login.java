package com.cour_monitor.pesanobat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    private EditText editTextUser;
    private EditText editTextPassword;
    private Button buttonLogin;
    ProgressDialog PD;

    //boolean variable to check user is logged in or not
    //initially it is false
    private boolean loggedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonLogin = (Button) findViewById(R.id.btn_login);
        editTextUser = (EditText) findViewById(R.id.txt_user);
        editTextPassword = (EditText) findViewById(R.id.txt_pass);
        PD = new ProgressDialog(this);
        PD.setMessage("please wait.....");
        PD.setCancelable(false);


        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

    }

    private void login() {
        PD.show();
        final String user = editTextUser.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.LOGIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    if (response.equalsIgnoreCase("success")) {
                        PD.dismiss();
                        //Creating a shared preference
                        SharedPreferences sharedPreferences = Login.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                        //Creating editor to store values to shared preferences
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        //Adding values to editor
                        editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, true);
                        editor.putString(Config.USER_SHARED_PREF, user);

                        //Saving values to editor
                        editor.apply();

                        //Starting account activity
                        Intent intent = new Intent(Login.this, Home.class);
                        startActivity(intent);

                    } else {
                        PD.dismiss();
                        Toast.makeText(getApplicationContext(),
                                "Username or Password are invalid", Toast.LENGTH_SHORT)
                                .show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("user", user);
                try {
                    params.put("password", getMD5Hash(password));
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }

                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //In onresume fetching value from sharedpreference
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        //Fetching the boolean value form sharedpreferences
        loggedIn = sharedPreferences.getBoolean(Config.LOGGEDIN_SHARED_PREF, false);

        //If we will get true
        if(loggedIn){
            //We will start the Profile Activity
            Intent intent = new Intent(Login.this, Home.class);
            startActivity(intent);
        }
    }

    //md5 encrypter using MessageDigest
    public  String getMD5Hash(String s) throws NoSuchAlgorithmException {

        String result = s;
        if (s != null) {
            MessageDigest md = MessageDigest.getInstance("MD5"); // or "SHA-1"
            md.update(s.getBytes());
            BigInteger hash = new BigInteger(1, md.digest());
            result = hash.toString(16);
            while (result.length() < 32) { // 40 for SHA-1
                result = "0" + result;
            }
        }
        return result;
    }
}
