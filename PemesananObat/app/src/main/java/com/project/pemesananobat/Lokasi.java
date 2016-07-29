package com.project.pemesananobat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Lokasi extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        RoutingListener {

    private GoogleApiClient mGoogleApiClient;
    private Location mCurrentLocation;
    private GoogleMap mMap;
    private LocationManager locationManager;
    protected LatLng titik_awal;
    private String user,date;
    protected LatLng titik_akhir;
    private ProgressDialog progressDialog;
    private List<Polyline> polylines;
    private LocationListener location_list, cur_loc_list;
    private TextView starting, destination;
    private boolean statusGPS = false;
    private boolean statusNetwork = false;
    private ImageView send;
    private GPSDatabase gpstrack;
    private static final int[] COLORS = new int[]{R.color.track_primary, R.color.track_secondary, R.color.track_third, R.color.track_third, R.color.primary_dark_material_light};

    private String id,nama;

    private final int[] MAP_TYPES = {GoogleMap.MAP_TYPE_SATELLITE,
            GoogleMap.MAP_TYPE_NORMAL,
            GoogleMap.MAP_TYPE_HYBRID,
            GoogleMap.MAP_TYPE_TERRAIN,
            GoogleMap.MAP_TYPE_NONE};
    private int curMapTypeIndex = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lokasi);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        polylines = new ArrayList<>();
        starting = (TextView) findViewById(R.id.start);
        destination = (TextView) findViewById(R.id.destination);
        send = (ImageView) findViewById(R.id.send);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        gpstrack = new GPSDatabase(this,"track_location",null,1);
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        user = sharedPreferences.getString(Config.USER_SHARED_PREF, "Not Available");
        date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        statusGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        statusNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        checkGPS();
    }

    private void fetchLokasi() {
        Intent i = getIntent();
        id = i.getStringExtra("id");
        nama = i.getStringExtra("nama");

        String url = "http://10.0.2.2/distributor-obat/android/select.php?ids="+id;
        JsonArrayRequest fetchPesanan = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i=0; i< response.length(); i++){
                    try {
                        JSONObject object = response.getJSONObject(i);
                        titik_akhir = new LatLng(object.getDouble("geo_lat"),object.getDouble("geo_long"));
                        starting.setText(nama);
                        destination.setText(object.getString("alamat"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Lokasi.this, "Data lokasi EROR", Toast.LENGTH_SHORT).show();
            }
        });

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(fetchPesanan);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    @Override
    public void onStart() {
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
            Toast.makeText(getApplicationContext(), "getcurrentlocation!", Toast.LENGTH_SHORT).show();
            cur_loc_list = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    mCurrentLocation = new Location("");
                    mCurrentLocation.setLatitude(location.getLatitude());
                    mCurrentLocation.setLongitude(location.getLongitude());
                    initCamera(mCurrentLocation);
                    locationManager.removeUpdates(cur_loc_list);
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            };

            if(statusNetwork && statusGPS){
                Criteria criteria = new Criteria();
                locationManager.requestLocationUpdates(
                        locationManager.getBestProvider(criteria,false), 0, 0,cur_loc_list);
            } else if(statusGPS){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0 , cur_loc_list);
            } else if(statusNetwork){
                locationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER, 0, 0,cur_loc_list);
            }
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


        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                Criteria criteria = new Criteria();
                //mCurrentLocation = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
                titik_awal = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
                route();
                return true;
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Tracking Location!", Toast.LENGTH_SHORT).show();
                getlocation();
            }
        });


    }

    private void checkGPS() {

        if (statusNetwork) {
            mGoogleApiClient.connect();
            fetchLokasi();
        } else if (statusGPS) {
            mGoogleApiClient.connect();
            fetchLokasi();
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
                                    Lokasi.this.finish();
                                }
                            });
            //Showing the alert dialog
            AlertDialog showprompt = promptgps.create();
            promptgps.show();
        }
    }

    public void getlocation() {

        location_list = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double lat = location.getLatitude();
                double lng = location.getLongitude();
                CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(lat,lng));
                CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);
                mMap.moveCamera(center);
                mMap.animateCamera(zoom);
                gpstrack.insertlocation(id,String.valueOf(lat),String.valueOf(lng),user, date,"no");
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if(statusNetwork && statusGPS){
            Criteria criteria = new Criteria();
            locationManager.requestLocationUpdates(
                    locationManager.getBestProvider(criteria,false), 1000, 0,location_list);
        } else if(statusGPS){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0 , location_list);
        } else if(statusNetwork){
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, 1000, 0,location_list);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(getApplicationContext(), "onConnectionSuspended!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(getApplicationContext(), "onConnectionFailed!", Toast.LENGTH_SHORT).show();
    }


    public void route() {
        progressDialog = ProgressDialog.show(this, "Please wait.",
                "Fetching route information.", true);
        Routing routing = new Routing.Builder()
                .travelMode(AbstractRouting.TravelMode.DRIVING)
                .withListener(this)
                .alternativeRoutes(true)
                .waypoints(titik_awal, titik_akhir)
                .build();
        routing.execute();
    }


    private String getAddressFromLatLng(LatLng latLng) {
        Geocoder geocoder = new Geocoder(this);

        String address = "";
        try {
            address = geocoder
                    .getFromLocation(latLng.latitude, latLng.longitude, 1)
                    .get(0).getAddressLine(0);
        } catch (IOException e) {
        }

        return address;
    }


    @Override
    public void onRoutingFailure(RouteException e) {
        progressDialog.dismiss();
        if (e != null) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Something went wrong, Try again", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRoutingStart() {

    }

    @Override
    public void onRoutingSuccess(ArrayList<Route> route, int shortestRouteIndex) {
        progressDialog.dismiss();
        CameraUpdate center = CameraUpdateFactory.newLatLng(titik_awal);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);

        mMap.moveCamera(center);
        mMap.animateCamera(zoom);


        if (polylines.size() > 0) {
            for (Polyline poly : polylines) {
                poly.remove();
            }
        }

        polylines = new ArrayList<>();
        //add route(s) to the map.
        for (int i = 0; i < route.size(); i++) {

            //In case of more than 5 alternative routes
            int colorIndex = i % COLORS.length;

            PolylineOptions polyOptions = new PolylineOptions();
            polyOptions.color(getResources().getColor(COLORS[colorIndex]));
            polyOptions.width(10 + i * 3);
            polyOptions.addAll(route.get(i).getPoints());
            Polyline polyline = mMap.addPolyline(polyOptions);
            polylines.add(polyline);

            Toast.makeText(getApplicationContext(), "Route " + (i + 1) + ": distance - " + route.get(i).getDistanceValue() + ": duration - " + route.get(i).getDurationValue(), Toast.LENGTH_SHORT).show();
        }

        // Start marker
        MarkerOptions options = new MarkerOptions();
        options.position(titik_awal);
        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        mMap.addMarker(options);

        // End marker
        options = new MarkerOptions();
        options.position(titik_akhir);
        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        mMap.addMarker(options);

    }

    @Override
    public void onRoutingCancelled() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        try {
            locationManager.removeUpdates(location_list);
        } catch (Exception e){

        }
        this.finish();
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        checkGPS();
    }
}
