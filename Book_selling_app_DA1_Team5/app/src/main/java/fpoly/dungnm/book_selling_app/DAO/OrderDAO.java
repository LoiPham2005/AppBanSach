package fpoly.dungnm.book_selling_app.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import fpoly.dungnm.book_selling_app.database.DBHelper;
import fpoly.dungnm.book_selling_app.models.ModelCart;
import fpoly.dungnm.book_selling_app.models.ModelOrder;
import fpoly.dungnm.book_selling_app.models.ModelProducts;

public class OrderDAO {
    SQLiteOpenHelper dbHelper;
    SQLiteDatabase database;

    public OrderDAO(Context context) {
        dbHelper = new DBHelper(context); // Thay bằng lớp DatabaseHelper của bạn
        database = dbHelper.getWritableDatabase();
    }

//    public long insertOrder(ModelOrder order, ArrayList<ModelProducts> productList) {
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        long orderId = -1;
//
//        try {
//            db.beginTransaction();
//
//            // Lưu thông tin đơn hàng vào bảng Orders
//            ContentValues orderValues = new ContentValues();
//
//            orderValues.put("address", order.getAddress());
//            orderValues.put("total_amount", order.getTotalAmount());
//            orderValues.put("payment_method", order.getPaymentMethod());
//            orderValues.put("created_at", System.currentTimeMillis());
//            orderValues.put("status", "Đang xử lý");
//
//            // Định dạng ngày tháng thành chuỗi
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            orderValues.put("date", order.getDate());
//
//
//            orderId = db.insert("Orders", null, orderValues);
//
//            if (orderId != -1) {
//                // Lưu thông tin chi tiết sản phẩm vào bảng OrderDetails
//                for (ModelProducts product : productList) {
//                    ContentValues productValues = new ContentValues();
//                    productValues.put("order_id", orderId);
//                    productValues.put("product_name", product.getTitle());
//                    productValues.put("quantity", product.getQuantity());
//                    productValues.put("price", product.getPrice());
//                    db.insert("OrderDetails", null, productValues);
//                }
//            }
//
//            db.setTransactionSuccessful();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            db.endTransaction();
//            db.close();
//        }
//
//        return orderId;
//    }

    public long insertOrder(int userID,ModelOrder order, ArrayList<ModelProducts> productsList) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long orderId = -1;

        try {
            db.beginTransaction(); // Bắt đầu transaction

            // Thêm thông tin vào bảng ORDERS
            ContentValues orderValues = new ContentValues();
            orderValues.put("UserID", userID);
            orderValues.put("address", order.getAddress());
            orderValues.put("total_amount", order.getTotalAmount());
            orderValues.put("payment_method", order.getPaymentMethod());
            orderValues.put("created_at", System.currentTimeMillis());
            orderValues.put("status", order.getStatus());
            orderValues.put("date", order.getDate());

            // Định dạng ngày tháng thành chuỗi
            orderValues.put("date", order.getDate());


            // Chèn vào bảng ORDERS và lấy ID của đơn hàng vừa tạo
            orderId = db.insert("ORDERS", null, orderValues);

            if (orderId != -1) {
                Log.e("DEBUG", "Order inserted with ID: " + orderId);
                // Thêm chi tiết sản phẩm vào bảng OrderDetails
                for (ModelProducts productsItem : productsList) {
                    ContentValues detailValues = new ContentValues();
                    detailValues.put("order_id", orderId);
                    detailValues.put("product_id", productsItem.getId()); // Sử dụng product_id thay vì product_name
                    detailValues.put("quantity", productsItem.getQuantity());
                    detailValues.put("price", productsItem.getPrice());

//                    Log.e("888888", "oso luong trong DAO: " + productsItem.getQuantity());
//
//                    // Chèn dữ liệu vào bảng OrderDetails
//                    db.insert("OrderDetails", null, detailValues);
                    long detailId = db.insert("OrderDetails", null, detailValues);
                    if (detailId == -1) {
                        Log.e("DEBUG", "Failed to insert product with ID: " + productsItem.getId());
                    } else {
                        Log.e("DEBUG", "Inserted product with ID: " + productsItem.getId());
                    }
                }


            }

            db.setTransactionSuccessful(); // Đánh dấu transaction thành công
        } catch (Exception e) {
            e.printStackTrace(); // Ghi log nếu có lỗi
        } finally {
            db.endTransaction(); // Kết thúc transaction
            db.close(); // Đóng kết nối
        }

