package ra.entily;

import ra.Interface.IBill_Detail;
import ra.entily.Bill;
import ra.entily.Product;
import ra.util.ConnectionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Bill_Detail implements IBill_Detail<Bill_Detail, Integer> {
    private int bill_detail_id;
    private int bill_id;
    private String product_id;
    private int quantity;
    private float price;

    public Bill_Detail() {
    }

    public Bill_Detail(int bill_detail_id, int bill_id, String product_id, int quantity, float price) {
        this.bill_detail_id = bill_detail_id;
        this.bill_id = bill_id;
        this.product_id = product_id;
        this.quantity = quantity;
        this.price = price;
    }

    public int getBill_detail_id() {
        return bill_detail_id;
    }

    public void setBill_detail_id(int bill_detail_id) {
        this.bill_detail_id = bill_detail_id;
    }

    public int getBill_id() {
        return bill_id;
    }

    public void setBill_id(int bill_id) {
        this.bill_id = bill_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public List<Bill_Detail> getAll() {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        List<Bill_Detail> billDetailsList = null;
        Bill_Detail billDetail = null;
        try {
            callSt = conn.prepareCall("{call get_all_bill_detail()}");
            ResultSet rs = callSt.executeQuery();
            billDetailsList = new ArrayList<>();
            while (rs.next()) {
                billDetail = new Bill_Detail();
                billDetail.setBill_detail_id(rs.getInt("bill_Detail_id"));
                billDetail.setBill_id(rs.getInt("bill_id"));
                billDetail.setProduct_id(rs.getString("product_id"));
                billDetail.setQuantity(rs.getInt("quantity"));
                billDetail.setPrice(rs.getFloat("price"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return billDetailsList;
    }

    @Override
    public boolean create(Bill_Detail billDetail) {
        Connection connection = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        boolean result = false;
        try {
            callSt = connection.prepareCall("{ call create_bd(?,?,?,?)}");
            callSt.setInt(1, billDetail.getBill_id());
            callSt.setString(2, billDetail.getProduct_id());
            callSt.setInt(3, billDetail.getQuantity());
            callSt.setFloat(4, billDetail.getPrice());
            callSt.executeUpdate();
            result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(connection);
        }
        return result;
    }

    public boolean createe(Bill_Detail billDetail) {
        Connection connection = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        boolean result = false;
        try {
            callSt = connection.prepareCall("{ call create_bd_out(?,?,?,?)}");
            callSt.setInt(1, billDetail.getBill_id());
            callSt.setString(2, billDetail.getProduct_id());
            callSt.setInt(3, billDetail.getQuantity());
            callSt.setFloat(4, billDetail.getPrice());
            callSt.executeUpdate();
            result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(connection);
        }
        return result;
    }

    public List<Bill_Detail> find_id_bill_detail(Integer integer) {
        Connection connection = ConnectionDB.openConnection();
        CallableStatement callst = null;
        Bill_Detail billDetail = null;
        List<Bill_Detail> billDetailList = new ArrayList<>();
        try {
            callst = connection.prepareCall("{call get_intf_bill_detail_by_bill_id(?)}");
            callst.setInt(1, integer);
            ResultSet rs = callst.executeQuery();
            while (rs.next()) {
                billDetail = new Bill_Detail();
                billDetail.setBill_detail_id(rs.getInt("bill_Detail_id"));
                billDetail.setBill_id(rs.getInt("bill_id"));
                billDetail.setProduct_id(rs.getString("product_id"));
                billDetail.setQuantity(rs.getInt("quantity"));
                billDetail.setPrice(rs.getFloat("price"));
                billDetailList.add(billDetail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(connection);
        }
        return billDetailList;
    }

    @Override
    public Bill_Detail find_ID(Integer integer) {

        return null;
    }

    @Override
    public void inputData(Scanner scanner) {
        this.bill_id = inputCheck_Bill_Id(scanner, new Bill_Detail());
        this.product_id = inputIdProduct(scanner, new Product());
        this.quantity = inputQuantity(scanner);
        this.price = inputCheckPrice(scanner);
    }

    public void inputDataOut(Scanner scanner) {
        this.bill_id = inputCheck_Bill_Id_Out(scanner, new Bill());
        this.product_id = inputIdProduct(scanner, new Product());
        this.quantity = inputQuantity(scanner);
        this.price = inputCheckPrice(scanner);
    }

    public int inputCheck_Bill_Id(Scanner scanner,Bill_Detail bill) {
        do {
            System.out.println("Nhập vào mã phiếu nhập vào: ");
            try {
                int id = Integer.parseInt(scanner.nextLine());
                if (bill.find_ID(id) != null) {
                    return id;
                } else {
                    System.err.println("Mã phiếu không tồn tại!Nhập lại");
                }
            } catch (NumberFormatException e) {
                System.err.println("Hãy nhập vào số nguyên!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (true);
    }

    public int inputCheck_Bill_Id_Out(Scanner scanner,Bill bill) {
        do {
            System.out.println("Nhập vào mã phiếu xuất ra: ");
            try {
                int id = Integer.parseInt(scanner.nextLine());
                if (bill.find_ID_Out(id) != null) {
                    return id;
                } else {
                    System.err.println("Mã phiếu không tồn tại!Nhập lại");
                }
            } catch (NumberFormatException e) {
                System.err.println("Hãy nhập vào số nguyên!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (true);
    }

    public String inputIdProduct(Scanner scanner, Product product) {
        do {
            System.out.println("Nhập vào mã sản phẩm: ");
            String idProduct = scanner.nextLine();
            if (idProduct.trim().isEmpty()) {
                System.err.println("Không được bỏ trống mã sản phẩm");
            } else if (product.findByID(idProduct) != null) {
                Connection conn = ConnectionDB.openConnection();
                CallableStatement callableStatement = null;
                try {
                    callableStatement = conn.prepareCall("{call get_status_product(?,?)}");
                    callableStatement.setString(1, idProduct);
                    callableStatement.registerOutParameter(2, Types.INTEGER);
                    callableStatement.executeQuery();
                    int getStatus = callableStatement.getInt(2);
                    if (getStatus == 1) {
                        return idProduct;
                    } else {
                        System.err.println("Sản phẩm đã dừng hoạt động ");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    ConnectionDB.closeConnection(conn);
                }
            } else {
                System.err.println("Không tồn tại mã sản phẩm :  " + idProduct);
            }
        } while (true);
    }

    public int inputQuantity(Scanner scanner) {
        do {
            System.out.println("Nhập vào số lượng của sản phẩm: ");
            try {
                int quantity = Integer.parseInt(scanner.nextLine());
                if (quantity <= 0) {
                    System.err.println("Hãy nhập số lượng lớn hơn 0 ");
                } else {
                    return quantity;
                }
            } catch (NumberFormatException e) {
                System.err.println("Hãy nhập vào số nguyên");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (true);
    }

    public float inputCheckPrice(Scanner scanner) {
        do {
            System.out.println("Nhập vào giá nhập của sản phẩm:  ");
            try {
                float price = Float.parseFloat(scanner.nextLine());
                if (price <= 0) {
                    System.err.println("Hãy nhập giá sản phẩm lớn hơn 0");
                } else {
                    return price;
                }
            } catch (NumberFormatException e) {
                System.err.println("Hãy nhập vào số ");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (true);
    }

    @Override
    public void displayDate() {
        System.out.printf("Mã phiếu chi tiết: %d - Mã phiếu nhập: %d \n", this.bill_detail_id, this.bill_id);
        System.out.printf("Mã sản phẩm: %s - Số lượng : %d - Giá nhập vào: %.2f \n", this.product_id, this.quantity, this.price);
    }
}
