package com.example.covidhelper.ui.dashboard.tools.hotspot;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.covidhelper.database.table.Hotspot;
import com.example.covidhelper.ui.dashboard.tools.hotspot.HotspotRepository;

import java.util.List;

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
}
