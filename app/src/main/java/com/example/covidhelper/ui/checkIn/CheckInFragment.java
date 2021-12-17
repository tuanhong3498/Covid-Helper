package com.example.covidhelper.ui.checkIn;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covidhelper.R;
import com.example.covidhelper.database.table.CheckInRecord;
import com.example.covidhelper.database.table.CheckInRecordDetails;
import com.example.covidhelper.database.table.User;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

        //latest check in card
        TextView name = root.findViewById(R.id.name);
        TextView checkInPlace = root.findViewById(R.id.check_in_place);
        TextView checkInAddress = root.findViewById(R.id.check_in_address);

        //vaccination status card
        CardView vaccinationStatusCard = root.findViewById(R.id.vaccination_status_card);
        TextView vaccinationStatus = root.findViewById(R.id.vaccination_status);

        //symptom status card
        CardView riskStatusCard = root.findViewById(R.id.risk_status_card);
        ImageView riskStatusImage = root.findViewById(R.id.risk_status_image);
        TextView symptomStatus = root.findViewById(R.id.symptom_status);

        //update the latest check in record card
        TextView date = root.findViewById(R.id.time);
        // Get a new or existing ViewModel from the ViewModelProvider.
        ViewModelProvider.Factory factory  = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication());
        CheckInViewModel checkInViewModel = factory.create(CheckInViewModel.class);

        checkInViewModel.getLatestCheckIn(1).observe(requireActivity(), latestCheckInList -> {
            for (CheckInRecordDetails latestCheckInRecord : latestCheckInList)
            {
                checkInPlace.setText(latestCheckInRecord.recordPlace);
                checkInAddress.setText(latestCheckInRecord.recordAddress);
            }
        });

        checkInViewModel.getUserInfo(1).observe(requireActivity(), userInfoList -> {
            // Update the cached copy of the words in the adapter.
            for (User user : userInfoList)
            {
                name.setText(user.fullName);
                switch (user.vaccinationStage) {
                    case "Dose 2":
                        vaccinationStatus.setText("Partially vaccinated");
                        vaccinationStatusCard.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                        break;
                    case "Wait 14 Days":
                        vaccinationStatus.setText("Wait 14 Days");
                        vaccinationStatusCard.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                        break;
                    case "Fully Vaccinated":
                        vaccinationStatus.setText("Fully Vaccinated");
                        vaccinationStatusCard.setCardBackgroundColor(Color.parseColor("#D5F5E3"));
                        break;
                    default:
                        vaccinationStatus.setText("Not Vaccinated");
                        vaccinationStatusCard.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                }

                switch (user.symptomStatus) {
                    case "Low Symptom":
                        riskStatusCard.setCardBackgroundColor(Color.parseColor("#C0ECFF"));
                        riskStatusImage.setColorFilter(Color.parseColor("#00B2FF"));
                        break;
                    case "Medium Symptom":
                        riskStatusCard.setCardBackgroundColor(Color.parseColor("#F4DFAF"));
                        riskStatusImage.setColorFilter(Color.parseColor("#F8C44F"));
                        break;
                    case "High Symptom":
                        riskStatusCard.setCardBackgroundColor(Color.parseColor("#ECC6C6"));
                        riskStatusImage.setColorFilter(Color.parseColor("#F37878"));
                        break;
                }
                symptomStatus.setText(user.symptomStatus);

            }
        });

        //the check in history
        // storeData
        checkInViewModel.getCheckInDate(1).observe(requireActivity(), checkInDateList -> {
            // Update the cached copy of the words in the adapter.
            dateList = new ArrayList<>();
            for (CheckInRecord checkInRecord : checkInDateList)
            {
                dateList.add(checkInRecord.recordTime);
            }

            date.setText(getDate(Collections.max(dateList)));
            checkInViewModel.getDailyCheckInDate(1, getDate(Collections.max(dateList))).observe(requireActivity(), dailyCheckInList -> {
                recyclerView = root.findViewById(R.id.all_announcements_recyclerview);
                place = new ArrayList<>();
                address = new ArrayList<>();
                time = new ArrayList<>();
                // Update the cached copy of the words in the adapter.
                for (CheckInRecordDetails dailyCheckInRecord : dailyCheckInList)
                {
                    time.add(getTime(dailyCheckInRecord.recordTime));
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
                navController.navigate(R.id.checkInHistoryFragment));
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
                int currentTime = (int)(System.currentTimeMillis());


                // Get a new or existing ViewModel from the ViewModelProvider.
                ViewModelProvider.Factory factory = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication());
                CheckInViewModel checkInViewModel = factory.create(CheckInViewModel.class);
                checkInViewModel.insertCheckInDate(new CheckInRecord(1, getDate(currentTime), currentTime,1));
            }
        }else {
            Toast.makeText(getActivity().getApplicationContext()
                    ,"Oops...You did not scan anything,"
                    , Toast.LENGTH_SHORT).show();
        }
    }

    private String getDate(long unixTimestamp)
    {
        return timeToString(unixTimestamp, "dd MMM yyyy");
    }

    private String getTime(long unixTimestamp)
    {
        return timeToString(unixTimestamp, "hh:mm aa");
    }

    private String timeToString(long unixTimestamp, String dateFormatPattern)
    {
        Date date = new Date(unixTimestamp*1000);
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormatPattern, Locale.UK);
        return sdf.format(date);
    }
}
