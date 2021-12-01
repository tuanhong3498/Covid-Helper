package com.example.covidhelper.ui.dashboard.tools.SOP;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covidhelper.R;
import com.google.android.material.button.MaterialButton;

public class SopFragment extends Fragment
{
    // UI elements
    private AutoCompleteTextView autoCompleteTextViewState;
    private TextView textViewCurrentPhase;

    private ImageView statusDineIn;
    private ImageView statusNonContactSport;
    private ImageView statusContactSport;
    private ImageView statusInterDistrictTravel;
    private ImageView statusInterStateTravel;
    private ImageView statusExamClass;
    private ImageView statusNonExamClass;
    private ImageView statusSocialActivity;

    private MaterialButton buttonCompleteSOP;

    // internal variable
    private ArrayAdapter<String> arrayAdapter;
    private String state;

    private SopViewModel mViewModel;

    public static SopFragment newInstance()
    {
        return new SopFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_sop, container, false);

        autoCompleteTextViewState = root.findViewById(R.id.SOP_autoTextView_state);
        textViewCurrentPhase = root.findViewById(R.id.SOP_text_phase);

        statusDineIn = root.findViewById(R.id.SOP_dineIn_status);
        statusNonContactSport = root.findViewById(R.id.SOP_sport_nonContact_status);
        statusContactSport = root.findViewById(R.id.SOP_sport_contact_status);
        statusInterDistrictTravel = root.findViewById(R.id.SOP_travel_interDistrict_status);
        statusInterStateTravel = root.findViewById(R.id.SOP_travel_interState_status);
        statusExamClass = root.findViewById(R.id.SOP_school_examClass_status);
        statusNonExamClass = root.findViewById(R.id.SOP_school_nonExamClass_status);
        statusSocialActivity = root.findViewById(R.id.SOP_socialActivity_status);

        buttonCompleteSOP = root.findViewById(R.id.SOP_button_complete_SOP);

        // TODO: get state from DB
        state = "Selangor";
        setStatus(state);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SopViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        autoCompleteTextViewState.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                String newState = arrayAdapter.getItem(position).toString();
                System.out.println("New state selected: " + newState);
                setStatus(newState);
            }
        });

        buttonCompleteSOP.setOnClickListener(v ->
        {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://pelanpemulihannegara.gov.my/selangor/index-en.html"));
            startActivity(intent);
        });
    }

    @Override
    public void onResume()
    {
        super.onResume();
        String[] states = getResources().getStringArray(R.array.states);
        arrayAdapter = new ArrayAdapter<>(requireContext(), R.layout.dropdown_item, states);
        autoCompleteTextViewState.setAdapter(arrayAdapter);
    }

    private void setStatus(String state)
    {

    }

}