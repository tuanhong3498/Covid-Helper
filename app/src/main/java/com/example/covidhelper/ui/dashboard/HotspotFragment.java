package com.example.covidhelper.ui.dashboard;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.covidhelper.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;

public class HotspotFragment extends Fragment implements OnMapReadyCallback {
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;
    GoogleMap map;
    SupportMapFragment supportMapFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_hotspot, container, false);



        String[] permissions = new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                Manifest.permission.ACCESS_NETWORK_STATE,
        };
        HotspotFragment.this.requestPermissions( permissions, REQUEST_CODE);
        getLocation();







//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
//        fetLastLocation();

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
                        map.addMarker(new MarkerOptions().position(latLng).title(location));
                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
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
        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);

        //获取基于GPS的LocationProvider
        //需要加入权限  <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
        LocationProvider provider = locationManager.getProvider(LocationManager.GPS_PROVIDER);
        //权限检查,编辑器自动添加




        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
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
//              需要添加两个位置权限
//              近似精度的权限
//            <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
//              更精细精度的访问权限
//            <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
    }

    private void fetLastLocation() {
        System.out.println("运行获取地址函数");
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //when permission is not granted
            requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            System.out.println("失败获取地址");
            return;
        }
        System.out.println("成功获取地址权限");
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)||locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            System.out.println("成功获取地址GPS权限");
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    System.out.println("if获取到地址是什么"+location);
                    if(location != null) {
                        currentLocation = location;
                        System.out.println("成功获取地址");
                    }else{
                        LocationCallback locationCallback = new LocationCallback(){
                            @Override
                            public void onLocationResult(LocationResult locationResult){
                                if (locationResult == null) {
                                    return;
                                }
                                for (Location location : locationResult.getLocations()) {
                                    currentLocation = location;
                                }
                                System.out.println("else获取到地址是什么"+locationResult.getLastLocation());
                            }
                        };
                    }
                    System.out.println("获取到地址为空"+currentLocation);
//                    supportMapFragment.getMapAsync(HotspotFragment.this::onMapReady);
                }
            });
        }else {
            startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
//        fetLastLocation();
        drawCircle(map);
        if (currentLocation != null){
            LatLng curLatLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(curLatLng);
            markerOptions.title(curLatLng.latitude + ":" + curLatLng.longitude);
            map.clear();
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    curLatLng,10
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
                    latLng,10
            ));
            map.addMarker(markerOptions);
            drawCircle(map);
        });

    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
//        if (requestCode == REQUEST_CODE && (grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
//            //when permission is granted, call method
//            fetLastLocation();
//        }else {
//            //when permission is denied
//            Toast.makeText(getActivity(),"Permission Denied", Toast.LENGTH_SHORT).show();
//        }
//    }

    public void drawCircle(GoogleMap map)
    {
        map.addCircle(new CircleOptions()
                .center(new LatLng(2.8324251,101.7047863))
                .radius(10000)
                .strokeColor(Color.parseColor("#D61313"))
                .fillColor(Color.parseColor("#5EFF1C1C")));
        map.addCircle(new CircleOptions()
                .center(new LatLng(3.1144229,101.5954963))
                .radius(10000)
                .strokeColor(Color.parseColor("#D61313"))
                .fillColor(Color.parseColor("#5EFF1C1C")));

        //Australia
        map.addCircle(new CircleOptions()
                .center(new LatLng(-37.9173602,145.1280869))
                .radius(10000)
                .strokeColor(Color.parseColor("#D61313"))
                .fillColor(Color.parseColor("#5EFF1C1C")));
        map.addCircle(new CircleOptions()
                .center(new LatLng(-37.9135936,145.1429571))
                .radius(10000)
                .strokeColor(Color.parseColor("#D61313"))
                .fillColor(Color.parseColor("#5EFF1C1C")));
    }



    public void locationUpdates(Location location) {
        if (location != null) {
            //创建一个字符串构建器,用于记录位置信息
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("您的位置是: \n");
            stringBuilder.append("经度: ");
            stringBuilder.append(location.getLongitude());
            stringBuilder.append("\n 纬度:");
            stringBuilder.append(location.getLatitude());
            System.out.println("经度: "+location.getLongitude() +"纬度:" + location.getLatitude());
            currentLocation = location;

        } else {
            System.out.println("没有获取到GPS信息");
        }
    }
}