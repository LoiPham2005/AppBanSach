package fpoly.dungnm.book_selling_app.pages.search;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import fpoly.dungnm.book_selling_app.DAO.ProductDAO;
import fpoly.dungnm.book_selling_app.R;
import fpoly.dungnm.book_selling_app.adapter.AdapterProducts;
import fpoly.dungnm.book_selling_app.adapter.AdapterSearchProducts;
import fpoly.dungnm.book_selling_app.models.ModelProducts;

public class SearchActivity extends AppCompatActivity implements Filterable {
    RecyclerView rcvSearch;
    AdapterSearchProducts adapter;
    ArrayList<ModelProducts> listProducts = new ArrayList<>();
    ProductDAO productDAO;
    private List<ModelProducts> filteredProducts; // Danh sách sản phẩm đã lọc
    TextInputLayout edSearch ;
    ImageView imgBack;
    Button btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        rcvSearch = findViewById(R.id.rcvSearch);
        edSearch = findViewById(R.id.edTimKiem);
        imgBack = findViewById(R.id.imgBack);
        btnSearch = findViewById(R.id.btnSearch);

        productDAO = new ProductDAO(this);
        listProducts = productDAO.getAllProducts();
        filteredProducts = new ArrayList<>(listProducts); // Khởi tạo danh sách đã lọc

        // Thiết lập RecyclerView
        // Thiết lập RecyclerView với GridLayoutManager
        GridLayoutManager manager = new GridLayoutManager(this, 2); // Số 2 là số cột
        rcvSearch.setLayoutManager(manager);

        // adapter = new AdapterProducts(getContext(), listProducts);
        adapter = new AdapterSearchProducts(this, (ArrayList<ModelProducts>) filteredProducts);

        rcvSearch.setAdapter(adapter);

        // nút back
        imgBack.setOnClickListener(v -> {
            onBackPressed();
        });

        // Lắng nghe sự kiện nhập liệu từ ô tìm kiếm và tìm kiếm trực tiếp , tự động
         edSearch.getEditText().addTextChangedListener(new TextWatcher() {
             @Override
             public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

             @Override
             public void onTextChanged(CharSequence s, int start, int before, int count) {}

             @Override
             public void afterTextChanged(Editable s) {
                 getFilter().filter(s.toString()); // Kích hoạt bộ lọc
             }
         });

// nhấn button để tìm kiếm
        btnSearch.setOnClickListener(v -> {
        String searchText = edSearch.getEditText().getText().toString();
        getFilter().filter(searchText); // Kích hoạt bộ lọc khi nhấn nút
    });

    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                List<ModelProducts> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(listProducts);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (ModelProducts product : listProducts) {
                        if (product.getTitle().toLowerCase().contains(filterPattern)) {
                            filteredList.add(product);
                        }
                    }
                }

                results.values = filteredList;
                results.count = filteredList.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredProducts.clear();
                filteredProducts.addAll((List<ModelProducts>) results.values);
                adapter.notifyDataSetChanged();
            }
        };
    }
}