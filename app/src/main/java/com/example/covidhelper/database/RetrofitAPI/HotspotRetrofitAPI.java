package com.example.covidhelper.database.RetrofitAPI;

import com.example.covidhelper.database.table.Hotspot;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface HotspotRetrofitAPI
{
    @POST("hotspot")  //insert new data
        //on below line we are creating a method to post our data.
    Call<Hotspot> createHotspot(@Body Hotspot hotspot);

    @GET("hotspot/{id}")  // get one hotspot item by ID
    Call<Hotspot> getHotspot(@Path("id") int id, @Body Hotspot hotspot);

    @GET("hotspot")  // get all hotspot items //only this one is used
    Call<List<Hotspot>> getHotspots();

    @PUT("hotspot/{id}")
    Call<Hotspot> updateHotspot(@Path("id") int id);

    @DELETE("hotspot/{id}")
    Call<Hotspot> deleteHotspot(@Path("id") int id);
}
