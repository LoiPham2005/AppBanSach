package fpoly.dungnm.book_selling_app.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;

import fpoly.dungnm.book_selling_app.database.DBHelper;
import fpoly.dungnm.book_selling_app.models.ModelUser;

public class UserDAO {
    SQLiteDatabase database;
    DBHelper dbHelper;

    public UserDAO(Context context) {
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public boolean insertUser(ModelUser user) {
        ContentValues values = new ContentValues();
        values.put("email", user.getEmail());
        values.put("password", user.getPassword());
        values.put("fullname", user.getFullname());
        values.put("phone", user.getPhone());
        values.put("address", user.getAddress());
        values.put("status", user.getStatus());

        long result = database.insert("USER", null, values);
        return result != -1;
    }

    public boolean updateUser(ModelUser user) {
        ContentValues values = new ContentValues();
        values.put("email", user.getEmail());
        values.put("password", user.getPassword());
        values.put("fullname", user.getFullname());
        values.put("phone", user.getPhone());
        values.put("address", user.getAddress());
        values.put("status", user.getStatus());

        int result = database.update("USER", values, "id = ?", new String[]{String.valueOf(user.getId())});
        return result > 0;
    }

    public boolean deleteUser(int id) {
        int result = database.delete("USER", "id = ?", new String[]{String.valueOf(id)});
        return result > 0;
    }

    @SuppressLint("Range")
    public ArrayList<ModelUser> getAllUsers() {
        ArrayList<ModelUser> userList = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM USER", null);

        if (cursor.moveToFirst()) {
            do {
                ModelUser user = new ModelUser();
                user.setId(cursor.getInt(cursor.getColumnIndex("id")));
                user.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                user.setPassword(cursor.getString(cursor.getColumnIndex("password")));
                user.setFullname(cursor.getString(cursor.getColumnIndex("fullname")));
                user.setPhone(cursor.getInt(cursor.getColumnIndex("phone")));
                user.setAddress(cursor.getString(cursor.getColumnIndex("address")));
                user.setStatus(cursor.getInt(cursor.getColumnIndex("status")));
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return userList;
    }

    @SuppressLint("Range")
    public ModelUser getUserById(int id) {
        ModelUser user = null;
        Cursor cursor = database.rawQuery("SELECT * FROM USER WHERE id = ?", new String[]{String.valueOf(id)});

        if (cursor.moveToFirst()) {
            user = new ModelUser();
            user.setId(cursor.getInt(cursor.getColumnIndex("id")));
            user.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            user.setPassword(cursor.getString(cursor.getColumnIndex("password")));
            user.setFullname(cursor.getString(cursor.getColumnIndex("fullname")));
            user.setPhone(cursor.getInt(cursor.getColumnIndex("phone")));
            user.setAddress(cursor.getString(cursor.getColumnIndex("address")));
            user.setStatus(cursor.getInt(cursor.getColumnIndex("status")));
        }
        cursor.close();
        return user;
    }

    @SuppressLint("Range")
    public ModelUser checkLogin(String email, String password) {
        ModelUser user = null; Cursor cursor = database.rawQuery("SELECT * FROM USER WHERE email = ? AND password = ?", new String[]{email, password});
        if (cursor.moveToFirst()) {
            user = new ModelUser();
            user.setId(cursor.getInt(cursor.getColumnIndex("id")));
            user.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            user.setPassword(cursor.getString(cursor.getColumnIndex("password")));
            user.setFullname(cursor.getString(cursor.getColumnIndex("fullname")));
            user.setPhone(cursor.getInt(cursor.getColumnIndex("phone")));
            user.setAddress(cursor.getString(cursor.getColumnIndex("address")));
            user.setStatus(cursor.getInt(cursor.getColumnIndex("status")));
        }
        cursor.close();
        return user;
    }
}

