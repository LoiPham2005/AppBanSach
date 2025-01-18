package fpoly.dungnm.book_selling_app.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import fpoly.dungnm.book_selling_app.database.DBHelper;
import fpoly.dungnm.book_selling_app.models.ModelProducts;

public class ProductDAO {
    SQLiteDatabase database;
    DBHelper dbHelper;

    public ProductDAO(Context context) {
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
    }


    // Lấy tất cả sản phẩm
    public ArrayList<ModelProducts> getAllProducts() {
        ArrayList<ModelProducts> productList = new ArrayList<>();
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM PRODUCTS", null);
        if (cursor.moveToFirst()) {
            do {
                // Chuyển đổi image từ byte array
                byte[] imageBytes = cursor.getBlob(1);
                productList.add(new ModelProducts(
                        cursor.getInt(0),      // id
                        imageBytes,            // image
                        cursor.getString(2),   // title
                        cursor.getString(3),   // author
                        cursor.getInt(4),      // price
                        cursor.getString(5),   // description
                        cursor.getInt(6),    // category
                        cursor.getInt(7)    // quantity
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return productList;
    }

    // Thêm sản phẩm mới
    public boolean insertProduct(ModelProducts product) {
        ContentValues values = new ContentValues();

        values.put("image", product.getImage());  // Chuyển ảnh vào BLOB
        values.put("title", product.getTitle());
        values.put("author", product.getAuthor());
        values.put("price", product.getPrice());
        values.put("description", product.getDescription());
        values.put("category", product.getCategory());
        values.put("quantity", product.getQuantity());
        long result = database.insert("PRODUCTS", null, values);

        return result > 0;
    }


    // Cập nhật thông tin sản phẩm
    public boolean updateProduct(ModelProducts product) {
        ContentValues values = new ContentValues();
        values.put("image", product.getImage());  // Chuyển ảnh vào BLOB
        values.put("title", product.getTitle());
        values.put("author", product.getAuthor());
        values.put("price", product.getPrice());
        values.put("description", product.getDescription());
        values.put("category", product.getCategory());
        values.put("quantity", product.getQuantity());
        int result = database.update("PRODUCTS", values, "id = ?", new String[]{String.valueOf(product.getId())});
        return result > 0;
    }

    // Xóa sản phẩm
    public boolean deleteProduct(int id) {
        int result = database.delete("PRODUCTS", "id = ?", new String[]{String.valueOf(id)});
        return result > 0;
    }

    // Lấy sản phẩm theo ID
    public ModelProducts getProductById(int id) {
        ModelProducts product = null;
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM PRODUCTS WHERE id = ?", new String[]{String.valueOf(id)});

        if (cursor != null && cursor.moveToFirst()) {
            byte[] imageBytes = cursor.getBlob(1);
            product = new ModelProducts(
                    cursor.getInt(0),      // id
                    imageBytes,            // image
                    cursor.getString(2),   // title
                    cursor.getString(3),   // author
                    cursor.getInt(4),      // price
                    cursor.getString(5),   // description
                    cursor.getInt(6),  // category
                    cursor.getInt(7)    // quantity
            );
        }
        if (cursor != null) {
            cursor.close();
        }
        return product;
    }

}
