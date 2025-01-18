package fpoly.dungnm.book_selling_app.pages.order_payment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import fpoly.dungnm.book_selling_app.DAO.OrderDAO;
import fpoly.dungnm.book_selling_app.DAO.WalletDAO;
import fpoly.dungnm.book_selling_app.R;
import fpoly.dungnm.book_selling_app.adapter.AdapterOrderPayment;
import fpoly.dungnm.book_selling_app.models.ModelCart;
import fpoly.dungnm.book_selling_app.models.ModelOrder;
import fpoly.dungnm.book_selling_app.models.ModelProducts;
import fpoly.dungnm.book_selling_app.models.ModelWallet;
import fpoly.dungnm.book_selling_app.pages.crud_frofile.crud_adress.AdressActivity;
import fpoly.dungnm.book_selling_app.pages.crud_frofile.voucher.VoucherActivity;
import fpoly.dungnm.book_selling_app.pages.invoice.InvoiceActivity;

public class OrderPaymentActivity extends AppCompatActivity {
    private static final int REQUEST_SELECT_ADDRESS = 100;
    private static final int REQUEST_SELECT_VOUCHER = 101;

    RecyclerView recyclerView;
    AdapterOrderPayment adapter;
    ArrayList<ModelCart> cartList = new ArrayList<>();
    ArrayList<ModelProducts> productsList = new ArrayList<>();

    ImageView imgBackCart;
    RelativeLayout rlAdress;
    TextView tvAddressText, tvMoneyShip, tvMoneyProduct, tvVoucherPrice, tvTotalMoney, tvVoucherAdd, tvVoucherName;
    LinearLayout llVoucher;

