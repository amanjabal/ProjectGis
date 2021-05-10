package com.example.projectgis;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback
        , GoogleMap.OnMyLocationButtonClickListener
        , GoogleMap.OnMyLocationClickListener {

    private GoogleMap mMap;
    double LatStart = -0.9019480999999855;
    double LngStart = 119.91211878220886;
    double LatEnd = -0.8994417770929647;
    double LngEnd = 119.89867381758697;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng saya     = new LatLng(-2.542968352413601, 121.96240946468123);
        LatLng masjid1 = new LatLng(-2.53997250568257, 121.96080734217311);
        LatLng masjid2 = new LatLng(-2.5445656,121.9700381);
        LatLng masjid3 = new LatLng(-2.5397767,121.968562);

        //add marker to map
        mMap.addMarker(new MarkerOptions()
                .position(saya)
                .title("Rumah Saya")
                .snippet("Ini Rumah Saya")
                .icon(bitmapDescriptorFromImage(getApplicationContext(), R.drawable.user))
        );
        mMap.addMarker(new MarkerOptions()
                .position(masjid1)
                .title("Masjid Nurul Iman")
                .snippet("Kelurahan Bungi")
                .icon(bitmapDescriptorFromImage(getApplicationContext(), R.mipmap.ic_launcher_round))
        );
        mMap.addMarker(new MarkerOptions()
                .position(masjid2)
                .title("Masjid Islamic Center")
                .snippet("Kelurahan Marsaoleh")
                .icon(bitmapDescriptorFromImage(getApplicationContext(), R.mipmap.masjid3_round))
        );
        mMap.addMarker(new MarkerOptions()
                .position(masjid3)
                .title("Masjid Nurul Huda")
                .snippet("Kelurahan Matano")
                .icon(bitmapDescriptorFromImage(getApplicationContext(), R.mipmap.masjid2))
        );

        //PolyLine
        LatLng DirgaHouse = new LatLng(LatStart, LngStart);
        LatLng BnsVeteran = new LatLng(LatEnd, LngEnd);

        mMap.addPolyline(new PolylineOptions().add(
                saya,
                new LatLng(-2.5430228458140256, 121.96249610957354),
                new LatLng(-2.542730930185239, 121.96238512517114),
                new LatLng(-2.5427119552591684, 121.96255878118724),
                new LatLng(-2.5425791307688255, 121.96296578747497),
                new LatLng(-2.53987113468464, 121.96245017209647),
                new LatLng(-2.539748887464133, 121.96113328187528),

                masjid1).width(10).color(Color.BLUE)
        );
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(saya, 13.5f));

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager
                    .PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION
                        , Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            } else {
                mMap.setMyLocationEnabled(true);
            }
        } else {
            mMap.setMyLocationEnabled(true);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager
                        .PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(this, Manifest.permission
                        .ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
                }
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "My Location Button Clicked", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Lokasiku saat ini : " + location, Toast.LENGTH_LONG).show();
    }
    private BitmapDescriptor bitmapDescriptorFromImage(Context context, int ImageResId)
    {
        Drawable imageDrawable = ContextCompat.getDrawable(context, ImageResId);
        imageDrawable.setBounds(0,0,imageDrawable.getIntrinsicWidth(),imageDrawable
                .getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(imageDrawable.getIntrinsicWidth(),imageDrawable
                .getIntrinsicHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        imageDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    }




