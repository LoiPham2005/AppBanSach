package fpoly.dungnm.book_selling_app.screens.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import fpoly.dungnm.book_selling_app.DAO.OrderDAO;
import fpoly.dungnm.book_selling_app.R;

public class AnalytistFragment extends Fragment {
    private BarChart barChart;
    ImageView imgBackAnalytist, imgFilter;
//    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    int mYear, mMonth, mDay;
    EditText edStartDate, edEndDate;
    OrderDAO orderDAO;
    TextView tvTotalRevenue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_analytist, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        barChart = view.findViewById(R.id.barChart);
        imgBackAnalytist = view.findViewById(R.id.imgBackAnalytist);
        imgFilter = view.findViewById(R.id.imgFilter);
        tvTotalRevenue = view.findViewById(R.id.totalRevenue);
        orderDAO = new OrderDAO(getActivity()); // Khởi tạo OrderDAO

        // Ánh xạ các EditText tại đây
        edStartDate = view.findViewById(R.id.edStartDate);
        edEndDate = view.findViewById(R.id.edEndDate);

        imgBackAnalytist.setOnClickListener(v -> {
            getActivity().onBackPressed();
        });

//        loadBarChartData();  // Load dữ liệu mặc định
        loadBarChartDataDemo();

        imgFilter.setOnClickListener(v -> {
            showDialogFilter();
        });
    }
    private void loadBarChartDataDemo() {
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, 10)); // 25/9
        entries.add(new BarEntry(1, 8));  // 26/9
        entries.add(new BarEntry(2, 6));  // 27/9

        BarDataSet dataSet = new BarDataSet(entries, "Doanh thu");
        BarData barData = new BarData(dataSet);
        barChart.setData(barData);
        barChart.invalidate(); // refresh
    }

    private void loadBarChartData() {
        ArrayList<BarEntry> entries = new ArrayList<>();
        // Giả sử bạn sẽ thêm dữ liệu mẫu vào đây.
        entries.add(new BarEntry(0, 10)); // 25/9
        entries.add(new BarEntry(1, 8));  // 26/9
        entries.add(new BarEntry(2, 6));  // 27/9

        // Chạy truy vấn doanh thu theo khoảng thời gian
        String startDate = edStartDate.getText().toString();
        String endDate = edEndDate.getText().toString();

        if (!startDate.isEmpty() && !endDate.isEmpty()) {
            double totalRevenue = orderDAO.getTotalRevenueByDateRange(startDate, endDate);
            entries.clear(); // Xóa dữ liệu cũ
            entries.add(new BarEntry(0, (float) totalRevenue)); // Chỉ một cột dữ liệu với tổng doanh thu

            BarDataSet dataSet = new BarDataSet(entries, "Doanh thu");
            BarData barData = new BarData(dataSet);
            barChart.setData(barData);
            barChart.invalidate(); // refresh

            tvTotalRevenue.setText(String.format("%,.0f VNĐ", totalRevenue));
        } else {
            Log.e("Error", "Start date or end date is empty");
        }
    }

    private void showDialogFilter() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_filter_analytist, null);
        dialog.setView(view);

        edStartDate = view.findViewById(R.id.edStartDate);
        edEndDate = view.findViewById(R.id.edEndDate);

        edStartDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            mYear = calendar.get(Calendar.YEAR);
            mMonth = calendar.get(Calendar.MONTH);
            mDay = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog1 = new DatePickerDialog(getContext(), 0,
                    mDateTuNgay, mYear, mMonth, mDay);

            dialog1.show();
        });

        edEndDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            mYear = calendar.get(Calendar.YEAR);
            mMonth = calendar.get(Calendar.MONTH);
            mDay = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog2 = new DatePickerDialog(getContext(), 0,
                    mDateDenNgay, mYear, mMonth, mDay);

            dialog2.show();
        });

        dialog.setPositiveButton("Lọc", (dialog1, which) -> {
            loadBarChartData(); // Tải lại biểu đồ sau khi lọc
            dialog1.dismiss();
        });
        dialog.setNegativeButton("Hủy", (dialog1, which) -> {
            dialog1.dismiss();
        });

        dialog.show();
    }

    DatePickerDialog.OnDateSetListener mDateTuNgay = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            mYear = year;
            mMonth = month;
            mDay = dayOfMonth;
            GregorianCalendar calendar = new GregorianCalendar(mYear, mMonth, mDay);
            edStartDate.setText(sdf.format(calendar.getTime()));
        }
    };

    DatePickerDialog.OnDateSetListener mDateDenNgay = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            mYear = year;
            mMonth = month;
            mDay = dayOfMonth;
            GregorianCalendar calendar = new GregorianCalendar(mYear, mMonth, mDay);
            edEndDate.setText(sdf.format(calendar.getTime()));
        }
    };
}
