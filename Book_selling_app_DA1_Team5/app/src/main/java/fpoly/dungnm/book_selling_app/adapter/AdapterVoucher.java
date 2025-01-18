package fpoly.dungnm.book_selling_app.adapter;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;

import fpoly.dungnm.book_selling_app.DAO.VoucherDAO;
import fpoly.dungnm.book_selling_app.R;
import fpoly.dungnm.book_selling_app.models.ModelVoucher;

public class AdapterVoucher extends RecyclerView.Adapter<AdapterVoucher.VoucherHolder> {

    private Context mContext;
    private ArrayList<ModelVoucher> listVoucher;
    private VoucherDAO voucherDAO;
    public AdapterVoucher(Context mContext, ArrayList<ModelVoucher> listVoucher) {
        this.mContext = mContext;
        this.listVoucher = listVoucher;
    }

    @NonNull
    @Override
    public VoucherHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_item_voucher, parent, false);
        VoucherHolder holder = new VoucherHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull VoucherHolder holder, int position) {
        ModelVoucher modelVoucher = listVoucher.get(position);

        if (modelVoucher.getType().equals("discount")) {
            holder.imageVoucher.setImageResource(R.drawable.img_voucher);
        } else {
            holder.imageVoucher.setImageResource(R.drawable.img_voucher_shipping);
        }
        holder.tvTitleVoucher.setText(modelVoucher.getContent());
        holder.tvDiscountVoucher.setText(String.valueOf(modelVoucher.getDiscount()));

        holder.itemVoucher.setOnClickListener(v -> {
            openDialogUpdateVoucher(modelVoucher);
        });

        holder.itemVoucher.setOnLongClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("Xác nhận xóa");
            builder.setMessage("Bạn có chắc chắn muốn xóa?");
            builder.setPositiveButton("Có", (dialog, which) -> {
                voucherDAO = new VoucherDAO(mContext);
                if (voucherDAO.deleteVoucher(modelVoucher.getId())) {
                    listVoucher.clear();
                    listVoucher = voucherDAO.getAllVoucher();
                    notifyDataSetChanged();
                    Toast.makeText(mContext, "Xóa thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("Không", (dialog, which) -> {
                dialog.dismiss();
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            return true;
        });
    }

    private void openDialogUpdateVoucher(ModelVoucher modelVoucher) {
        LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
        View dialog = inflater.inflate(R.layout.layout_dialog_add_voucher, null);
        EditText edContent = dialog.findViewById(R.id.edContent);
        EditText edDiscount = dialog.findViewById(R.id.edDiscount);
        EditText edStartDate = dialog.findViewById(R.id.edStartDate);
        EditText edEndDate = dialog.findViewById(R.id.edEndDate);
        RadioButton rdDiscount = dialog.findViewById(R.id.rdDiscount);
        RadioButton rdShip = dialog.findViewById(R.id.rdShip);

        edContent.setText(modelVoucher.getContent());
        edDiscount.setText(String.valueOf(modelVoucher.getDiscount()));
        edStartDate.setText(modelVoucher.getStartDate());
        edEndDate.setText(modelVoucher.getEndDate());
        if (modelVoucher.getType().equals("discount")) {
            rdDiscount.setChecked(true);
        } else {
            rdShip.setChecked(true);
        }

        Calendar calendar = Calendar.getInstance();
        edStartDate.setOnClickListener(v -> {
            new DatePickerDialog(mContext, (view, year, month, dayOfMonth) -> {
                String selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                edStartDate.setText(selectedDate);
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        // Sự kiện chọn ngày cho End Date
        edEndDate.setOnClickListener(v -> {
            new DatePickerDialog(mContext, (view, year, month, dayOfMonth) -> {
                String selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                edEndDate.setText(selectedDate);
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setView(dialog);

        builder.setPositiveButton("Sửa", (dialog1, which) -> {
            String content = edContent.getText().toString();
            String discount = edDiscount.getText().toString();
            String startDate = edStartDate.getText().toString();
            String endDate = edEndDate.getText().toString();
            String type ="";
            if (rdDiscount.isChecked()) {
                type = "discount";
            } else {
                type = "ship";
            }
            if (content.isEmpty() || discount.isEmpty() || startDate.isEmpty() || endDate.isEmpty()) {
                Toast.makeText(mContext, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else {
                int discountInt = Integer.parseInt(discount);
                modelVoucher.setContent(content);
                modelVoucher.setDiscount(discountInt);
                modelVoucher.setStartDate(startDate);
                modelVoucher.setEndDate(endDate);
                modelVoucher.setType(type);
                voucherDAO = new VoucherDAO(mContext);
                if(voucherDAO.updateVoucher(modelVoucher)) {
                    Toast.makeText(mContext, "Sửa thành công" +type, Toast.LENGTH_SHORT).show();
                    listVoucher.clear();
                    listVoucher = voucherDAO.getAllVoucher();
                    notifyDataSetChanged();
                } else {
                    Toast.makeText(mContext, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Hủy", (dialog1, which) -> {
            dialog1.dismiss();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    @Override
    public int getItemCount() {
        if (listVoucher != null) {
            return listVoucher.size();
        }
        return 0;
    }

    public static class VoucherHolder extends RecyclerView.ViewHolder {
        RelativeLayout itemVoucher;
        ImageView imageVoucher;
        TextView tvTitleVoucher, tvDiscountVoucher;
        public VoucherHolder(@NonNull View itemView) {
            super(itemView);
            itemVoucher = itemView.findViewById(R.id.itemVoucher);
            imageVoucher = itemView.findViewById(R.id.imageVoucher);
            tvTitleVoucher = itemView.findViewById(R.id.tvTitleVoucher);
            tvDiscountVoucher = itemView.findViewById(R.id.tvDiscountVoucher);
        }
    }
}
