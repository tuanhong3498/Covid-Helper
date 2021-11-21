package com.example.covidhelper.ui.profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.covidhelper.R;


public class EditInformationFragment extends Fragment {

    AutoCompleteTextView stateDropDownMenu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_edit_information, container, false);

        stateDropDownMenu = root.findViewById(R.id.edit_info_autoTextView_state);

        return root;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        String[] states = getResources().getStringArray(R.array.states);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(requireContext(), R.layout.dropdown_item, states);
        stateDropDownMenu.setAdapter(arrayAdapter);
    }
}