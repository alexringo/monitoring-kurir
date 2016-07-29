package com.cour_monitor.pesanobat;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Me on 27/05/2016.
 */
public class tambahPesananCont extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,OnMapReadyCallback,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnMapClickListener,
        GoogleMap.OnMarkerClickListener{

    private GoogleApiClient mGoogleApiClient;
    private Location mCurrentLocation;
    private GoogleMap mMap;
    private LocationManager locationManager;
    private boolean statusGPS,statusNetwork;
    private final int[] MAP_TYPES = {GoogleMap.MAP_TYPE_SATELLITE,
            GoogleMap.MAP_TYPE_NORMAL,
            GoogleMap.MAP_TYPE_HYBRID,
            GoogleMap.MAP_TYPE_TERRAIN,
            GoogleMap.MAP_TYPE_NONE};
    private int curMapTypeIndex = 1;
    private final int requestCode = 1;

    EditText alamat_pemesan, instansi_pemesan;
    private String geo_lat,geo_lng;
    ArrayList<HashMap<String, String>> itemList,data_pemesan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tambah_pesanan_2);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbartambahpesanan_2));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        instansi_pemesan = (EditText)findViewById(R.id.instansi_pemesan);
        alamat_pemesan = (EditText) findViewById(R.id.alamat_pemesan);


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        itemList = new ArrayList<>();
        data_pemesan = new ArrayList<>();
        itemList = (ArrayList<HashMap<String, String>>)getIntent().getSerializableExtra("daftar_obat");
        checkGPS();
    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        mCurrentLocation = LocationServices
                .FusedLocationApi
                .getLastLocation(mGoogleApiClient);
        if(mCurrentLocation != null) {
            initCamera(mCurrentLocation);
        } else {
            mCurrentLocation = new Location("");
            mCurrentLocation.setLatitude(-7.26422404);
            mCurrentLocation.setLongitude(112.75165558);
            initCamera(mCurrentLocation);
        }
    }

    private void initCamera(final Location location) {
        final CameraPosition position = CameraPosition.builder()
                .target(new LatLng(location.getLatitude(),
                        location.getLongitude()))
                .zoom(14f)
                .bearing(0.0f)
                .tilt(0.0f)
                .build();

        mMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(position), null);

        mMap.setMapType(MAP_TYPES[curMapTypeIndex]);
        mMap.setMyLocationEnabled(true);


    }

    private void checkGPS() {
        statusGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        statusNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);


        if (statusNetwork) {
            mGoogleApiClient.connect();
        } else if (statusGPS) {
            mGoogleApiClient.connect();
        } else {
            AlertDialog.Builder promptgps = new AlertDialog.Builder(this);
            promptgps.setMessage("Activate GPS !")
                    .setCancelable(false)
                    .setTitle("Need GPS Permission")
                    .setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        DialogInterface dialog, int id) {
                                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                    startActivityForResult(intent, 0);
                                }
                            })
                    .setNegativeButton("No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, final int id) {
                                    tambahPesananCont.this.finish();
                                }
                            });
            //Showing the alert dialog
            AlertDialog showprompt = promptgps.create();
            promptgps.show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        checkGPS();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    @Override
    public void onMapClick(LatLng latLng) {
        mMap.clear();
        MarkerOptions options = new MarkerOptions().position( latLng );
        options.title(getAddressFromLatLng(latLng));

        options.icon(BitmapDescriptorFactory.defaultMarker());
        mMap.addMarker(options);
        alamat_pemesan.setText(getAddressFromLatLng(latLng));
        geo_lat = String.valueOf(latLng.latitude);
        geo_lng = String.valueOf(latLng.longitude);
        Toast.makeText(this,geo_lat+geo_lng,Toast.LENGTH_SHORT).show();
    }

    private String getAddressFromLatLng(LatLng latLng) {
        Geocoder geocoder = new Geocoder(getApplicationContext());

        String address = "";
        try {
            address = geocoder
                    .getFromLocation( latLng.latitude, latLng.longitude, 1 )
                    .get( 0 ).getAddressLine( 0 );
        } catch (IOException e ) {
        }

        return address;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.showInfoWindow();
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowClickListener( this );
        mMap.setOnMapClickListener(this);
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
            pesananbaru();
        }
        return super.onOptionsItemSelected(item);
    }

    private void pesananbaru() {

        HashMap<String, String> item = new HashMap<>();
        item.put("geo_lat", geo_lat);
        item.put("geo_lng", geo_lng);
        item.put("nama_instansi", instansi_pemesan.getText().toString());
        item.put("alamat_pemesan", alamat_pemesan.getText().toString());
        data_pemesan.add(item);
        Gson gson = new GsonBuilder().create();
        //Use GSON to serialize Array List to JSON
        String sd = gson.toJson(itemList);
        JSONArray bd = new JSONArray(data_pemesan);
        String url = "http://10.0.2.2/distributor-obat/android/tambah_pesanan.php";
        AsyncHttpClient client = new AsyncHttpClient();
        final RequestParams params = new RequestParams();
        params.put("pesanan", sd);
        params.put("data_pemesan", bd);
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {

                    Toast.makeText(getApplicationContext(), "Pesanan disimpan", Toast.LENGTH_LONG).show();
                    Intent k = new Intent(tambahPesananCont.this, Home.class);
                    k.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(k);
                    finish();

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
