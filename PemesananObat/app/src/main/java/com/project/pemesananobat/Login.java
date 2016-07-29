package com.project.pemesananobat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.security.MessageDigest;

public class Login extends AppCompatActivity implements View.OnClickListener {

    //Defining views
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


        buttonLogin.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        //In onresume fetching value from sharedpreference
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME,Context.MODE_PRIVATE);

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

    private void login(){
        PD.show();
        //Getting values from edit texts
        final String user = editTextUser.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();

        Map<String,String> params = new HashMap<String,String>();
        params.put("user", user);
        try {
            params.put("password", getMD5Hash(password));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        CustomRequest login_request = new CustomRequest(Request.Method.POST,Config.LOGIN_URL,params,new Response.Listener<JSONObject>(){

            @Override
            public void onResponse(JSONObject response) {

                try {
                    int success = response.getInt("success");

                    if (success == 1) {
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

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        //Adding the string request to the queue

        VolleySingleton.getInstance(this).addToRequestQueue(login_request);
    }

    @Override
    public void onClick(View v) {
        //Calling the login function
        login();
    }
}

