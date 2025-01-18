package fpoly.dungnm.book_selling_app.models;

public class ModelAddres {
    int id;
    String fullName;
    int phone;
    String address;

    public ModelAddres(String fullName, int phone, String address) {
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
    }

    public ModelAddres(int id, String fullName, int phone, String address) {
        this.id = id;
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ModelAddres() {
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