        return orderId; // Trả về ID của đơn hàng
    }




    public ArrayList<ModelOrder> getAllOrders(int userID) {
        ArrayList<ModelOrder> orderList = new ArrayList<>();
        database = dbHelper.getReadableDatabase();

        // Truy vấn tất cả các đơn hàng từ bảng Orders
        Cursor cursor = database.rawQuery("SELECT * FROM ORDERS WHERE UserID = ?", new String[]{String.valueOf(userID)});

        if (cursor.moveToFirst()) {
            do {
                try {
                    // Lấy chỉ mục của các cột trong bảng Orders
                    int idColumnIndex = cursor.getColumnIndex("id");
//                    int userIDColumnIndex = cursor.getColumnIndex("UserID");
                    int addressColumnIndex = cursor.getColumnIndex("address");
                    int totalAmountColumnIndex = cursor.getColumnIndex("total_amount");
                    int paymentMethodColumnIndex = cursor.getColumnIndex("payment_method");
                    int statusColumnIndex = cursor.getColumnIndex("status");
                    int dateColumnIndex = cursor.getColumnIndex("date");
                    int createdAtColumnIndex = cursor.getColumnIndex("created_at");


                    // Lấy các thông tin cơ bản từ bảng Orders
                    int id = cursor.getInt(idColumnIndex);
//                    int userID = cursor.getInt(userIDColumnIndex);
                    String address = cursor.getString(addressColumnIndex);
                    double totalAmount = cursor.getDouble(totalAmountColumnIndex);
                    String paymentMethod = cursor.getString(paymentMethodColumnIndex);
                    String status = cursor.getString(statusColumnIndex);
                    String createdAt = cursor.getString(createdAtColumnIndex); // Lấy thông tin created_at


                    // Xử lý ngày
                    String numericDate = cursor.getString(dateColumnIndex);
                    SimpleDateFormat inputFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
                    SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

                    Date date = inputFormat.parse(numericDate);  // Chuyển ngày số thành đối tượng Date
                    String formattedDate = outputFormat.format(date);  // Định dạng thành dd-MM-yyyy

                    // Tạo đối tượng ModelOrder
                    ModelOrder order = new ModelOrder();
                    order.setId(id);
                    order.setUserID(userID);
                    order.setAddress(address);
                    order.setTotalAmount(totalAmount);
                    order.setPaymentMethod(paymentMethod);
                    order.setStatus(status);
                    order.setDate(formattedDate);
                    order.setCreated_at(createdAt);  // Gán giá trị created_at

                    // Lấy danh sách sản phẩm từ bảng OrderDetails
                    ArrayList<ModelProducts> productList = new ArrayList<>();
                    Cursor productCursor = database.rawQuery("SELECT * FROM OrderDetails WHERE order_id = ?", new String[]{String.valueOf(id)});

                    if (productCursor.moveToFirst()) {
                        do {
                            // Lấy chỉ mục của các cột trong bảng OrderDetails
                            int orderIDColumnIndex = productCursor.getColumnIndex("order_id");
                            int quantityColumnIndex = productCursor.getColumnIndex("quantity");
                            int priceColumnIndex = productCursor.getColumnIndex("price");
                            int productIdColumnIndex = productCursor.getColumnIndex("product_id");

                            // Lấy thông tin sản phẩm
                            int orderId = productCursor.getInt(orderIDColumnIndex);
                            int quantity = productCursor.getInt(quantityColumnIndex);
                            double price = productCursor.getDouble(priceColumnIndex);
                            int productId = productCursor.getInt(productIdColumnIndex);

                            // Tạo đối tượng ModelProducts và thêm vào danh sách
                            ModelProducts product = new ModelProducts();
                            product.setQuantity(quantity);
                            product.setPrice((int) price);
                            product.setId(productId);

                            productList.add(product);
                        } while (productCursor.moveToNext());
                    }
                    productCursor.close();

                    // Gắn danh sách sản phẩm vào đơn hàng
                    order.setProducts(productList);

                    // Thêm đơn hàng vào danh sách
                    orderList.add(order);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return orderList;
    }


    // hàm tính doanh thu
//public double getTotalRevenueByDateRange() {
//    double totalRevenue = 0.0;
//    double totalAmount = 0;
//    database = dbHelper.getReadableDatabase();
//
//    // Câu truy vấn tổng doanh thu từ ngày bắt đầu đến ngày kết thúc
////    String query = "SELECT SUM(total_amount) AS total_revenue FROM ORDERS WHERE date BETWEEN ? AND ?";
//    String query = "SELECT SUM(total_amount) FROM ORDERS";
//    Cursor cursor = null;
//    try {
////        cursor = database.rawQuery(query, new String[]{startDate, endDate});
//        if (cursor.moveToFirst()) {
////            double totalRevenue1 = cursor.getColumnIndex("total_revenue");
////            totalRevenue = cursor.getDouble((int) totalRevenue1);
//            totalAmount = cursor.getDouble(0);
//        }
//    } catch (Exception e) {
//        e.printStackTrace();
//    } finally {
//        if (cursor != null) {
//            cursor.close();
//        }
//    }
//
//    return totalRevenue;
//}

//    public double getTotalRevenueByDateRange(String startDate, String endDate) {
//        double totalRevenue = 0;
//        database = dbHelper.getReadableDatabase();
//
//        String query = "SELECT SUM(total_amount) FROM ORDERS WHERE date BETWEEN ? AND ?";
//        Cursor cursor = database.rawQuery(query, new String[]{startDate, endDate});
//
//        if (cursor.moveToFirst()) {
//            totalRevenue = cursor.getDouble(0);
//        }
//        cursor.close();
//        return totalRevenue;
//    }

    public double getTotalRevenueByDateRange(String startDate, String endDate) {
        double totalRevenue = 0;
        SQLiteDatabase database = null;
        Cursor cursor = null;
        try {
            // Mở kết nối cơ sở dữ liệu
            database = dbHelper.getReadableDatabase();

            // Truy vấn tổng doanh thu trong khoảng thời gian đã chọn
            String query = "SELECT SUM(total_amount) FROM ORDERS WHERE date BETWEEN ? AND ?";
            cursor = database.rawQuery(query, new String[]{startDate, endDate});

            // Lấy giá trị tổng doanh thu
            if (cursor.moveToFirst()) {
                totalRevenue = cursor.getDouble(0);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Ghi log lỗi
        } finally {
            // Đóng Cursor và cơ sở dữ liệu để tránh rò rỉ tài nguyên
            if (cursor != null) {
                cursor.close();
            }
            if (database != null) {
                database.close();
            }
        }
        return totalRevenue;
    }




}
