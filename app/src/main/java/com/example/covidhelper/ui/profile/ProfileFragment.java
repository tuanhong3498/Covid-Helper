package com.example.covidhelper.ui.profile;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.covidhelper.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class ProfileFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        ImageView qrCode= root.findViewById(R.id.risk_state_qr_image);
        CardView change_state = root.findViewById(R.id.change_state);
        CardView change_phone_number = root.findViewById(R.id.change_phone_number);
        CardView change_email = root.findViewById(R.id.change_email);
        CardView change_password = root.findViewById(R.id.change_password);

        MultiFormatWriter writer = new MultiFormatWriter();
        try {
            BitMatrix matrix = writer.encode("Your are very healthy!", BarcodeFormat.QR_CODE,350,350);
            BarcodeEncoder encoder = new BarcodeEncoder();
            Bitmap bitmap = encoder.createBitmap(matrix);
            qrCode.setImageBitmap(bitmap);
            InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(
                    Context.INPUT_METHOD_SERVICE
            );
        }catch (WriterException e){
            e.printStackTrace();
        }

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