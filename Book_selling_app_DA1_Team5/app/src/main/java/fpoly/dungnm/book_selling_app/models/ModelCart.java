package fpoly.dungnm.book_selling_app.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ModelCart implements Parcelable {
    private int userID;
    private int bookID;
    private int quantity;
    private double amount;
    private boolean isSelected;

    public ModelCart() {
    }

    public ModelCart(int userID, int bookID, int quantity, double amount) {
        this.userID = userID;
        this.bookID = bookID;
        this.quantity = quantity;
        this.amount = amount;
    }

    public ModelCart(int userID, int bookID, int quantity, double amount, boolean isSelected) {
        this.userID = userID;
        this.bookID = bookID;
        this.quantity = quantity;
        this.amount = amount;
        this.isSelected = isSelected;
    }

    protected ModelCart(Parcel in) {
        userID = in.readInt();
        bookID = in.readInt();
        quantity = in.readInt();
        amount = in.readDouble();
        isSelected = in.readByte() != 0; // 1 = true, 0 = false
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public static final Creator<ModelCart> CREATOR = new Creator<ModelCart>() {
        @Override
        public ModelCart createFromParcel(Parcel in) {
            return new ModelCart(in);
        }

        @Override
        public ModelCart[] newArray(int size) {
            return new ModelCart[size];
        }
    };

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(userID);
        dest.writeInt(bookID);
        dest.writeInt(quantity);
        dest.writeDouble(amount);
        dest.writeByte((byte) (isSelected ? 1 : 0)); // 1 = true, 0 = false
    }
}
