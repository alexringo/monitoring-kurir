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

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Me on 18/05/2016.
 */
public class selesaiFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    SwipeRefreshLayout swipe;
    ArrayList<HashMap<String, String>> item_List;
    ListAdapter adapter;
    int success;
    LayoutInflater inflater;
    String id,nama,alamat;
    ListView list;

    public static final String ITEM_ID = "id";
    public static final String ITEM_NAME = "nama";
    public static final String ITEM_ALAMAT = "alamat";

    private String TAG = selesaiFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.selesai_list, container, false);
        list = (ListView)rootview.findViewById(R.id.list_selesai);
        item_List = new ArrayList<HashMap<String, String>>();
        swipe = (SwipeRefreshLayout) rootview.findViewById(R.id.swipe_refresh_list_selesai);
        swipe.setOnRefreshListener(this);
        swipe.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipe.post(new Runnable() {
            @Override
            public void run() {
                swipe.setRefreshing(true);
                item_List.clear();
                fetchPesanan();
            }
        });

        return rootview;
    }

    private void fetchPesanan() {
        swipe.setRefreshing(true);
        item_List.clear();
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String user = sharedPreferences.getString(Config.USER_SHARED_PREF, "Not Available");
        String url = "http://10.0.2.2/distributor-obat/android/select.php?status=done&user="+user;

        JsonArrayRequest fetchOrder = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());
                for (int i=0; i< response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        HashMap<String, String> item = new HashMap<>();
                        item.put(ITEM_ID, obj.getString("id"));
                        item.put(ITEM_NAME, obj.getString("nama"));
                        item.put(ITEM_ALAMAT, obj.getString("alamat"));

                        item_List.add(item);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                String[] from = {ITEM_ID, ITEM_NAME, ITEM_ALAMAT};
                int[] to = {R.id.id2, R.id.nama2, R.id.alamat2};

                adapter = new SimpleAdapter(getActivity(), item_List, R.layout.list_row_selesai,from,to);
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
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(fetchOrder);
    }

    @Override
    public void onRefresh() {
        item_List.clear();
        fetchPesanan();
    }

    class ListitemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent detailpesanan = new Intent(getActivity(), detail_selesai.class);
            detailpesanan.putExtra("item", item_List.get(position));

            startActivity(detailpesanan);
        }
    }


}
