package fpoly.dungnm.book_selling_app.screens.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import fpoly.dungnm.book_selling_app.R;


public class SettingsFragment extends Fragment {
    ImageView imgBackProfile;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imgBackProfile = view.findViewById(R.id.imgBackProfile);

        imgBackProfile.setOnClickListener(v -> {
            requireActivity().onBackPressed(); // Quay lại màn hình trước
        });

    }
}