package fpoly.dungnm.book_selling_app.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;

import fpoly.dungnm.book_selling_app.DAO.OrderDAO;
import fpoly.dungnm.book_selling_app.R;
import fpoly.dungnm.book_selling_app.models.ModelCart;
import fpoly.dungnm.book_selling_app.models.ModelOrder;
import fpoly.dungnm.book_selling_app.models.ModelOrder;
import fpoly.dungnm.book_selling_app.models.ModelProducts;

public class AdapterOrder extends RecyclerView.Adapter<AdapterOrder.ViewHoder> {
    Context context;
    ArrayList<ModelOrder> list;
    OrderDAO orderDAO;

    public AdapterOrder(Context context, ArrayList<ModelOrder> listSanPham) {
        this.context = context;
        this.list = listSanPham;
        orderDAO = new OrderDAO(context);
    }

    public interface OnItemClickListener {
        void deleteItem(String id);

        void updateItem(String id, String name, String title, String author, String price, String description, String category);
    }

    public void setOnItemClickListener(OnItemClickListener click) {
        this.click = click;
    }

    private OnItemClickListener click;


    public void setData(ArrayList<ModelOrder> ls) {
        this.list = ls;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order, parent, false);
        return new ViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder holder, int position) {
        ModelOrder order = list.get(position);

        holder.orderName.setText("Đơn hàng: "+order.getId()+"");
        holder.tvOrderNumber.setText("Mã đơn: "+order.getCreated_at());
        holder.tvDate.setText(order.getDate().toString());
        holder.tvTotalPrice.setText("Tổng tiền: "+ String.format("%,.0f", order.getTotalAmount())+"VNĐ");


        if (order.getProducts() != null && !order.getProducts().isEmpty()) {
            // Giả sử chỉ lấy sản phẩm đầu tiên
           ModelCart cart = order.getCartList().get(0);

            holder.tvQuantity.setText(String.valueOf(cart.getQuantity())); // Lấy quantity từ sản phẩm
            holder.tvPriceOder.setText(String.valueOf(cart.getAmount())); // Lấy price từ sản phẩm

//            ModelProducts product =order.getProducts().get(0);
//            holder.tvQuantity.setText(String.valueOf(product.getQuantity()));
//
//            holder.tvPriceOder.setText(String.valueOf(product.getPrice()));
        }else {
            Log.e("55555555555", "quality: không có giá trị" );
        }

    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public static class ViewHoder extends RecyclerView.ViewHolder {
        Button btnCancel,btnDetails;
        TextView orderName,tvOrderNumber,tvQuantity,tvPriceOder,tvDate,tvTotalPrice;

        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            btnCancel = itemView.findViewById(R.id.btnCancel);
            btnDetails = itemView.findViewById(R.id.btnDetails);
            orderName = itemView.findViewById(R.id.orderName);
            tvOrderNumber = itemView.findViewById(R.id.tvOrderNumber);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
//            tvPriceOder = itemView.findViewById(R.id.tvPriceOder);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvTotalPrice = itemView.findViewById(R.id.tvTotalPrice);
        }
    }
}
