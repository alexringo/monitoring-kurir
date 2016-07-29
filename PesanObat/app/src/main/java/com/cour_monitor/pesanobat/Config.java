package com.cour_monitor.pesanobat;

/**
 * Created by Me on 26/05/2016.
 */
public class Config {
    //URL to our login.php file
    public static final String LOGIN_URL = "http://10.0.2.2/distributor-obat/android/login.php";

    //Keys for user and password as defined in our $_POST['key'] in login.php
    public static final String KEY_USER = "user";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_TOKEN = "gcm_token";
    public static final String KEY_KURIR = "kurir";

    //If server response is equal to this that means login is successful
    public static final String LOGIN_SUCCESS = "success";
    public static final String GCM_UPDATED = "success";

    //Keys for Sharedpreferences
    //This would be the name of our shared preferences
    public static final String SHARED_PREF_NAME = "myloginapp";

    //This would be used to store the user of current logged in user
    public static final String USER_SHARED_PREF = "user";
    public static final String GCM_SHARED_PREF = "token";

    //We will use this to store the boolean in sharedpreference to track user is loggedin or not
    public static final String LOGGEDIN_SHARED_PREF = "loggedin";
}
