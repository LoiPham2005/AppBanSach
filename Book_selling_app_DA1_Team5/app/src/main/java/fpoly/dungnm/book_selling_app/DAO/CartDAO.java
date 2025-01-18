package fpoly.dungnm.book_selling_app.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import fpoly.dungnm.book_selling_app.database.DBHelper;
import fpoly.dungnm.book_selling_app.models.ModelCart;
import fpoly.dungnm.book_selling_app.models.ModelProducts;

public class CartDAO {
    SQLiteDatabase database;
    DBHelper dbHelper;

    public CartDAO(Context context) {
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
    }

//    public ArrayList<ModelCart> getAllCart() {
//        ArrayList<ModelCart> cartList = new ArrayList<>();
//        database = dbHelper.getReadableDatabase();
//        Cursor cursor = database.rawQuery("SELECT * FROM CART", null);
//        if (cursor.moveToFirst()) {
//            do {
//                cartList.add(new ModelCart(
//                        cursor.getInt(0),      // userID
//                        cursor.getInt(1),     // bookID
//                        cursor.getInt(2),   // quantity
//                        cursor.getDouble(3)  // amount
//                ));
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        return cartList;
//    }
public ArrayList<ModelCart> getAllCart(int userId) {
    ArrayList<ModelCart> cartList = new ArrayList<>();
    database = dbHelper.getReadableDatabase();
    Cursor cursor = database.rawQuery("SELECT * FROM CART WHERE UserID = ?", new String[]{String.valueOf(userId)});
    if (cursor.moveToFirst()) {
        do {
            cartList.add(new ModelCart(
                    cursor.getInt(0),      // userID
                    cursor.getInt(1),      // bookID
                    cursor.getInt(2),      // quantity
                    cursor.getDouble(3)    // amount
            ));
        } while (cursor.moveToNext());
    }
    cursor.close();
    return cartList;
}


    public boolean insertOrUpdateCart(ModelProducts product, int userID) {
        // Lấy sản phẩm từ giỏ hàng theo ID
        ModelCart existingCart = getCartById(userID, product.getId());
        if (existingCart != null) {
            Log.e("CartDAO", "Có cart");
            // Nếu đã tồn tại, cộng thêm số lượng
            int newQuantity = existingCart.getQuantity() + 1; // Tăng số lượng thêm 1
            double newAmount = existingCart.getAmount() + product.getPrice(); // Tăng giá trị amount
            return updateQuantity(userID, product.getId(), newQuantity, newAmount);
        } else {
            // Nếu chưa tồn tại, thêm sản phẩm mới với số lượng là 1
            ContentValues values = new ContentValues();
            values.put("UserID", userID);
            values.put("BookID", product.getId());
            values.put("quantity", 1); // Đặt số lượng là 1
            values.put("amount", product.getPrice()); // Đặt giá trị amount

            long result = database.insert("CART", null, values);
            if (result == -1) {
                Log.e("CartDAO", "Lỗi khi thêm sản phẩm vào giỏ hàng");
                return false;
            }
            return true;
        }
    }



    public boolean updateQuantity(int userID, int productId, int quantity, double amount) {
        ContentValues values = new ContentValues();
        values.put("quantity", quantity); // Update the quantity
        values.put("amount", amount); // Update the amount

        int result = database.update("CART", values, "UserID = ? AND BookID = ?", new String[]{String.valueOf(userID),String.valueOf(productId)});
        return result > 0;
    }

    // Xóa sản phẩm
    public boolean deleteCart(int userID,int bookID) {
        int result = database.delete("CART", "UserID = ? AND BookID = ?", new String[]{String.valueOf(userID), String.valueOf(bookID)});
        return result > 0;
    }

    // Lấy sản phẩm theo ID
    public ModelCart getCartById(int userID,int bookID) {
        ModelCart cart = null;
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM CART WHERE UserID = ? AND BookID = ?", new String[]{String.valueOf(userID), String.valueOf(bookID)});

        if (cursor != null && cursor.moveToFirst()) {
            cart = new ModelCart(
                    cursor.getInt(0),      // userID
                    cursor.getInt(1),      // bookID
                    cursor.getInt(2),      // quantity
                    cursor.getDouble(3)    // amount
            );
        }
        if (cursor != null) {
            cursor.close();
        }
        return cart;
    }

}
