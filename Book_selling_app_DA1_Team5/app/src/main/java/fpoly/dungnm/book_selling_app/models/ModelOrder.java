package fpoly.dungnm.book_selling_app.models;

import java.util.Date;
import java.util.List;

public class ModelOrder {
    private int id;
    private int userID;
    private String address;
    private double totalAmount;
    private String paymentMethod;
    private String status;
    private String date;
    private String created_at;
    private List<ModelProducts> products; // Danh sách sản phẩm trong đơn hàng
    private List<ModelCart> cartList;

    public ModelOrder() {
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public List<ModelProducts> getProducts() {
        return products;
    }

    public void setProducts(List<ModelProducts> products) {
        this.products = products;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    // Getters và Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public List<ModelCart> getCartList() {
        return cartList;
    }

    public void setCartList(List<ModelCart> cartList) {
        this.cartList = cartList;
    }
}
