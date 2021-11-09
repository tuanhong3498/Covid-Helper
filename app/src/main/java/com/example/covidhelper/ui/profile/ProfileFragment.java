package com.example.covidhelper.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.covidhelper.R;

public class ProfileFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        CardView change_state = root.findViewById(R.id.change_state);
        CardView change_phone_number = root.findViewById(R.id.change_phone_number);
        CardView change_email = root.findViewById(R.id.change_email);
        CardView change_password = root.findViewById(R.id.change_password);

        change_state.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(requireActivity(), R.id.fragment_container);
            navController.navigate(R.id.changeStateFragment);
        });
        change_phone_number.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(requireActivity(), R.id.fragment_container);
            navController.navigate(R.id.changePhoneNumberFragment);
        });
        change_email.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(requireActivity(), R.id.fragment_container);
            navController.navigate(R.id.changeEmailFragment);
        });
        change_password.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(requireActivity(), R.id.fragment_container);
            navController.navigate(R.id.changePasswordFragment);
        });
        return root;
    }
}