package fpoly.dungnm.book_selling_app.models;

public class ModelWallet {
    int id;
    int userId;
    double pay;

    public ModelWallet(int id, double pay) {
        this.id = id;
        this.pay = pay;

//    private int userId;
//    private double balance;
//
//    public ModelWallet() {
//    }
//    public ModelWallet(int userId, double balance) {
//        this.userId = userId;
//        this.balance = balance;

    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    public ModelWallet(double pay) {
        this.pay = pay;
    }

    public ModelWallet() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPay() {
        return pay;
    }

    public void setPay(double pay) {
        this.pay = pay;

//    public double getBalance() {
//        return balance;
//    }
//
//    public void setBalance(double balance) {
//        this.balance = balance;

    }
}
