package fpoly.dungnm.book_selling_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;

import fpoly.dungnm.book_selling_app.DAO.ProductDAO;
import fpoly.dungnm.book_selling_app.R;
import fpoly.dungnm.book_selling_app.models.ModelProducts;
import fpoly.dungnm.book_selling_app.pages.productdetail.ProductDetailsActivity;

public class AdapterHomeProducts extends RecyclerView.Adapter<AdapterHomeProducts.ViewHoder> {
    Context context;
    ArrayList<ModelProducts> list;
    ProductDAO productDAO;

    public AdapterHomeProducts(Context context, ArrayList<ModelProducts> listSanPham) {
        this.context = context;
        this.list = listSanPham;
        productDAO = new ProductDAO(context);
    }


    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home_product, parent, false);
        return new ViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder holder, int position) {
        ModelProducts product = list.get(position);
        byte[] imageBytes = product.getImage();

//        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
//        holder.itemImage.setImageBitmap(bitmap);

        // Nếu imageBytes không rỗng, giải mã và hiển thị ảnh
        if (imageBytes != null && imageBytes.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            holder.imgAvaterHome.setImageBitmap(bitmap);
        } else {
            // Hiển thị ảnh mặc định nếu không có ảnh
            holder.imgAvaterHome.setImageResource(android.R.drawable.ic_dialog_alert); // Ví dụ: ảnh mặc định
        }

        holder.tvTitleHome.setText(product.getTitle());
        holder.tvPriceHome.setText(product.getPrice()+"");

        // Nếu có URL ảnh, bạn có thể sử dụng Glide hoặc Picasso để tải ảnh từ URL
        Glide.with(context).load(product.getImage()).into(holder.imgAvaterHome);

        // chọn xem chi tiết sản phẩm
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailsActivity.class);
            intent.putExtra("productId", product.getId());

            context.startActivity(intent);
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
        TextView tvTitleHome,tvPriceHome;
        ImageView imgAvaterHome;

        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            tvTitleHome = itemView.findViewById(R.id.tvTitleHome);
            tvPriceHome = itemView.findViewById(R.id.tvPriceHome);
            imgAvaterHome = itemView.findViewById(R.id.imgAvaterHome);

        }
    }
}
