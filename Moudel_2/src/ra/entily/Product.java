package ra.entily;

import ra.Interface.IProduct;
import ra.util.ConnectionDB;
import java.sql.*;
import java.util.*;
import java.util.Date;

public class Product implements IProduct<Product, String> {
    private String product_id;
    private String product_name;
    private String mannufacturer;
    private Date created;
    private int batch;
    private int quantity;
    private boolean product_status;

    public Product() {
    }

    public Product(String product_id, String product_name, String mannufacturer, Date created, int batch, int quantity, boolean product_status) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.mannufacturer = mannufacturer;
        this.created = created;
        this.batch = batch;
        this.quantity = quantity;
        this.product_status = product_status;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getMannufacturer() {
        return mannufacturer;
    }

    public void setMannufacturer(String mannufacturer) {
        this.mannufacturer = mannufacturer;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public int getBatch() {
        return batch;
    }

    public void setBatch(int batch) {
        this.batch = batch;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isProduct_status() {
        return product_status;
    }

    public void setProduct_status(boolean product_status) {
        this.product_status = product_status;
    }


    @Override
    public List<Product> getAll() {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        List<Product> productList = null;// trả về list
        try {
            callSt = conn.prepareCall("{call getALl_product()}");
            ResultSet rs = callSt.executeQuery();
            productList = new ArrayList<>();
            while (rs.next())  // lấy từng dòng
            {
                Product product = new Product();
                product.setProduct_id(rs.getString("product_id"));
                product.setProduct_name(rs.getString("product_name"));
                product.setMannufacturer(rs.getString("mannufacturer"));
                product.setCreated(rs.getDate("created"));
                product.setBatch(rs.getInt("batch"));
                product.setQuantity(rs.getInt("quantity"));
                product.setProduct_status(rs.getBoolean("product_status"));
                productList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return productList;
    }

    @Override
    public boolean create(Product product) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        boolean result1 = false;
        try {
            conn.setAutoCommit(false);
            callSt = conn.prepareCall("{ call create_new_product(?,?,?,?,?,?)}");
            // sét giá trị cho các tham sô
            callSt.setString(1, getProduct_id());
            callSt.setString(2, getProduct_name());
            callSt.setString(3, getMannufacturer());
            callSt.setInt(4, getBatch());
            callSt.setInt(5, getQuantity());
            callSt.setBoolean(6, isProduct_status());
            callSt.executeUpdate();
            conn.commit();
            result1 = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return result1;
    }

    @Override
    public boolean update(Product product) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        boolean result = false;
        try {
            conn.setAutoCommit(false);
            // 2. Bắt đầu cập nhật
            callSt = conn.prepareCall("{call update_product(?,?,?,?,?)}");
            // set giá trị cho các tham số
            callSt.setString(1, product.getProduct_id());
            callSt.setString(2, product.getProduct_name());
            callSt.setString(3, product.getMannufacturer());
            callSt.setInt(4, product.getBatch());
            callSt.setBoolean(5, product.isProduct_status());
            // đăng ký kiểu dữ liệu cho các tham số ra
            callSt.executeUpdate();
            conn.commit();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ex) {
                e.printStackTrace();
            }
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return result;
    }

    @Override
    public Product findByID(String s) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        Product product = null;
        try {
            callSt = conn.prepareCall("{call get_by_productID(?)}");
            callSt.setString(1, s);
            ResultSet rs = callSt.executeQuery();
            if (rs.next()) {
                product = new Product();
                product.setProduct_id(rs.getString("product_id"));
                product.setProduct_name(rs.getString("product_name"));
                product.setMannufacturer(rs.getString("mannufacturer"));
                product.setCreated(rs.getDate("created"));
                product.setBatch(rs.getInt("batch"));
                product.setQuantity(rs.getInt("quantity"));
                product.setProduct_status(rs.getBoolean("product_status"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return product;
    }

    @Override
    public Product findByName(String s) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        Product product = null;
        try {
            callSt = conn.prepareCall("{call get_by_productName(?)}");
            callSt.setString(1, s);
            ResultSet rs = callSt.executeQuery();
            if (rs.next()) {
                product = new Product();
                product.setProduct_id(rs.getString("product_id"));
                product.setProduct_name(rs.getString("product_name"));
                product.setMannufacturer(rs.getString("mannufacturer"));
                product.setCreated(rs.getDate("created"));
                product.setBatch(rs.getInt("batch"));
                product.setQuantity(rs.getInt("quantity"));
                product.setProduct_status(rs.getBoolean("product_status"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return product;
    }

    public List<Product> find_Name(String s) {
        Connection connection = ConnectionDB.openConnection();
        CallableStatement callst = null;
        List<Product> productList = new ArrayList<>();
        try {
            callst = connection.prepareCall("{call get_by_productName(?)}");
            callst.setString(1, s);
            ResultSet rs = callst.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setProduct_id(rs.getString("product_id"));
                product.setProduct_name(rs.getString("product_name"));
                product.setMannufacturer(rs.getString("mannufacturer"));
                product.setCreated(rs.getDate("created"));
                product.setBatch(rs.getInt("batch"));
                product.setQuantity(rs.getInt("quantity"));
                product.setProduct_status(rs.getBoolean("product_status"));
                productList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(connection);
        }
        return productList;
    }

    public void update_product(Scanner scanner) {
        System.out.println("Nhập vào mã sản phầm muốn cập nhật: ");
        String idProduct = scanner.nextLine();
        if (idProduct.trim().isEmpty()) {
            System.err.println("Không được để trống mã sản phẩm");
        } else if (idProduct.trim().length() <= 5) {
            if (findByID(idProduct) != null) {
                Connection conn = ConnectionDB.openConnection();
                CallableStatement callableStatement = null;
                try {
                    callableStatement = conn.prepareCall("{call get_status_product(?,?)}");
                    callableStatement.setString(1, idProduct);
                    callableStatement.registerOutParameter(2, Types.INTEGER);
                    callableStatement.executeQuery();
                    int getStatus = callableStatement.getInt(2);
                    if (getStatus == 0) {
                        boolean checkOut = true;
                        do {
                            System.out.println("Sản phẩm đang ở trạng thái dừng  hoạt động ");
                            System.out.println("Ban có muốn cập nhật trạng thái sản phẩm trở lại hoạt không không ?");
                            System.out.println("1. Có");
                            System.out.println("2. Không");
                            System.out.println("Lựa chọn của bạn: ");
                            try {
                                int choice = Integer.parseInt(scanner.nextLine());
                                switch (choice) {
                                    case 1:
                                        callableStatement = conn.prepareCall("{call update_status_true(?)}");
                                        callableStatement.setString(1, idProduct);
                                        callableStatement.executeUpdate();
                                        System.err.println("Đã cập nhật trạng thái sản phẩm thành hoạt động ");
                                        checkOut = false;
                                        break;
                                    case 2:
                                        System.err.println("Không cập nhật trạng thái sản phẩm ");
                                        checkOut = false;
                                        break;

                                }
                            } catch (NumberFormatException e) {
                                System.err.println("Hãy lựa chọn số nguyên");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } while (checkOut);
                    } else {
                        boolean checkOut = true;
                        do {
                            System.err.println("Sản phẩm đang ở trạng thái  hoạt động ");
                            System.out.println("Ban có muốn cập nhật trạng thái sản phẩm thành dừng hoạt động không ? ");
                            System.out.println("1. Có");
                            System.out.println("2. Không");
                            System.out.println("Lựa chọn của bạn: ");
                            int choice = Integer.parseInt(scanner.nextLine());
                            switch (choice) {
                                case 1:
                                    callableStatement = conn.prepareCall("{call update_status_false(?)}");
                                    callableStatement.setString(1, idProduct);
                                    callableStatement.executeUpdate();
                                    System.err.println("Đã cập nhật trạng thái sản phẩm thành dừng hoạt động ");
                                    checkOut = false;
                                    break;
                                case 2:
                                    System.err.println("Không cập nhật trạng thái sản phẩm ");
                                    checkOut = false;
                                    break;

                            }
                        } while (checkOut);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    ConnectionDB.closeConnection(conn);
                }
            } else {
                System.err.println("Mã sản phẩm không tồn tại");
            }
        } else {
            System.err.println("Mã sản phẩm không được lớn hơn 5 ký tự ");
        }
    }

    public void updateProduct(Scanner scanner, Product product) {
        boolean checkOut = true;
        System.out.println("Nhập vào mã sản phẩm cần cập nhật: ");
        String id_product = scanner.nextLine();
        if (findByID(id_product) != null) {
            do {
                Connection conn = ConnectionDB.openConnection();
                CallableStatement callSt = null;
                System.out.println("************ MENU UPDATE***********");
                System.out.println("1. Cập nhật tên mới cho sản phẩm");
                System.out.println("2. Cập nhật nhà cung cấp mới");
                System.out.println("3. Cập nhật lại lô chưa sản phẩm");
                System.out.println("4. Thoát");
                System.out.println("Lựa chọn của bạn: ");
                try {
                    int choice = Integer.parseInt(scanner.nextLine());
                    switch (choice) {
                        case 1:
                            this.product_name = checkName(scanner);
                            callSt = conn.prepareCall("{call update_name(?,?)}");
                            callSt.setString(1, id_product);
                            callSt.setString(2, product.getProduct_name());
                            callSt.executeUpdate();
                            System.err.println("Cập nhật thành công");
                            break;
                        case 2:
                            this.mannufacturer = checkName(scanner);
                            callSt = conn.prepareCall("{call update_mannufacturer(?,?)}");
                            callSt.setString(1, id_product);
                            callSt.setString(2, product.getMannufacturer());
                            callSt.executeUpdate();
                            System.err.println("Cập nhật thành công");
                            break;
                        case 3:
                            this.batch = checkBatch(scanner);
                            callSt = conn.prepareCall("{call update_batch(?,?)}");
                            callSt.setString(1, id_product);
                            callSt.setInt(2, product.getBatch());
                            callSt.executeUpdate();
                            System.err.println("Cập nhật thành công");
                            break;
                        case 4:
                            checkOut = false;
                            break;
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Hãy nhập vào số nguyên");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    ConnectionDB.closeConnection(conn);
                }
            } while (checkOut);
        } else {
            System.err.println("Không tồn tại mã sản phẩm : " + id_product);
        }
    }

    @Override
    public boolean update_Status(String s) {
        return false;
    }

    @Override
    public void inputData(Scanner scanner) {
        this.product_id = checkID(scanner);
        this.product_name = checkName(scanner);
        this.mannufacturer = checkMannufacturer(scanner);
        this.batch = checkBatch(scanner);
        this.product_status = checkStatus(scanner);
    }

    public String checkID(Scanner scanner) {
        do {
            Product product = new Product();
            System.out.println("Nhập vào mã sản phẩm(5 ký tự): ");
            String id = scanner.nextLine();
            if (id.trim().length() == 5) {
                if (findByID(id) == null) {
                    return id;
                } else {
                    System.err.println("Mã  sản phẩm đã tồn tại!");
                }
            } else {
                System.err.println("Mã sản phẩm  phải có 5 ký tự");
            }
        } while (true);
    }

    public String checkName(Scanner scanner) {
        do {
            System.out.println("Nhập vào tên sản phẩm : ");
            String name = scanner.nextLine();
            if (name.trim().isEmpty()) {
                System.err.println("Không được để trống tên sản phẩm");
            } else {
                if (findByName(name) == null) {
                    return name;
                } else {
                    System.err.println("Tên đã tồn tại!!");
                }
            }
        } while (true);
    }

    public String checkMannufacturer(Scanner scanner) {
        do {
            System.out.println("Nhập vào tên nhà cung cấp: ");
            String mannufacturerr = scanner.nextLine();
            if (mannufacturerr.trim().isEmpty()) {
                System.err.println("Không được bỏ trống nhà cung cấp!");
            } else {
                return mannufacturerr;
            }
        } while (true);
    }

    public int checkBatch(Scanner scanner) {
        do {
            System.out.println("Nhập vào số lô: ");
            try {
                int batchc = Integer.parseInt(scanner.nextLine());
                if (batchc > 0) {
                    return batchc;
                } else {
                    System.err.println("Hãy nhập vào số lớn hơn 0");
                }
            } catch (NumberFormatException e) {
                System.err.println("Hãy nhập vào số nguyên");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (true);
    }

    public boolean checkStatus(Scanner scanner) {
        do {
            System.out.println("Nhập vào trạng thái của sản phẩm: ");
            String product_statuss = scanner.nextLine();
            if (product_statuss.equals("true") | product_statuss.equals("false")) {
                return Boolean.parseBoolean(product_statuss);
            } else {
                System.err.println("Chỉ nhận giá trị true hoặc false");
            }
        } while (true);
    }


    @Override
    public void displayData() {
        System.out.printf("Mã sản phẩm: %s - Tên sản phẩm :  %s   - Nhà cung cấp:  %s  - Ngày tạo: %s  \n", this.product_id, this.product_name, this.mannufacturer, this.created);
        System.out.printf("Lô chứa hàng: %d  - Số lượng sản phẩm: %d  - Trạng thái: %s  \n", this.batch, this.quantity, this.product_status ? "Hoạt động" : "Không hoạt động");

    }
}
