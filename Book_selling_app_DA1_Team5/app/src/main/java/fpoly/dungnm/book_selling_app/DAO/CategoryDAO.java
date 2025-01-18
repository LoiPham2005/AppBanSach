package fpoly.dungnm.book_selling_app.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import fpoly.dungnm.book_selling_app.database.DBHelper;
import fpoly.dungnm.book_selling_app.models.ModelCategory;
import fpoly.dungnm.book_selling_app.models.ModelUser;

public class CategoryDAO {
    SQLiteDatabase database;
    DBHelper dbHelper;

    public CategoryDAO(Context context) {
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public ArrayList<ModelCategory> getAllCategory() {
        ArrayList<ModelCategory> cateList = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM CATEGORY", null);

        if (cursor.moveToFirst()) {
            do {
                ModelCategory cate = new ModelCategory();
                cate.setId(cursor.getInt(0));
                cate.setCategoryName((cursor.getString(1)));
                cateList.add(cate);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return cateList;
    }
}
