package com.example.covidhelper.ui.checkIn;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covidhelper.R;
import com.example.covidhelper.database.table.CheckInRecord;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class CheckInHistoryFragment extends Fragment
{
    RecyclerView recyclerView;
    List<String> date;
    List<Long> dateUnixTimestamp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_check_in_history, container, false);
        SharedPreferences sp  = requireContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        recyclerView = root.findViewById(R.id.daily_check_in_history_list);
        date = new ArrayList<>();
        dateUnixTimestamp  = new ArrayList<>();

        // Get a new or existing ViewModel from the ViewModelProvider.
        ViewModelProvider.Factory factory  = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication());
        CheckInViewModel checkInViewModel = factory.create(CheckInViewModel.class);

        // storeData
        checkInViewModel.getCheckInDate(sp.getInt("userID", -1)).observe(requireActivity(), checkInRecordList -> {
            // Update the cached copy of the words in the adapter.
            for (CheckInRecord checkInRecord : checkInRecordList)
            {
//                date.add(checkInRecord.recordDate);
                try {
                    dateUnixTimestamp.add(timeToUnix(checkInRecord.recordDate));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            dateUnixTimestamp.sort(Collections.reverseOrder());
            for (Long dateUnixTimestampRecord : dateUnixTimestamp)
            {
                date.add(getDate(dateUnixTimestampRecord));
            }

            GroupAdapter groupAdapter = new GroupAdapter(inflater,root.getContext(),date);
            recyclerView.setAdapter(groupAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        });


        return root;
    }

    private String getDate(long unixTimestamp)
    {
        return timeToString(unixTimestamp, "dd MMM yyyy");
    }

    private String timeToString(long unixTimestamp, String dateFormatPattern)
    {
        Date date = new Date(unixTimestamp);//unixTimestamp*1000
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormatPattern, Locale.UK);
        return sdf.format(date);
    }



    private long timeToUnix(String dateString) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.UK);
        return Objects.requireNonNull(sdf.parse(dateString)).getTime();
    }
}