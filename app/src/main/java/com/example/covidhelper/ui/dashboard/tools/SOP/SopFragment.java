package com.example.covidhelper.ui.dashboard.tools.SOP;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import com.example.covidhelper.model.SOPStatus;
import com.example.covidhelper.model.SopPhase;
import com.google.android.material.button.MaterialButton;

public class SopFragment extends Fragment
{
    int userID;

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

        SharedPreferences sp = requireContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        userID = sp.getInt("userID", -1);

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

        ViewModelProvider.Factory factory = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication());
        mViewModel = factory.create(SopViewModel.class);


        mViewModel.getUser(userID).observe(requireActivity(), user ->
        {
            // default selection of the drop down menu
            autoCompleteTextViewState.setText(user.livingState, false);

            setStatus(user.livingState);
        });

        return root;
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
                setStatus(arrayAdapter.getItem(position).toString());
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
        mViewModel.getSOP(state).observe(requireActivity(), sop ->
        {
            SopPhase phase = SopPhase.fromString(sop.phaseType);
            textViewCurrentPhase.setText(phase.getTitle());
            textViewCurrentPhase.setTextColor(ContextCompat.getColor(requireContext(), phase.getColorID()));

            statusDineIn.setImageResource(SOPStatus.fromString(sop.dineIn).getDrawableID());
            statusNonContactSport.setImageResource(SOPStatus.fromString(sop.closeSpaceSports).getDrawableID());
            statusContactSport.setImageResource(SOPStatus.fromString(sop.openSpaceSports).getDrawableID());
            statusInterDistrictTravel.setImageResource(SOPStatus.fromString(sop.withinStateTravel).getDrawableID());
            statusInterStateTravel.setImageResource(SOPStatus.fromString(sop.interStateTravel).getDrawableID());
            statusExamClass.setImageResource(SOPStatus.fromString(sop.examClass).getDrawableID());
            statusNonExamClass.setImageResource(SOPStatus.fromString(sop.nonExamClass).getDrawableID());
            statusSocialActivity.setImageResource(SOPStatus.fromString(sop.socialActivity).getDrawableID());
        });
    }

}