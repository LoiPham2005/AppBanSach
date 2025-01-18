package fpoly.dungnm.book_selling_app.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ModelProducts implements Parcelable {
    int id;
    byte[] image;
    String title;
    String author;
    int price;
    String description;
    int category;
    int quantity;
    private boolean isSelected; // Thêm thuộc tính này

    public ModelProducts(int id, byte[] image, String title, String author, int price, String description, int category, int quantity, boolean isSelected) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.author = author;
        this.price = price;
        this.description = description;
        this.category = category;
        this.quantity = quantity;
        this.isSelected = isSelected;
    }

    public ModelProducts() {
    }


    public ModelProducts(int id, byte[] image, String title, String author, int price, String description, int category, int quantity) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.author = author;
        this.price = price;
        this.description = description;
        this.category = category;
        this.quantity = quantity;
    }

    public ModelProducts(int id, byte[] image, String title, String author, int price, String description, int category) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.author = author;
        this.price = price;
        this.description = description;
        this.category = category;
    }

    public ModelProducts(byte[] image, String title, String author, int price, String description, int category) {
        this.image = image;
        this.title = title;
        this.author = author;
        this.price = price;
        this.description = description;
        this.category = category;
    }

    public ModelProducts(byte[] image, String title, String author, int price, String description, int category, int quantity) {
        this.image = image;
        this.title = title;
        this.author = author;
        this.price = price;
        this.description = description;
        this.category = category;
        this.quantity = quantity;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    // Triển khai Parcelable
    protected ModelProducts(Parcel in) {
        id = in.readInt();
        image = in.createByteArray();
        title = in.readString();
        author = in.readString();
        price = in.readInt();
        description = in.readString();
        category = in.readInt();
        quantity = in.readInt();
        isSelected = in.readByte() != 0; // 1 = true, 0 = false
    }

    public static final Creator<ModelProducts> CREATOR = new Creator<ModelProducts>() {
        @Override
        public ModelProducts createFromParcel(Parcel in) {
            return new ModelProducts(in);
        }

        @Override
        public ModelProducts[] newArray(int size) {
            return new ModelProducts[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeByteArray(image);
        dest.writeString(title);
        dest.writeString(author);
        dest.writeInt(price);
        dest.writeString(description);
        dest.writeInt(category);
        dest.writeInt(quantity);
        dest.writeByte((byte) (isSelected ? 1 : 0)); // 1 = true, 0 = false
    }
}