    private double totalOrderAmount = 0;
    private RadioGroup radioGroupPayment;
    private RadioButton radioCashOnDelivery, radioEWallet;
    private TextView tvWalletBalance;
    private Button btnPayOrderProduct;
    WalletDAO walletDAO;
    int USER_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_payment);

        // Ánh xạ các View
        initViews();

        walletDAO = new WalletDAO(this);

        // Cài đặt RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<ModelCart> selectedCarts = getIntent().getParcelableArrayListExtra("selectedCarts");

        if (selectedCarts != null && !selectedCarts.isEmpty()) {
            adapter = new AdapterOrderPayment(this, selectedCarts);
            recyclerView.setAdapter(adapter);

            cartList = selectedCarts;
            updateTotalMoney(); // Tính và hiển thị tổng tiền
        } else {
            Toast.makeText(this, "Không có sản phẩm nào được chọn!", Toast.LENGTH_SHORT).show();
        }

        // Xử lý các sự kiện
        imgBackCart.setOnClickListener(v -> onBackPressed());

        rlAdress.setOnClickListener(v -> {
            clickChoiceAddress();
        });

        llVoucher.setOnClickListener(v -> {
            handleVoucherSelection();
        });

        handlePaymentSelection();
        handleOrderPayment();

        SharedPreferences sharedPreferences = getSharedPreferences("CHECK_LOGIN", MODE_PRIVATE);
        USER_ID = sharedPreferences.getInt("USER_ID", -1);
    }


    private void initViews() {
        recyclerView = findViewById(R.id.rcvOrderpayment);
        imgBackCart = findViewById(R.id.imgBackOderpayment);
        rlAdress = findViewById(R.id.rlAdress);
        tvAddressText = findViewById(R.id.tvAddressText);
        tvMoneyShip = findViewById(R.id.tvMoneyShip);
        tvMoneyProduct = findViewById(R.id.tvMoneyProduct);
        tvVoucherPrice = findViewById(R.id.tvVoucherPrice);
        tvTotalMoney = findViewById(R.id.tvTotalMoney);
        llVoucher = findViewById(R.id.llVoucher);
        tvVoucherAdd = findViewById(R.id.tvVoucherAdd);
        tvVoucherName = findViewById(R.id.tvVoucherName);
        radioGroupPayment = findViewById(R.id.radioGroupPayment);
        radioCashOnDelivery = findViewById(R.id.radioCashOnDelivery);
        radioEWallet = findViewById(R.id.radioEWallet);
        tvWalletBalance = findViewById(R.id.tvWalletBalance);
        btnPayOrderProduct = findViewById(R.id.btnPayOrderProduct);
    }

    private void handleVoucherSelection() {
            Intent intent = new Intent(this, VoucherActivity.class);
            startActivityForResult(intent, REQUEST_SELECT_VOUCHER);
    }
    private void clickChoiceAddress() {
        Intent intent = new Intent(this, AdressActivity.class);
        startActivityForResult(intent, REQUEST_SELECT_ADDRESS);
    }

    private void handlePaymentSelection() {
        radioGroupPayment.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radioEWallet) {
                tvWalletBalance.setText("Số dư ví: " + String.format("%,.0f", walletDAO.getFirstPay()) + " VNĐ");
                tvWalletBalance.setVisibility(View.VISIBLE);
            } else {
                tvWalletBalance.setVisibility(View.GONE);
            }
        });
    }

    private void handleOrderPayment() {
        btnPayOrderProduct.setOnClickListener(v -> {
            if (radioEWallet.isChecked()) {
                double currentWalletBalance = walletDAO.getFirstPay();

                if (currentWalletBalance < totalOrderAmount) {
                    Toast.makeText(this, "Số dư ví không đủ!", Toast.LENGTH_LONG).show();
                } else {
                    double newBalance = currentWalletBalance - totalOrderAmount;

                    ModelWallet wallet = new ModelWallet();
                    wallet.setId(1);
                    wallet.setPay(newBalance);

                    long check = walletDAO.updatePay(wallet); // Cập nhật số dư mới vào cơ sở dữ liệu
                    if (check > 0) {
                        Toast.makeText(this, "Thanh toán thành công!", Toast.LENGTH_SHORT).show();

                        saveOrder();
                        Intent intent = new Intent(this, InvoiceActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(this, "Thanh toán thất bại!", Toast.LENGTH_SHORT).show();
                    }

                    tvWalletBalance.setText("Số dư ví: " + String.format("%,.0f", newBalance) + " VNĐ");

                }
            } else if (radioCashOnDelivery.isChecked()) {
                Toast.makeText(this, "Thanh toán khi nhận hàng!", Toast.LENGTH_SHORT).show();

                saveOrder();
                Intent intent = new Intent(this, InvoiceActivity.class);
                startActivity(intent);
            }
        });
    }

    private void saveOrder() {
        String address = tvAddressText.getText().toString();
        String paymentMethod = radioEWallet.isChecked() ? "E-Wallet" : "Cash on Delivery";

        ModelOrder order = new ModelOrder();
        order.setUserID(USER_ID);
        order.setAddress(address);
        order.setTotalAmount(totalOrderAmount);
        order.setPaymentMethod(paymentMethod);
        order.setStatus("Đang xử lí");

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        String formattedDate = sdf.format(date);
        order.setDate(formattedDate);

        OrderDAO orderDAO = new OrderDAO(this);
        long orderId = orderDAO.insertOrder(USER_ID,order, productsList);

        if (orderId != -1) {
            Log.e("id", "id khi mua hàng: " + orderId);
            Toast.makeText(this, "Đơn hàng đã được lưu!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Lưu đơn hàng thất bại!", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateTotalMoney() {
        double totalProductPrice = 0;
        for (ModelCart cart : cartList) {
//            totalProductPrice += cart.getAmount() * cart.getQuantity();
            totalProductPrice += cart.getAmount();
        }

        double shippingFee = 20000;
        double voucherDiscount = 0;

        String voucherType = tvVoucherAdd.getText().toString();
        if (voucherType.equalsIgnoreCase("Giảm giá 50%")) {
            voucherDiscount = totalProductPrice * 0.5;
        } else if (voucherType.equalsIgnoreCase("Free ship")) {
            voucherDiscount = 20000;
        }

        double totalAmount = totalProductPrice + shippingFee - voucherDiscount;
        totalOrderAmount = totalAmount;

        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        tvMoneyProduct.setText(decimalFormat.format(totalProductPrice) + " VNĐ");
        tvMoneyShip.setText(decimalFormat.format(shippingFee) + " VNĐ");
        tvVoucherPrice.setText("-" + decimalFormat.format(voucherDiscount) + " VNĐ");
        tvTotalMoney.setText(decimalFormat.format(totalAmount) + " VNĐ");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_SELECT_ADDRESS && resultCode == RESULT_OK && data != null) {
            String fullname = data.getStringExtra("fullname");
            String phone = data.getStringExtra("phone");
            String address = data.getStringExtra("address");

//            tvAddressText.setText(fullname + " | " + phone + "\n" + address);
            if (fullname != null && phone != null && address != null) {
                tvAddressText.setText("Họ và tên: " + fullname + "\nSĐT: " + phone + "\nĐịa chỉ: " + address);
            }
        } else if (requestCode == REQUEST_SELECT_VOUCHER && resultCode == RESULT_OK && data != null) {
            String nameSale = data.getStringExtra("namesale");

            if (nameSale != null) {
                tvVoucherAdd.setText(nameSale);
                tvVoucherName.setText("Voucher: "+ nameSale);
                updateTotalMoney();  // Cập nhật lại tiền thanh toán khi voucher được chọn
            }
        }
    }
}
