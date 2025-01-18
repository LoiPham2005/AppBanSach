package fpoly.dungnm.book_selling_app.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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

public class AdapterProducts extends RecyclerView.Adapter<AdapterProducts.ViewHoder> {
    Context context;
    ArrayList<ModelProducts> list;
    ProductDAO productDAO;
    CategoryDAO categoryDAO;
    ArrayList<ModelCategory> listCategory;
    ModelProducts product;

    public AdapterProducts(Context context, ArrayList<ModelProducts> listSanPham) {
        this.context = context;
        this.list = listSanPham;
        productDAO = new ProductDAO(context);
    }

    public interface OnItemClickListener {
        void deleteItem(int id);

        void updateItem(String id, String name, String title, String author, String price, String description, int category, int quantity);
    }

    public void setOnItemClickListener(OnItemClickListener click) {
        this.click = click;
    }

    private OnItemClickListener click;


    public void setData(ArrayList<ModelProducts> ls) {
        this.list = ls;
        notifyDataSetChanged();
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
            holder.itemImage.setImageResource(android.R.drawable.ic_dialog_alert); // Ví dụ: ảnh mặc định
        }


        holder.itemTitle.setText(product.getTitle());
        holder.itemAuthor.setText(product.getAuthor());
        holder.itemPrice.setText(String.valueOf(product.getPrice()));
        holder.itemDescription.setText(product.getDescription());
        for (ModelCategory category : listCategory) {
            if (category.getId() == product.getCategory()) {
                holder.itemCategory.setText(category.getCategoryName());
                break;
            }
        }

        // Nếu có URL ảnh, bạn có thể sử dụng Glide hoặc Picasso để tải ảnh từ URL
        Glide.with(context).load(product.getImage()).into(holder.itemImage);

        // xoá sản phẩm
        holder.itemView.setOnLongClickListener(v -> {
            int id = list.get(holder.getAdapterPosition()).getId();
            click.deleteItem(id);
            return true;
        });

        // sửa sản phẩm
        holder.itemView.setOnClickListener(v -> {
            String id = String.valueOf(list.get(holder.getAdapterPosition()).getId());
            byte[] image = list.get(holder.getAdapterPosition()).getImage();
            String title = list.get(holder.getAdapterPosition()).getTitle();
            String author = list.get(holder.getAdapterPosition()).getAuthor();
            String price = String.valueOf(list.get(holder.getAdapterPosition()).getPrice());
            String description = list.get(holder.getAdapterPosition()).getDescription();
            int category = list.get(holder.getAdapterPosition()).getCategory();
            int quantity = list.get(holder.getAdapterPosition()).getQuantity();

            click.updateItem(id, Arrays.toString(image), title, author, price, description, category, quantity);
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
