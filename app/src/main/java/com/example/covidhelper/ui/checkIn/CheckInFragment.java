package com.example.covidhelper.ui.checkIn;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covidhelper.R;
import com.example.covidhelper.database.table.CheckInRecord;
import com.example.covidhelper.database.table.User;
import com.example.covidhelper.database.table.VaccineDose1Record;
import com.example.covidhelper.database.table.VaccineDose2Record;
import com.example.covidhelper.ui.profile.ProfileViewModel;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import kotlinx.coroutines.Dispatchers;

public class CheckInFragment extends Fragment implements RecentlyCheckInListAdapter.RecyclerviewOnClickListener
{
    Button btScan;
    TextView btViewAll;

    RecyclerView recyclerView;
    List<String> time,place,address;
    List<Integer> dateList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_check_in, container, false);

//        int currentTime = (int)(System.currentTimeMillis()-System.currentTimeMillis()/1000*1000);
//        //System.out.println("当前时间戳"+System.currentTimeMillis()+" "+currentDate+" "+(System.currentTimeMillis()-System.currentTimeMillis()/1000*1000)+" "+ currentTime);
//        SimpleDateFormat timeFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
//        String timeStr = timeFormat.format(467);
//        System.out.println("当前时间戳"+timeStr+"转化前"+timeFormat1.format(System.currentTimeMillis())+" "+currentTime + " "+System.currentTimeMillis());
//        @SuppressLint("SimpleDateFormat") SimpleDateFormat timeFormat1 = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");
//        long timeSample = 0;
//        try {
//            timeSample = timeFormat1.parse("2021.10.04 23:02:17").getTime();
//            System.out.println("时间戳数字是"+timeSample);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

        //latest check in card
        TextView name = root.findViewById(R.id.name);
        TextView checkInPlace = root.findViewById(R.id.check_in_place);
        TextView checkInAddress = root.findViewById(R.id.check_in_address);

        //update the latest check in record card
        TextView date = root.findViewById(R.id.time);
        // Get a new or existing ViewModel from the ViewModelProvider.
        ViewModelProvider.Factory factory  = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication());
        CheckInViewModel checkInViewModel = factory.create(CheckInViewModel.class);

        checkInViewModel.getLatestCheckIn(1).observe(requireActivity(), latestCheckInList -> {
            for (CheckInRecord latestCheckInRecord : latestCheckInList)
            {
                checkInPlace.setText(latestCheckInRecord.recordPlace);
                checkInAddress.setText(latestCheckInRecord.recordAddress);
            }
        });

        //the check in history
        // storeData
        checkInViewModel.getCheckInDate(1).observe(requireActivity(), checkInDateList -> {
            // Update the cached copy of the words in the adapter.
            dateList = new ArrayList<>();
            for (CheckInRecord checkInRecord : checkInDateList)
            {
                dateList.add(checkInRecord.recordDate);
            }
            //convert int to date string
            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
            @SuppressLint("SimpleDateFormat") SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");
            long dateLong = (long)(Collections.max(dateList));
            String dateStr = dateFormat.format(dateLong*100000000);
//            System.out.println("当前时间戳"+(int)(System.currentTimeMillis())+" "+System.currentTimeMillis()+" " +(long)(Collections.max(dateList)));

            date.setText(dateStr);
            checkInViewModel.getDailyCheckInDate(1, Collections.max(dateList)).observe(requireActivity(), dailyCheckInList -> {
                recyclerView = root.findViewById(R.id.all_announcements_recyclerview);
                place = new ArrayList<>();
                address = new ArrayList<>();
                time = new ArrayList<>();
                // Update the cached copy of the words in the adapter.
                for (CheckInRecord dailyCheckInRecord : dailyCheckInList)
                {
                    long timeLong = dateLong*100000000+(long)dailyCheckInRecord.recordTime;
                    String timeStr = timeFormat.format(timeLong);
                    String[] timeSplit = timeStr.split(" ");

                    System.out.println("当前时间戳"+System.currentTimeMillis()+" "+timeLong+" " +timeStr);

                    time.add(timeSplit[1]);
                    place.add(dailyCheckInRecord.recordPlace);
                    address.add(dailyCheckInRecord.recordAddress);
                }
                RecentlyCheckInListAdapter recentlyCheckInListAdapter = new RecentlyCheckInListAdapter(inflater, time, place, address,this);
                recyclerView.setAdapter(recentlyCheckInListAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
            });
        });

        //the QR code scanner
        btScan = root.findViewById(R.id.scan_btn);
        btViewAll = root.findViewById(R.id.view_all);

        btScan.setOnClickListener(v -> {
            IntentIntegrator intentIntegrator= IntentIntegrator.forSupportFragment(this);
            intentIntegrator.setPrompt("For flash use volume up key");
            intentIntegrator.setBeepEnabled(true);
            intentIntegrator.setOrientationLocked(true);
            intentIntegrator.setCaptureActivity(Capture.class);
            intentIntegrator.initiateScan();
        });


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        NavController navController = Navigation.findNavController(view);
        btViewAll.setOnClickListener(v ->
        {
            navController.navigate(R.id.checkInHistoryFragment);
        });
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void recyclerviewClick(int position) {

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(intentResult.getContents() != null){
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Result");
            String scanContent = intentResult.getContents();
            builder.setMessage(scanContent);
            builder.setPositiveButton("OK", (dialogInterface, which) -> dialogInterface.dismiss());
            builder.show();
            if (scanContent.contains(":")){
                String[] split = scanContent.split(":");
                int currentDate = (int)(System.currentTimeMillis()/1000);
                int currentTime = (int)(System.currentTimeMillis()-System.currentTimeMillis()/1000*1000);


                // Get a new or existing ViewModel from the ViewModelProvider.
                ViewModelProvider.Factory factory = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication());
                CheckInViewModel checkInViewModel = factory.create(CheckInViewModel.class);
                checkInViewModel.insertCheckInDate(new CheckInRecord(1, currentDate, split[0], split[1], currentTime));
            }
        }else {
            Toast.makeText(getActivity().getApplicationContext()
                    ,"Oops...You did not scan anything,"
                    , Toast.LENGTH_SHORT).show();
        }
    }
}
