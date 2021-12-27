package com.example.covidhelper.ui.dashboard.tools.hotspot;

import android.app.Application;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.covidhelper.database.RetrofitAPI.HotspotRetrofitAPI;
import com.example.covidhelper.database.table.Hotspot;
import com.example.covidhelper.ui.dashboard.tools.hotspot.HotspotRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HotspotViewModel extends AndroidViewModel
{
    private final HotspotRepository mRepository;

    public HotspotViewModel (Application application) {
        super(application);
        mRepository = new HotspotRepository(application);
    }

    LiveData<List<Hotspot>> getHotspotData() {
        return mRepository.getHotspotData();
    }

    // Getting the list of histories from MockAPI and populate the recyclerview
    Call<List<Hotspot>> retrieveHistoryFromApi() {

        String baseurl =  "https://61c90a2dadee460017260ec4.mockapi.io/";

        // Retrofit code to retrieve data from database
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseurl)
                // as we are sending data in json format so
                // we have to add Gson converter factory
                .addConverterFactory(GsonConverterFactory.create())
                // at last we are building our retrofit builder.
                .build();

        // below line is to create an instance for our retrofit api class.
        HotspotRetrofitAPI hotspotRetrofitAPI = retrofit.create(HotspotRetrofitAPI.class);

        return hotspotRetrofitAPI.getHotspots();
    }
}
