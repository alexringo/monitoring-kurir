package com.cour_monitor.pesanobat;

import android.content.Intent;
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

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Me on 26/05/2016.
 */
public class prosesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    SwipeRefreshLayout swipe;
    ArrayList<HashMap<String, String>> itemList;
    ListAdapter adapter;
    int success;
    LayoutInflater inflater;
    String id,nama,alamat;
    ListView list;

    private String TAG=prosesFragment.class.getSimpleName();
    public static final String ITEM_ID = "id";
    public static final String ITEM_NAME = "nama";
    public static final String ITEM_ALAMAT = "alamat";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.prosesfragment_layout, parent, false);
        list = (ListView)rootview.findViewById(R.id.list_proses);
        itemList =  new ArrayList<HashMap<String, String>>();
        swipe = (SwipeRefreshLayout) rootview.findViewById(R.id.swipe_refresh_layout);
        swipe.setOnRefreshListener(this);
        swipe.post(new Runnable() {
            @Override
            public void run() {
                swipe.setRefreshing(true);
                itemList.clear();
                fetchPesanan();
            }
        });
        swipe.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        return rootview;
    }

    private void fetchPesanan() {
        swipe.setRefreshing(true);
        itemList.clear();
        String url = "http://10.0.2.2/distributor-obat/android/select.php?proses=proses";

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
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
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

    @Override
    public void onRefresh() {
        itemList.clear();
        fetchPesanan();
    }
}
