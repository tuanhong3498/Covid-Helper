package com.example.covidhelper.ui.checkIn;

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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covidhelper.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CheckInFragment extends Fragment implements RecentlyCheckInListAdapter.RecyclerviewOnClickListener
{
    Button btScan;
    TextView btViewAll;

    RecyclerView recyclerView;
    List<String> place,address,time;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_check_in, container, false);

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

        recyclerView = root.findViewById(R.id.all_announcements_recyclerview);
        place = new ArrayList<>();
        address = new ArrayList<>();
        time = new ArrayList<>();

        storeDataInArrays();

        RecentlyCheckInListAdapter recentlyCheckInListAdapter = new RecentlyCheckInListAdapter(inflater, time, place, address,this);
        recyclerView.setAdapter(recentlyCheckInListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));

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

    void storeDataInArrays()
    {
        time = Arrays.asList(getResources().getStringArray(R.array.check_in_time));
        place = Arrays.asList(getResources().getStringArray(R.array.check_in_place));
        address = Arrays.asList(getResources().getStringArray(R.array.check_in_address));
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
            builder.setMessage(intentResult.getContents());
            builder.setPositiveButton("OK", (dialogInterface, which) -> dialogInterface.dismiss());
            builder.show();
        }else {
            Toast.makeText(getActivity().getApplicationContext()
                    ,"Oops...You did not scan anything,"
                    , Toast.LENGTH_SHORT).show();
        }
    }
}