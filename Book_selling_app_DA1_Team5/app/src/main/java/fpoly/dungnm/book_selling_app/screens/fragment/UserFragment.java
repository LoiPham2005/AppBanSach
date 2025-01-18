package fpoly.dungnm.book_selling_app.screens.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import fpoly.dungnm.book_selling_app.DAO.UserDAO;
import fpoly.dungnm.book_selling_app.R;
import fpoly.dungnm.book_selling_app.adapter.AdapterUser;
import fpoly.dungnm.book_selling_app.models.ModelProducts;
import fpoly.dungnm.book_selling_app.models.ModelUser;


public class UserFragment extends Fragment {
    private RecyclerView rvUser;
    private AdapterUser userAdapter;
    private ArrayList<ModelUser> userList;
    private FirebaseFirestore db;
    TextInputLayout edSearchUser;
    ImageView imgBackUser;
    private ArrayList<ModelUser> filteredUserList;
    private UserDAO userDAO;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvUser = view.findViewById(R.id.rvUser);
        edSearchUser = view.findViewById(R.id.edSearchUser);
        imgBackUser = view.findViewById(R.id.imgBackUser);

        filteredUserList = new ArrayList<>();

        // Thiết lập RecyclerView
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        rvUser.setLayoutManager(manager);

        userList = new ArrayList<>();
        userAdapter = new AdapterUser(getContext(),userList);
        rvUser.setAdapter(userAdapter);

        loadUsersFromDatabase();

        imgBackUser.setOnClickListener(v -> {
            getActivity().onBackPressed();
        });

        // Tìm kiếm theo sự thay đổi trong TextInputLayout
        edSearchUser.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                filterUsers(s.toString());
            }
        });


        // Xử lý tìm kiếm khi nhấn nút
//        btnSearchUser.setOnClickListener(v -> {
//            String searchText = edSearchUser.getEditText().getText().toString();
//            filterUsers(searchText);
//        });

    }

    private void loadUsersFromDatabase() {
        userDAO = new UserDAO(getContext());
        if (userDAO.getAllUsers() != null) {
            userList.clear();
            userList.addAll(userDAO.getAllUsers());
            userAdapter.notifyDataSetChanged();
        }
    }

    // Lắng nghe sự kiện nhập liệu từ ô tìm kiếm và tìm kiếm trực tiếp , tự động

    private void filterUsers(String query) {
        filteredUserList.clear();
        if (query.isEmpty()) {
            filteredUserList.addAll(userList);
        } else {
            String lowerCaseQuery = query.toLowerCase();
            for (ModelUser user : userList) {
                if (user.getFullname().toLowerCase().contains(lowerCaseQuery) ||
                        user.getEmail().toLowerCase().contains(lowerCaseQuery)) {
                    filteredUserList.add(user);
                }
            }
        }
        userAdapter.notifyDataSetChanged();
    }

}