package com.example.covidhelper.ui.dashboard.tools.SOP;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.covidhelper.database.table.SOPContent;

public class SopViewModel extends AndroidViewModel
{
    private SopRepository sopRepository;

    public SopViewModel(Application application)
    {
        super(application);
        sopRepository = new SopRepository(application);
    }

    LiveData<SOPContent> getSOP(String state)
    {
        return sopRepository.getSOP(state);
    }
}