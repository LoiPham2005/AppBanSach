package fpoly.dungnm.book_selling_app.models;

public class ModelUser {
    private int id;
    private String email;
    private String password;
    private String fullname;
    private int phone;
    private String address;
    private int status;

    public ModelUser() {
    }

    public ModelUser(int id, String email, String password, String fullname, int phone, String address, int status) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.fullname = fullname;
        this.phone = phone;
        this.address = address;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
