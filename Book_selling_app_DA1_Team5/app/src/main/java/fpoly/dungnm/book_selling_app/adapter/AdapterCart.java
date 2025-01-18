package fpoly.dungnm.book_selling_app.adapter;

import static android.content.Context.MODE_PRIVATE;
import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import fpoly.dungnm.book_selling_app.DAO.CartDAO;
import fpoly.dungnm.book_selling_app.DAO.ProductDAO;
import fpoly.dungnm.book_selling_app.R;
import fpoly.dungnm.book_selling_app.models.ModelCart;
import fpoly.dungnm.book_selling_app.models.ModelProducts;
import fpoly.dungnm.book_selling_app.pages.order_payment.OrderPaymentActivity;
import fpoly.dungnm.book_selling_app.pages.productdetail.ProductDetailsActivity;

public class AdapterCart extends RecyclerView.Adapter<AdapterCart.ViewHoder> {
    Context context;
    ArrayList<ModelCart> list;
    CartDAO cartDAO;
    ProductDAO productDAO;
    private int USER_ID;
    private OnItemClickListener click;

    public interface OnItemClickListener {
        void onItemClick();
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.click = listener;
    }
    public AdapterCart(Context context, ArrayList<ModelCart> list) {
        this.context = context;
        this.list = list;
        cartDAO = new CartDAO(context);

    }


    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new ViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder holder, int position) {
        ModelCart cart = list.get(position);

        productDAO = new ProductDAO(context);

        ModelProducts product = productDAO.getProductById(cart.getBookID());
        byte[] imageBytes = product.getImage();

        SharedPreferences sharedPreferences = context.getSharedPreferences("CHECK_LOGIN", MODE_PRIVATE);
        USER_ID = sharedPreferences.getInt("USER_ID", -1);

//        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
//        holder.itemImage.setImageBitmap(bitmap);

        // Nếu imageBytes không rỗng, giải mã và hiển thị ảnh
        if (imageBytes != null && imageBytes.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            holder.imgCartProduct.setImageBitmap(bitmap);
        } else {
            // Hiển thị ảnh mặc định nếu không có ảnh
            holder.imgCartProduct.setImageResource(R.drawable.ic_launcher_background); // Ví dụ: ảnh mặc định
        }
        holder.tvCartTitle.setText(product.getTitle());
        holder.tvCartPrice.setText(String.valueOf(product.getPrice()));
        holder.tvCartQuantity.setText(String.valueOf(cart.getQuantity()));
//        holder.tvCartQuantity.setText(product.getCategory());

        // Nếu có URL ảnh, bạn có thể sử dụng Glide hoặc Picasso để tải ảnh từ URL
        Glide.with(context).load(product.getImage()).into(holder.imgCartProduct);

        // Ensure the checkbox reflects the current state
        holder.cbCheck.setChecked(cart.isSelected());

        // Add a listener to the checkbox
        holder.cbCheck.setOnCheckedChangeListener((buttonView, isChecked) -> {
            cart.setSelected(isChecked); // Update the selected state
            click.onItemClick(); // Notify the listener to update the total price
        });

        // chọn xem chi tiết sản phẩm
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailsActivity.class);
            intent.putExtra("cartID", product.getId());

            context.startActivity(intent);
        });

        // Xóa sản phẩm khỏi giỏ hàng
        holder.imgDeleteProduct.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                .setTitle("Bạn có muốn xóa sản phẩm này không?")
                .setPositiveButton("Có", (dialog, which) -> {
                    cartDAO.deleteCart(USER_ID, product.getId());
                    list.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, list.size());
                    click.onItemClick();
                })
                .setNegativeButton("Không", (dialog, which) -> {
                    dialog.dismiss();
                })
                .create().show();
        });

//         Thêm sự kiện cho checkbox
        holder.cbCheck.setOnCheckedChangeListener((buttonView, isChecked) -> {
            cart.setSelected(isChecked); // Cập nhật trạng thái đã chọn
        });
        
        // Ensure the checkbox reflects the current state
        holder.cbCheck.setChecked(cart.isSelected());

        // Xử lý sự kiện khi nhấn nút +
        holder.btnIncrease.setOnClickListener(v -> {
            int quantity = cart.getQuantity();
            cart.setQuantity(quantity + 1); // Tăng số lượng
            cart.setAmount(product.getPrice() * (quantity + 1));
            holder.tvCartQuantity.setText(String.valueOf(cart.getQuantity())); // Cập nhật giao diện

            // Cập nhật vào cơ sở dữ liệu nếu cần
            cartDAO.updateQuantity(USER_ID, product.getId(), cart.getQuantity(), cart.getAmount()); // Giả sử bạn có phương thức này trong CartDAO
            click.onItemClick();
        });

        // Xử lý sự kiện khi nhấn nút -
        holder.btnDecrease.setOnClickListener(v -> {
            int quantity = cart.getQuantity();
            if (quantity > 0) {
                cart.setQuantity(quantity - 1); // Giảm số lượng
                cart.setAmount(product.getPrice() * (quantity - 1));
                holder.tvCartQuantity.setText(String.valueOf(cart.getQuantity())); // Cập nhật giao diện

                // Cập nhật vào cơ sở dữ liệu nếu cần
                cartDAO.updateQuantity(USER_ID, product.getId(), cart.getQuantity(), cart.getAmount()); // Giả sử bạn có phương thức này trong CartDAO
                click.onItemClick();
            }
        });


    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public static class ViewHoder extends RecyclerView.ViewHolder {
        ImageView imgCartProduct, imgDeleteProduct;
        TextView tvCartTitle, tvCartPrice, tvCartQuantity;
        CheckBox cbCheck;
        ImageButton btnDecrease, btnIncrease;

        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            imgCartProduct = itemView.findViewById(R.id.imgCartProduct);
            tvCartTitle = itemView.findViewById(R.id.tvCartTitle);
            tvCartPrice = itemView.findViewById(R.id.tvCartPrice);
            tvCartQuantity = itemView.findViewById(R.id.tvCartQuantity);
            cbCheck = itemView.findViewById(R.id.cbCheck);
            btnDecrease = itemView.findViewById(R.id.btnDecrease);
            btnIncrease = itemView.findViewById(R.id.btnIncrease);
            imgDeleteProduct = itemView.findViewById(R.id.imgDeleteProduct);
        }
    }
}
