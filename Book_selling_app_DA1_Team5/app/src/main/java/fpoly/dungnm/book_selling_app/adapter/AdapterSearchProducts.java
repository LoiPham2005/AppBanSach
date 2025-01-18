package fpoly.dungnm.book_selling_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;

import fpoly.dungnm.book_selling_app.DAO.CategoryDAO;
import fpoly.dungnm.book_selling_app.DAO.ProductDAO;
import fpoly.dungnm.book_selling_app.R;
import fpoly.dungnm.book_selling_app.models.ModelCategory;
import fpoly.dungnm.book_selling_app.models.ModelProducts;
import fpoly.dungnm.book_selling_app.pages.productdetail.ProductDetailsActivity;

public class AdapterSearchProducts extends RecyclerView.Adapter<AdapterSearchProducts.ViewHoder> {
    Context context;
    ArrayList<ModelProducts> list;
    ProductDAO productDAO;
    CategoryDAO categoryDAO;
    ArrayList<ModelCategory> listCategory ;

    public AdapterSearchProducts(Context context, ArrayList<ModelProducts> listSanPham) {
        this.context = context;
        this.list = listSanPham;
        productDAO = new ProductDAO(context);

    }


    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder holder, int position) {
        ModelProducts product = list.get(position);
        categoryDAO = new CategoryDAO(context);
        listCategory = categoryDAO.getAllCategory();
        byte[] imageBytes = product.getImage();

//        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
//        holder.itemImage.setImageBitmap(bitmap);

        // Nếu imageBytes không rỗng, giải mã và hiển thị ảnh
        if (imageBytes != null && imageBytes.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            holder.itemImage.setImageBitmap(bitmap);
        } else {
            // Hiển thị ảnh mặc định nếu không có ảnh
            holder.itemImage.setImageResource(R.drawable.ic_launcher_background); // Ví dụ: ảnh mặc định
        }


        holder.itemTitle.setText(product.getTitle());
        holder.itemAuthor.setText(product.getAuthor());
        holder.itemPrice.setText(String.valueOf(product.getPrice()));
        holder.itemDescription.setText(product.getDescription());
//        holder.itemCategory.setText(product.getCategory());

        for (ModelCategory category : listCategory) {
            Log.e("checkCate", "idCate: " + category.getId() + " BookCate: "+product.getCategory());
            if (category.getId() == product.getCategory()) {
                holder.itemCategory.setText("Thể loại: " + category.getCategoryName());
                break;
            }
        }

        // Nếu có URL ảnh, bạn có thể sử dụng Glide hoặc Picasso để tải ảnh từ URL
        Glide.with(context).load(product.getImage()).into(holder.itemImage);


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
        TextView itemTitle, itemAuthor, itemPrice, itemDescription, itemCategory;
        ImageView itemImage;

        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            itemTitle = itemView.findViewById(R.id.itemTitle);
            itemAuthor = itemView.findViewById(R.id.itemAuthor);
            itemPrice = itemView.findViewById(R.id.itemPrice);
            itemDescription = itemView.findViewById(R.id.itemDescription);
            itemCategory = itemView.findViewById(R.id.itemCategory);
            itemImage = itemView.findViewById(R.id.itemImage);
        }
    }
}
