package com.project.pemesananobat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpResponse;

/**
 * Created by Me on 18/05/2016.
 */
public class prosesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    SwipeRefreshLayout swipe;
    ArrayList<HashMap<String, String>> itemList;
    ListAdapter adapter;
    int success;
    LayoutInflater inflater;
    String id,nama,alamat;
    ListView list;
    private GPSDatabase gpsDatabase;

    private String TAG=prosesFragment.class.getSimpleName();
    public static final String ITEM_ID = "id";
    public static final String ITEM_NAME = "nama";
    public static final String ITEM_ALAMAT = "alamat";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.proses_list, parent, false);
        list = (ListView)rootview.findViewById(R.id.list_proses);
        itemList =  new ArrayList<HashMap<String, String>>();
        gpsDatabase = new GPSDatabase(getContext(),"track_location",null,1);
        swipe = (SwipeRefreshLayout) rootview.findViewById(R.id.swipe_refresh_layout);
        swipe.setOnRefreshListener(this);
        swipe.post(new Runnable() {
            @Override
            public void run() {
                swipe.setRefreshing(true);
                itemList.clear();
                fetchPesanan();
                syncLocation();
            }
        });
        swipe.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        return rootview;
    }

    private void syncLocation() {
        String url = "http://10.0.2.2/distributor-obat/android/insert.php";
        AsyncHttpClient client = new AsyncHttpClient();
        final RequestParams params = new RequestParams();
        params.put("geolocation", gpsDatabase.composeJSONfromSQLite());
            client.post(url, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    try {
                        JSONArray arr = new JSONArray(responseBody);
                        gpsDatabase.updateSyncStatus();
                        Toast.makeText(getContext(),"DB Synced",Toast.LENGTH_SHORT).show();

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        Toast.makeText(getActivity(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                }
            });
    }

    @Override
    public void onRefresh() {
        itemList.clear();
        fetchPesanan();
        syncLocation();
    }

    private void fetchPesanan(){
        swipe.setRefreshing(true);
        itemList.clear();
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String user = sharedPreferences.getString(Config.USER_SHARED_PREF, "Not Available");
        String url = "http://10.0.2.2/distributor-obat/android/select.php?status=proses&user="+user;

        JsonArrayRequest fetchorder = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        HashMap<String, String> item = new HashMap<>();
                        item.put(ITEM_ID, obj.getString("id"));
                        item.put(ITEM_NAME, obj.getString("nama"));
                        item.put(ITEM_ALAMAT, obj.getString("alamat"));

                        itemList.add(item);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                String[] from = {ITEM_ID, ITEM_NAME, ITEM_ALAMAT};
                int[] to = {R.id.id, R.id.nama, R.id.alamat};
                adapter = new SimpleAdapter(getActivity(), itemList, R.layout.list_row, from, to);
                list.setAdapter(adapter);
                list.setOnItemClickListener(new ListitemClickListener());
                swipe.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Server eror: " + error.getMessage());
                Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_SHORT).show();
                swipe.setRefreshing(false);
            }
        });

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(fetchorder);
    }

    class ListitemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent detailpesanan = new Intent(getActivity(), FragmentDetail.class);
            detailpesanan.putExtra("item", itemList.get(position));

            startActivity(detailpesanan);
        }
    }



}
