package com.example.covidhelper.ui.dashboard.tools;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.covidhelper.database.CovidHelperDatabase;
import com.example.covidhelper.database.DAO.HotspotDAO;
import com.example.covidhelper.database.DAO.UserDAO;
import com.example.covidhelper.database.table.Hotspot;

import java.util.List;

public class HotspotRepository
{
    private final HotspotDAO hotspotDAO;

    HotspotRepository(Application application) {
        CovidHelperDatabase covidHelperDatabase = CovidHelperDatabase.getDatabase(application);
        hotspotDAO = covidHelperDatabase.getHotspotDAO();
    }

    LiveData<List<Hotspot>> getHotspotData() {
        return hotspotDAO.getHotspotData();
    }
}
