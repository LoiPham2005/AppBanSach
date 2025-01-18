package fpoly.dungnm.book_selling_app.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import android.database.sqlite.SQLiteDatabase;


import fpoly.dungnm.book_selling_app.database.DBHelper;
import fpoly.dungnm.book_selling_app.models.ModelWallet;

public class WalletDAO {

    private static final String TABLE_NAME = "PAY";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_PAY = "pay";

    private SQLiteDatabase db;
    private DBHelper dbHelper;

    public WalletDAO(Context context) {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    // Thêm một bản ghi vào bảng PAY
    public long insertPay(ModelWallet modelPay) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, modelPay.getId());
        values.put(COLUMN_PAY, modelPay.getPay());
        return db.insert(TABLE_NAME, null, values);
    }

    // Cập nhật một bản ghi trong bảng PAY
    public int updatePay(ModelWallet modelPay) {
        Log.e("check id DAO", "id: "+modelPay.getId() );
        ContentValues values = new ContentValues();
        values.put(COLUMN_PAY, modelPay.getPay());
        Log.e("8888888", "updatePay: "+modelPay.getPay() );
        long check =  db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{String.valueOf(modelPay.getId())});
        if(check <=0 ){
            Log.e("8888888", "updatePay: "+modelPay.getPay() );
            return -1;
        }else{
            return 1;
        }
    }

    // Xóa một bản ghi từ bảng PAY
    public int deletePay(int id) {
        return db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }

    // Lấy danh sách tất cả các bản ghi từ bảng PAY
    public List<ModelWallet> getAllPay() {
        List<ModelWallet> payList = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
            if (cursor.moveToFirst()) {
                do {
                    ModelWallet modelPay = new ModelWallet();
                    modelPay.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                    modelPay.setPay(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PAY)));
                    payList.add(modelPay);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return payList;
    }

    // Lấy số tiền của ID đầu tiên từ bảng PAY
    public double getFirstPay() {
        double firstPay = 0;
        String query = "SELECT " + COLUMN_PAY + " FROM " + TABLE_NAME + " LIMIT 1"; // Lấy bản ghi đầu tiên
        Cursor cursor = null;

        try {
            cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                firstPay = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PAY));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return firstPay;
    }


//    SQLiteDatabase database;
//    DBHelper dbHelper;
//
//    public WalletDAO(Context context) {
//        dbHelper = new DBHelper(context);
//        database = dbHelper.getWritableDatabase();
//    }
//
//    public boolean insertWallet(ModelWallet wallet) {
//        ContentValues values = new ContentValues();
//        values.put("UserID", wallet.getUserId());
//        values.put("balance", wallet.getBalance());
//        long result = database.insert("WALLET", null, values);
//        return result != -1;
//    }
//    public boolean updateWallet(ModelWallet wallet) {
//        ContentValues values = new ContentValues();
//        values.put("balance", wallet.getBalance());
//        long result = database.update("WALLET", values, "UserID = ?", new String[]{String.valueOf(wallet.getUserId())});
//        return result > 0;
//    }
//
//    public ModelWallet getWalletByUserId(int userId) {
//        ModelWallet wallet = null;
//        String query = "SELECT * FROM WALLET WHERE UserID = ?";
//        String[] args = {String.valueOf(userId)};
//        Cursor cursor = database.rawQuery(query, args);
//        if (cursor.moveToFirst()) {
//            wallet = new ModelWallet();
//            wallet.setUserId(cursor.getInt(0));
//            wallet.setBalance(cursor.getDouble(1));
//        }
//        return wallet;
//    }

}
