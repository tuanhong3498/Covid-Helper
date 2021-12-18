package com.example.covidhelper.ui.profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.covidhelper.R;
import com.example.covidhelper.database.table.User;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class ChangePasswordFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_change_password, container, false);
        // Get a new or existing ViewModel from the ViewModelProvider.
        ViewModelProvider.Factory factory  = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication());
        ProfileViewModel profileViewModel = factory.create(ProfileViewModel.class);

        TextInputLayout oldPasswordTextInputLayout, newPasswordTextInputLayout, retypePasswordTextInputLayout;
        oldPasswordTextInputLayout = root.findViewById(R.id.oldPasswordTextInputLayout);
        newPasswordTextInputLayout = root.findViewById(R.id.newPasswordTextInputLayout);
        retypePasswordTextInputLayout = root.findViewById(R.id.retypePasswordTextInputLayout);

        Button change_password_btn = root.findViewById((R.id.change_password_btn));
        change_password_btn.setOnClickListener(v ->{
            String oldPassword, newPassword, retypePassword;
            oldPassword = Objects.requireNonNull(oldPasswordTextInputLayout.getEditText()).getText().toString();
            newPassword = Objects.requireNonNull(newPasswordTextInputLayout.getEditText()).getText().toString();
            retypePassword = Objects.requireNonNull(retypePasswordTextInputLayout.getEditText()).getText().toString();

            oldPasswordTextInputLayout.setErrorEnabled(false);
            newPasswordTextInputLayout.setErrorEnabled(false);
            retypePasswordTextInputLayout.setErrorEnabled(false);

            boolean oldPasswordIsNull, newPasswordIsNull, retypePasswordIsNull, newPasswordAndRetypePasswordIsSame;
            oldPasswordIsNull = validateIsNull(oldPasswordTextInputLayout, oldPassword, "Old Password cannot be empty");
            newPasswordIsNull = validateIsNull(newPasswordTextInputLayout, newPassword, "New Password cannot be empty");
            retypePasswordIsNull = validateIsNull(retypePasswordTextInputLayout, retypePassword, "Retype Password cannot be empty");
            newPasswordAndRetypePasswordIsSame = validateIsTheSame(retypePasswordTextInputLayout, newPassword,  retypePassword, "Retyped password is not the same as New Password");

            if(oldPasswordIsNull && newPasswordIsNull && retypePasswordIsNull && newPasswordAndRetypePasswordIsSame) {

                SharedPreferences sp = requireContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                profileViewModel.getUserInfo(sp.getInt("userID", -1)).observe(requireActivity(), userInfoList -> {
                    // Update the cached copy of the words in the adapter.
                    for (User user : userInfoList)
                    {
                        if(oldPassword.equals(user.password))
                        {
                            profileViewModel.updateUserPassword(newPassword,sp.getInt("userID", -1));

                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("password", newPassword);
                            editor.apply();

                            NavController navController = Navigation.findNavController(requireActivity(), R.id.fragment_container);
                            navController.navigate(R.id.profileFragment);
                        }else{
                            showError(oldPasswordTextInputLayout,"Old password is incorrect");
                        }
                    }
                });
            }
        });

        return root;
    }

    private void showError(TextInputLayout textInputLayout,String error){
        textInputLayout.setError(error);
        Objects.requireNonNull(textInputLayout.getEditText()).setFocusable(true);
        textInputLayout.getEditText().setFocusableInTouchMode(true);
        textInputLayout.getEditText().requestFocus();
    }

    private boolean validateIsNull(TextInputLayout textInputLayout, String input, String error){
        if(input.equals("")){
            showError(textInputLayout,error);
            return false;
        }
        return true;
    }

    private boolean validateIsTheSame(TextInputLayout textInputLayout, String input1, String input2, String error){
        if(!input1.equals(input2)){
            showError(textInputLayout,error);
            return false;
        }
        return true;
    }
}