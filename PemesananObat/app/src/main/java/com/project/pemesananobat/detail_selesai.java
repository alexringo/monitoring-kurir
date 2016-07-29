package com.project.pemesananobat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Me on 22/05/2016.
 */
public class detail_selesai extends AppCompatActivity {
    ArrayList<HashMap<String, String>> itemList;
    ListAdapter adapter;
    ListView list;
    String id;
    ProgressDialog PD;

    public static final String TAG_NAMA_OBAT = "obat";
    public static final String TAG_JUMLAH = "jumlah";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_selesai);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        list = (ListView) findViewById(R.id.listview_detail);
        itemList = new ArrayList<HashMap<String, String>>();
        fetchPesanan();
    }

    private void fetchPesanan() {
        PD = new ProgressDialog(this);
        PD.setMessage("Loading.....");
        PD.show();
        Intent i = getIntent();
        HashMap<String, String> item = (HashMap<String, String>) i.getSerializableExtra("item");
        id = item.get(selesaiFragment.ITEM_ID);

        String url = "http://10.0.2.2/distributor-obat/android/select.php?id="+id;

        JsonArrayRequest fetchPesanan = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i=0; i< response.length(); i++){
                    try {
                        JSONObject object = response.getJSONObject(i);
                        HashMap<String, String> item = new HashMap<>();
                        item.put(TAG_NAMA_OBAT, object.getString("nama_obat"));
                        item.put(TAG_JUMLAH, object.getString("jumlah"));

                        itemList.add(item);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                String[] from = {TAG_NAMA_OBAT, TAG_JUMLAH};
                int[] to = {R.id.nama_obat, R.id.jumlah_obat};
                adapter = new SimpleAdapter(getApplicationContext(), itemList, R.layout.list_row_pesanan, from, to);
                list.setAdapter(adapter);
                PD.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                PD.dismiss();
            }
        });

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(fetchPesanan);
    }
}
