package com.example.covidhelper.ui.profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.example.covidhelper.R;
import com.example.covidhelper.database.table.User;
import com.example.covidhelper.ui.Sign.LoginActivity;
import com.example.covidhelper.ui.Sign.LoginViewModel;
import com.example.covidhelper.ui.Sign.SignUpActivity;
import com.google.android.material.textfield.TextInputLayout;


public class EditInformationFragment extends Fragment {

    AutoCompleteTextView stateDropDownMenu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_edit_information, container, false);
        // Get a new or existing ViewModel from the ViewModelProvider.
        ViewModelProvider.Factory factory  = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication());
        ProfileViewModel profileViewModel = factory.create(ProfileViewModel.class);

        TextInputLayout newPhoneNumberTextInputLayout, newEmailTextInputLayout;
        newPhoneNumberTextInputLayout = root.findViewById(R.id.newPhoneNumberTextInputLayout);
        newEmailTextInputLayout = root.findViewById(R.id.newEmailTextInputLayout);

        stateDropDownMenu = root.findViewById(R.id.edit_info_autoTextView_state);
        final String[] newState = new String[1];
        newState[0] = "";
        stateDropDownMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
                newState[0] = (String)parent.getItemAtPosition(position);
            }
        });

        Button edit_information_btn = root.findViewById((R.id.edit_information_btn));
        edit_information_btn.setOnClickListener(v ->{
            String newPhoneNumber, newEmail;
            newPhoneNumber = newPhoneNumberTextInputLayout.getEditText().getText().toString();
            newEmail = newEmailTextInputLayout.getEditText().getText().toString();
            profileViewModel.updateUserInformation(newState[0], newPhoneNumber, newEmail, 1);

            NavController navController = Navigation.findNavController(requireActivity(), R.id.fragment_container);
            navController.navigate(R.id.profileFragment);
        });

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