package fpoly.dungnm.book_selling_app.adapter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class AdapterOrderPayment extends RecyclerView.Adapter<AdapterOrderPayment.ViewHolder> {
    private Context context;
    private ArrayList<ModelCart> cartList;
    private CartDAO cartDAO;
    private ProductDAO productDAO;

    public AdapterOrderPayment(Context context, ArrayList<ModelCart> cartList) {
        this.context = context;
        this.cartList = cartList;
        this.cartDAO = new CartDAO(context);
        this.productDAO = new ProductDAO(context);  // Assuming ProductDAO fetches product details
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_payment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelCart cartItem = cartList.get(position);

        // Fetch the product details from the database based on bookID
        ModelProducts product = productDAO.getProductById(cartItem.getBookID()); // Assuming getProductById() fetches product

        SharedPreferences sharedPreferences = context.getSharedPreferences("CHECK_LOGIN", MODE_PRIVATE);
        int USER_ID = sharedPreferences.getInt("USER_ID", -1);

        if (product != null) {
            byte[] imageBytes = product.getImage();

            if (imageBytes != null && imageBytes.length > 0) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                holder.imgAvatarOrderProduct.setImageBitmap(bitmap);
            } else {
                // Default image if no image is found
                holder.imgAvatarOrderProduct.setImageResource(R.drawable.ic_launcher_background);
            }

            holder.tvTitleOrderProduct.setText("Sách: " + product.getTitle());
            holder.tvPriceOrderProduct.setText("Giá: " + String.valueOf(product.getPrice()));
            holder.tvQualityOrderProduct.setText("Số lượng: " + String.valueOf(cartItem.getQuantity()));

            // Glide for loading image from URL or fallback
            Glide.with(context).load(product.getImage()).into(holder.imgAvatarOrderProduct);
        }

        // On clicking the product, navigate to the product detail page
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailsActivity.class);
            intent.putExtra("cartID", cartItem.getBookID()); // Assuming cartItem contains bookID
            context.startActivity(intent);
        });

        // Khi nhấn vào nút xóa sản phẩm
        holder.imgDeleteOrderProduct.setOnClickListener(v -> {
            // Kiểm tra nếu giỏ hàng còn sản phẩm nào sau khi xóa
            new AlertDialog.Builder(context)
                    .setTitle("Bạn có muốn bỏ sản phẩm này không?")
                    .setPositiveButton("Có", (dialog, which) -> {
                        // Xóa sản phẩm khỏi giỏ hàng
                        cartList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, cartList.size());

                        // Kiểm tra nếu giỏ hàng còn sản phẩm nào sau khi xóa
                        if (cartList.isEmpty()) {
                            // Nếu không còn sản phẩm nào trong giỏ, yêu cầu quay lại màn h ình trước
                            new AlertDialog.Builder(context)
                                    .setTitle("Giỏ hàng của bạn đã trống!")
                                    .setMessage("Quay lại màn hình giỏ hàng")
                                    .setPositiveButton("Có", (dialog1, which1) -> {
                                        // Quay lại màn hình trước
                                        ((OrderPaymentActivity) context).onBackPressed();
                                    })
//                                    .setNegativeButton("Không", (dialog1, which1) -> {
//                                        dialog1.dismiss();
//                                    })
                                    .setCancelable(false) // Ngừng tắt dialog khi click ngoài
                                     // Ngừng tắt dialog khi click ngoài
                                    .create().show();
                        } else {
                            // Cập nhật tổng tiền sau khi xóa sản phẩm
                            if (context instanceof OrderPaymentActivity) {
                                ((OrderPaymentActivity) context).updateTotalMoney();
                            }
                        }
                    })
                    .setNegativeButton("Không", (dialog, which) -> {
                        dialog.dismiss();
                    })
                    .create().show();
        });
    }

    @Override
    public int getItemCount() {
        return cartList != null ? cartList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAvatarOrderProduct, imgDeleteOrderProduct;
        TextView tvTitleOrderProduct, tvPriceOrderProduct, tvQualityOrderProduct;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatarOrderProduct = itemView.findViewById(R.id.imgAvaterOrderProduct);
            imgDeleteOrderProduct = itemView.findViewById(R.id.imgDeleteOrderProduct);
            tvTitleOrderProduct = itemView.findViewById(R.id.tvTitleOrderProduct);
            tvPriceOrderProduct = itemView.findViewById(R.id.tvPriceOrderProduct);
            tvQualityOrderProduct = itemView.findViewById(R.id.tvQualityOrderProduct);
        }
    }
}
