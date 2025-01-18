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


public class SupportFragment extends Fragment {
    ImageView imgBackSupport;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_support, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imgBackSupport = view.findViewById(R.id.imgBackSupport);
        imgBackSupport.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().popBackStack();
        });
    }
}