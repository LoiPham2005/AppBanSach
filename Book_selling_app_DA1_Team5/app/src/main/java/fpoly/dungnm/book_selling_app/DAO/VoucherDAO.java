package fpoly.dungnm.book_selling_app.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import fpoly.dungnm.book_selling_app.database.DBHelper;
import fpoly.dungnm.book_selling_app.models.ModelVoucher;

public class VoucherDAO {
    SQLiteDatabase database;
    DBHelper dbHelper;

    public VoucherDAO(Context context) {
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public ArrayList<ModelVoucher> getAllVoucher() {
        ArrayList<ModelVoucher> list = new ArrayList<>();
        String sql = "SELECT * FROM VOUCHER";
        Cursor cursor = database.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                ModelVoucher voucher = new ModelVoucher();
                voucher.setId(cursor.getInt(0));
                voucher.setType(cursor.getString(1));
                voucher.setDiscount(cursor.getInt(2));
                voucher.setContent(cursor.getString(3));
                voucher.setStartDate(cursor.getString(4));
                voucher.setEndDate(cursor.getString(5));
                list.add(voucher);
            } while (cursor.moveToNext());
        }
        return list;
    }

    public ModelVoucher getVoucherById(int id) {
        ModelVoucher voucher = new ModelVoucher();
        String sql = "SELECT * FROM VOUCHER WHERE ID = ?";
        Cursor cursor = database.rawQuery(sql, new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            voucher.setId(cursor.getInt(0));
            voucher.setType(cursor.getString(1));
            voucher.setDiscount(cursor.getInt(2));
            voucher.setContent(cursor.getString(3));
            voucher.setStartDate(cursor.getString(4));
            voucher.setEndDate(cursor.getString(5));
        }
        return voucher;
    }

    public boolean insertVoucher(ModelVoucher voucher) {
        ContentValues values = new ContentValues();
        values.put("type", voucher.getType());
        values.put("discount", voucher.getDiscount());
        values.put("content", voucher.getContent());
        values.put("startDate", voucher.getStartDate());
        values.put("endDate", voucher.getEndDate());
        long result = database.insert("VOUCHER", null, values);
        return result != -1;
    }

    public boolean updateVoucher(ModelVoucher voucher) {
        ContentValues values = new ContentValues();
        values.put("type", voucher.getType());
        values.put("discount", voucher.getDiscount());
        values.put("content", voucher.getContent());
        values.put("startDate", voucher.getStartDate());
        values.put("endDate", voucher.getEndDate());
        int result = database.update("VOUCHER", values, "ID = ?", new String[]{String.valueOf(voucher.getId())});
        return result > 0;
    }

    public boolean deleteVoucher(int id) {
        int result = database.delete("VOUCHER", "ID = ?", new String[]{String.valueOf(id)});
        return result > 0;
    }

}
