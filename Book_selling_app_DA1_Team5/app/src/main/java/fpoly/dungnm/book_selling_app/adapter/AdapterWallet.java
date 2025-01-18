package fpoly.dungnm.book_selling_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpoly.dungnm.book_selling_app.DAO.WalletDAO;
import fpoly.dungnm.book_selling_app.R;
import fpoly.dungnm.book_selling_app.models.ModelUser;
import fpoly.dungnm.book_selling_app.models.ModelWallet;

public class AdapterWallet extends RecyclerView.Adapter<AdapterWallet.ViewHoder> {
    Context context;
    ArrayList<ModelWallet> userList;

    public AdapterWallet(Context context, ArrayList<ModelWallet> userList) {
        this.context = context;
        this.userList = userList;
    }
    public void updateData(ArrayList<ModelWallet> newList) {
        this.userList = newList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_wallet, parent, false);
        return new ViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder holder, int position) {
        ModelWallet wallet = userList.get(position);
        holder.imgLogoMomo.setImageResource(R.drawable.logo_momo);
        holder.tvNameMomo.setText("Ví Momo");
        holder.tvMoneyWallet.setText(String.format("%,.0f VNĐ", wallet.getPay()));
//        holder.tvMoneyWallet.setText(wallet.getPay()+"");

        WalletDAO walletDAO = new WalletDAO(context); // Khởi tạo

        holder.itemView.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View view = LayoutInflater.from(context).inflate(R.layout.item_wallet_update, null); // Đảm bảo bạn có file `dialog_update_wallet.xml`
            builder.setView(view);

            EditText edMoneyUpdate = view.findViewById(R.id.edMoneyUpdate);

            // Điền dữ liệu cũ vào trường
            edMoneyUpdate.setText(String.format("%,.0f", wallet.getPay()));
//            edMoneyUpdate.setText(wallet.getPay()+"");

            builder.setPositiveButton("Sửa", (dialogInterface, which) -> {
                String newMoney = edMoneyUpdate.getText().toString().trim();

                if (newMoney.isEmpty()) {
                    Toast.makeText(context, "Vui lòng nhập số tiền", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    // Cập nhật thông tin ví
                    wallet.setPay(Double.parseDouble(newMoney));
                    long result = walletDAO.updatePay(wallet);

                    if (result > 0) {
                        // Cập nhật danh sách
                        userList.set(position, wallet);
                        notifyItemChanged(position);

                        Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(context, "Giá trị nhập không hợp lệ", Toast.LENGTH_SHORT).show();
                }
            });

            builder.setNegativeButton("Hủy", (dialogInterface, which) -> dialogInterface.dismiss());

            AlertDialog dialog = builder.create();
            dialog.show();
        });


        // Nhấn giữ để xóa
        holder.itemView.setOnLongClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Xác nhận xóa");
            builder.setMessage("Bạn có chắc chắn muốn xóa ví này không?");

            builder.setPositiveButton("Xóa", (dialogInterface, which) -> {
                // Thực hiện xóa trong cơ sở dữ liệu
                int result = walletDAO.deletePay(wallet.getId());
                if (result > 0) {
                    // Xóa khỏi danh sách và cập nhật RecyclerView
                    userList.remove(position);
                    notifyItemRemoved(position);
                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                }
            });

            builder.setNegativeButton("Hủy", (dialogInterface, which) -> dialogInterface.dismiss());

            AlertDialog dialog = builder.create();
            dialog.show();

            return true; // Trả về true để xác nhận sự kiện nhấn giữ đã được xử lý
        });
    }

    @Override
    public int getItemCount() {
        if (userList != null) {
            return userList.size();
        }
        return 0;
    }

    public static class ViewHoder extends RecyclerView.ViewHolder {
        ImageView imgLogoMomo;
        TextView tvMoneyWallet, tvNameMomo;

        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            imgLogoMomo = itemView.findViewById(R.id.imgLogoMomo);
            tvNameMomo = itemView.findViewById(R.id.tvNameMomo);
            tvMoneyWallet = itemView.findViewById(R.id.tvMoneyWallet);
        }
    }

}
