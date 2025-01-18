package fpoly.dungnm.book_selling_app.adapter;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpoly.dungnm.book_selling_app.DAO.AddressDAO;
import fpoly.dungnm.book_selling_app.MainActivity;
import fpoly.dungnm.book_selling_app.R;
import fpoly.dungnm.book_selling_app.models.ModelAddres;
import fpoly.dungnm.book_selling_app.pages.order_payment.OrderPaymentActivity;

public class AdapterAdderss extends RecyclerView.Adapter<AdapterAdderss.ViewHolder> {
    private final Context context;
    private final ArrayList<ModelAddres> addressList;
    private final AddressDAO addressDAO;

    public AdapterAdderss(Context context, ArrayList<ModelAddres> addressList) {
        this.context = context;
        this.addressList = addressList;
        this.addressDAO = new AddressDAO(context);
    }

    public interface OnItemClickListener {
        void onEdit(ModelAddres address);
        void onDelete(ModelAddres address);
        void onUse(ModelAddres address);
    }
    public void setOnItemClickListener(OnItemClickListener click) {
        this.click = click;
    }
    private OnItemClickListener click;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_adress, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelAddres address = addressList.get(position);

        // Hiển thị thông tin địa chỉ
        holder.tvFullName.setText("Họ và tên: " + address.getFullName());
        holder.tvPhone.setText("SDT: 0" + address.getPhone());
        holder.tvEnterAddress.setText("Địa chỉ: " + address.getAddress());

        // Xử lý khi nhấn vào item
        holder.itemView.setOnClickListener(v -> showEditDialog(holder.getAdapterPosition()));

//        holder.btnUse.setOnClickListener(v -> {
//            Intent intent = new Intent(context, OrderPaymentActivity.class);
//            intent.putExtra("fullname", address.getFullName());
//            intent.putExtra("phone", address.getPhone() + "");
//            intent.putExtra("address", address.getAddress());
////            Log.e(TAG, "onBindViewHolder: ", );
//            context.startActivity(intent);
////            setResult(RESULT_OK, resultIntent);
////            finish();
//            // Kiểm tra context là một Activity trước khi gọi finish()
//
//        });
        holder.btnUse.setOnClickListener(v -> click.onUse(address));

        // Xóa sản phẩm khỏi giỏ hàng
        holder.itemView.setOnLongClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Bạn có muốn xóa sản phẩm này không?")
                    .setPositiveButton("Có", (dialog, which) -> {
                        addressDAO.deleteAddress(address.getId());
                        addressList.remove(position);
                        notifyItemRemoved(position);
                        Toast.makeText(context, "Xóa địa chỉ thành công", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Không", (dialog, which) -> {
                        dialog.dismiss();
                    })
                    .create().show();
            return true;
        });


    }

    @Override
    public int getItemCount() {
        return addressList == null ? 0 : addressList.size();
    }

    private void showEditDialog(int position) {
        ModelAddres address = addressList.get(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.item_adress_add, null);
        builder.setView(view);

        EditText edEnterAddress = view.findViewById(R.id.edEnterAdress);
        EditText edFullName = view.findViewById(R.id.edFullName);
        EditText edPhone = view.findViewById(R.id.edPhone);

        // Điền dữ liệu cũ vào các trường
        edEnterAddress.setText(address.getAddress());
        edFullName.setText(address.getFullName());
        edPhone.setText(String.valueOf(address.getPhone()));

        builder.setPositiveButton("Sửa", (dialogInterface, which) -> {
            String newAddress = edEnterAddress.getText().toString().trim();
            String newFullName = edFullName.getText().toString().trim();
            String phoneStr = edPhone.getText().toString().trim();

            if (newAddress.isEmpty() || newFullName.isEmpty() || phoneStr.isEmpty()) {
                Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                int newPhone = Integer.parseInt(phoneStr);

                // Cập nhật đối tượng và cơ sở dữ liệu
                address.setAddress(newAddress);
                address.setFullName(newFullName);
                address.setPhone(newPhone);

                if (addressDAO.updateAddress(address)) {
                    Toast.makeText(context, "Cập nhật địa chỉ thành công", Toast.LENGTH_SHORT).show();
                    notifyItemChanged(position); // Làm mới hiển thị
                } else {
                    Toast.makeText(context, "Cập nhật địa chỉ thất bại", Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException e) {
                Toast.makeText(context, "Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Hủy", (dialogInterface, which) -> dialogInterface.dismiss());

        Dialog dialog = builder.create();
        dialog.show();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvFullName, tvPhone, tvEnterAddress;
        Button btnUse;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFullName = itemView.findViewById(R.id.tvFullName);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            tvEnterAddress = itemView.findViewById(R.id.tvEnterAdress);
            btnUse = itemView.findViewById(R.id.btnUse);
        }
    }
}
