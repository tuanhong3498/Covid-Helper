package com.example.covidhelper.ui.dashboard.tools.hotspot;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.covidhelper.R;
import com.example.covidhelper.database.table.Hotspot;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HotspotFragment extends Fragment implements OnMapReadyCallback {
    Location currentLocation;
    private static final int REQUEST_CODE = 101;
    GoogleMap map;
    SupportMapFragment supportMapFragment;

    HotspotViewModel hotspotViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_hotspot, container, false);

        String[] permissions = new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
        };
        HotspotFragment.this.requestPermissions( permissions, REQUEST_CODE);
        getLocation();

        ViewModelProvider.Factory factory  = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication());
        hotspotViewModel = factory.create(HotspotViewModel.class);

        SearchView searchView = root.findViewById(R.id.sv_location);
        supportMapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.google_map);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = searchView.getQuery().toString();
                List<Address> addressList = null;

                if (location != null || !location.equals("")) {
                    Geocoder geocoder = new Geocoder(getContext());
                    try {
                        addressList = geocoder.getFromLocationName(location, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (addressList != null) {
                        Address address = addressList.get(0);
                        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                        map.clear();
                        drawCircle(map);
                        map.addMarker(new MarkerOptions().position(latLng).title(location));
                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 50));
                    }
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        supportMapFragment.getMapAsync(this);

        return root;
    }

    private void getLocation() {
        //获取位置服务
        LocationManager locationManager = (LocationManager) requireContext().getSystemService(Context.LOCATION_SERVICE);

        //获取基于GPS的LocationProvider
        LocationProvider provider = locationManager.getProvider(LocationManager.GPS_PROVIDER);

        //权限检查,编辑器自动添加
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        assert locationManager != null;
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,   //指定GPS定位的提供者
                1000,                 //间隔时间
                1,                 //位置间隔1米
                new LocationListener() {//监听GPS定位信息是否改变
                    @Override
                    public void onLocationChanged(Location location) {
                        //GPS信息发生改变时,回调
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {
                        //GPS状态发生改变时,回调
                    }

                    @Override
                    public void onProviderEnabled(String provider) {
                        //定位提供者启动时回调
                    }

                    @Override
                    public void onProviderDisabled(String provider) {
                        //定位提供者关闭时回调
                    }
                }
        );
        //获取最新的定位信息
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        //将最新的定位信息传递给locationUpdates()方法
        locationUpdates(location);
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        drawCircle(map);
        if (currentLocation != null){
            LatLng curLatLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(curLatLng);
            markerOptions.title(curLatLng.latitude + ":" + curLatLng.longitude);
            map.clear();
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    curLatLng,50
            ));
            map.addMarker(markerOptions);
            drawCircle(map);
        }
        map.setOnMapClickListener(latLng -> {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title(latLng.latitude + ":" + latLng.longitude);
            map.clear();
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    latLng,50
            ));
            map.addMarker(markerOptions);
            drawCircle(map);
        });

    }

    public void drawCircle(GoogleMap map)
    {
//        hotspotViewModel.getHotspotData().observe(requireActivity(), hotspotDataList -> {
//            for (Hotspot hotspot : hotspotDataList) {
//                map.addCircle(new CircleOptions()
//                        .center(new LatLng(hotspot.latitude,hotspot.longitude))
//                        .radius(hotspot.caseNumber * 10)
//                        .strokeColor(Color.parseColor("#D61313"))
//                        .fillColor(Color.parseColor("#5EFF1C1C")));
//            }
//        });


//        https://mockapi.io/projects/61c90a2dadee460017260ec5
        hotspotViewModel.retrieveHistoryFromApi().enqueue(new Callback<List<Hotspot>>() {
            @Override
            public void onResponse(Call<List<Hotspot>> call, Response<List<Hotspot>> response) {
                if(response.isSuccessful()){
                    List<Hotspot> historyList = response.body();
                    for (Hotspot hotspot : historyList) {
                        map.addCircle(new CircleOptions()
                                .center(new LatLng(hotspot.latitude,hotspot.longitude))
                                .radius(hotspot.caseNumber * 5)
                                .strokeColor(Color.parseColor("#D61313"))
                                .fillColor(Color.parseColor("#5EFF1C1C")));
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(new LatLng(hotspot.latitude,hotspot.longitude));
                        markerOptions.title(hotspot.PlaceName+", Case number: "+hotspot.caseNumber);
                        map.addMarker(markerOptions);
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Hotspot>> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }



    public void locationUpdates(Location location) {
        if (location != null) {
            currentLocation = location;

        } else {
            System.out.println("No GPS information retrieved");
        }
    }
}