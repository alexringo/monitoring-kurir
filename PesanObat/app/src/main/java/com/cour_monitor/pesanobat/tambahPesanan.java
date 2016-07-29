package com.cour_monitor.pesanobat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Me on 26/05/2016.
 */
public class tambahPesanan extends AppCompatActivity {

    private Button tambah;
    private EditText jumlah_obat;
    private AutoCompleteTextView nama_obat;
    ListView list_obat;
    ListView list_db_obat;
    SimpleAdapter adapter;
    ArrayList<HashMap<String, String>> itemList;
    ArrayList<String> item_nama_obat;
    ArrayAdapter<String> adp;
    public static final String TAG_NAMA_OBAT = "nama_obat";
    public static final String TAG_NAMA_BJ = "nama_obat";
    public static final String TAG_JUMLAH = "jumlah_obat";
    private static final int PERMISSION_REQUEST_CODE = 1;

    public tambahPesanan() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tambah_pesanan);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbartambahpesanan));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tambah = (Button) findViewById(R.id.tambah_Obat);
        nama_obat = (AutoCompleteTextView) findViewById(R.id.txt_namaObat);

        item_nama_obat=new ArrayList<>();
        list_obat = (ListView) findViewById(R.id.list_Obat);

        nama_obat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 3) {
                    item_nama_obat.clear();
                    fetchlist();
                } else if(s.length()<3){
                    item_nama_obat.clear();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        jumlah_obat = (EditText) findViewById(R.id.txt_jumlahObat);

        itemList = new ArrayList<>();
        String[] from = {TAG_NAMA_OBAT, TAG_JUMLAH};
        int[] to = {R.id.new_nama_obat, R.id.new_jumlah_obat};
        adapter = new SimpleAdapter(getApplicationContext(),itemList,R.layout.list_pesananbaru, from, to);
        list_obat.setAdapter(adapter);
        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> item = new HashMap<>();
                item.put(TAG_NAMA_OBAT, nama_obat.getText().toString());
                item.put(TAG_JUMLAH, jumlah_obat.getText().toString());
                itemList.add(item);
                adapter.notifyDataSetChanged();
                nama_obat.setText("");
                jumlah_obat.setText("");
            }
        });
    }

    private void fetchlist() {
        String q = nama_obat.getText().toString();
        String url = "http://10.0.2.2/distributor-obat/android/search.php?q="+q;
        item_nama_obat.clear();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i=0; i< response.length(); i++){
                    try {
                        JSONObject object = response.getJSONObject(i);
                        HashMap<String, String> item = new HashMap<>();
                        item.put(TAG_NAMA_BJ, object.getString("nama_obat"));
                        item_nama_obat.add(item.get(TAG_NAMA_BJ));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                adp = new ArrayAdapter<String>(getApplicationContext(),R.layout.single_row,item_nama_obat);
                nama_obat.setAdapter(adp);
                adp.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
            }
        });
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonArrayRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Adding our menu to toolbar
        getMenuInflater().inflate(R.menu.menu_pesanan_baru, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_done) {
            Intent i = new Intent(tambahPesanan.this, tambahPesananCont.class);
            i.putExtra("daftar_obat", itemList);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
}
