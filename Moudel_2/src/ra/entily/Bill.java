package ra.entily;
import ra.Bussiness.User;
import ra.Interface.IBill;

import ra.util.ConnectionDB;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Bill implements IBill<Bill, Integer> {
    private int biull_id;
    private String bill_code;
    private boolean bill_type;
    private String Emp_id_created;
    private Date Created;
    private String Emp_id_auth;
    private Date Auth_date;
    private int Bill_Status;

    public Bill() {
    }

    public Bill(int biull_id, String bill_code, boolean bill_type, String emp_id_created, Date created, String emp_id_auth, Date auth_date, int bill_Status) {
        this.biull_id = biull_id;
        this.bill_code = bill_code;
        this.bill_type = bill_type;
        Emp_id_created = emp_id_created;
        Created = created;
        Emp_id_auth = emp_id_auth;
        Auth_date = auth_date;
        Bill_Status = bill_Status;
    }

    public int getBiull_id() {
        return biull_id;
    }

    public void setBiull_id(int biull_id) {
        this.biull_id = biull_id;
    }

    public String getBill_code() {
        return bill_code;
    }

    public void setBill_code(String bill_code) {
        this.bill_code = bill_code;
    }

    public boolean isBill_type() {
        return bill_type;
    }

    public void setBill_type(boolean bill_type) {
        this.bill_type = bill_type;
    }

    public String getEmp_id_created() {
        return Emp_id_created;
    }

    public void setEmp_id_created(String emp_id_created) {
        Emp_id_created = emp_id_created;
    }

    public Date getCreated() {
        return Created;
    }

    public void setCreated(Date created) {
        Created = created;
    }

    public String getEmp_id_auth() {
        return Emp_id_auth;
    }

    public void setEmp_id_auth(String emp_id_auth) {
        Emp_id_auth = emp_id_auth;
    }

    public Date getAuth_date() {
        return Auth_date;
    }

    public void setAuth_date(Date auth_date) {
        Auth_date = auth_date;
    }

    public int getBill_Status() {
        return Bill_Status;
    }

    public void setBill_Status(int bill_Status) {
        Bill_Status = bill_Status;
    }

    @Override
    public List<Bill> getAll() {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        List<Bill> billList = null;
        try {
            callSt = conn.prepareCall("{call get_All_Bill_create()}");
            billList = new ArrayList<>();
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                Bill bill = new Bill();
                bill.setBiull_id(rs.getInt("bill_id"));
                bill.setBill_code(rs.getString("bill_code"));
                bill.setBill_type(rs.getBoolean("bill_type"));
                bill.setEmp_id_created(rs.getString("emp_id_created"));
                bill.setCreated(rs.getDate("created"));
                bill.setEmp_id_auth(rs.getString("emp_id_auth"));
                bill.setAuth_date(rs.getDate("auth_date"));
                bill.setBill_Status(rs.getInt("bill_status"));
                billList.add(bill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return billList;
    }

    @Override
    public boolean create(Bill bill) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        boolean result = false;
        try {
            callSt = conn.prepareCall("{call new_create_bill(?,?,?,?,?,?,?)}");
            //  callSt.setInt(1, bill.getBiull_id());
            callSt.setString(1, bill.getBill_code());
            callSt.setBoolean(2, bill.isBill_type());
            callSt.setString(3, bill.getEmp_id_created());
            callSt.setDate(4, new java.sql.Date(bill.getCreated().getTime()));
            callSt.setString(5, bill.getEmp_id_auth());
            callSt.setDate(6, new java.sql.Date(bill.getAuth_date().getTime()));
            callSt.setInt(7, bill.getBill_Status());
            callSt.executeUpdate();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Bill find_ID(Integer integer) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        Bill bill = null;
        try {
            callSt = conn.prepareCall("{call  find_by_id_bill(?)}");
            callSt.setInt(1, integer);
            ResultSet rs = callSt.executeQuery();
            if (rs.next()) {
                bill = new Bill();
                bill.setBiull_id(rs.getInt("bill_id"));
                bill.setBill_code(rs.getString("bill_code"));
                bill.setBill_type(rs.getBoolean("bill_type"));
                bill.setEmp_id_created(rs.getString("emp_id_created"));
                bill.setCreated(rs.getDate("created"));
                bill.setEmp_id_auth(rs.getString("emp_id_auth"));
                bill.setAuth_date(rs.getDate("auth_date"));
                bill.setBill_Status(rs.getInt("bill_status"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bill;
    }

    public BillOut find_ID_Out(Integer integer) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        BillOut billOut = null;
        try {
            callSt = conn.prepareCall("{call  find_by_id_bill_out(?)}");
            callSt.setInt(1, integer);
            ResultSet rs = callSt.executeQuery();
            if (rs.next()) {
                billOut = new BillOut();
                billOut.setBiull_id_out(rs.getInt("bill_id"));
                billOut.setBill_code_out(rs.getString("bill_code"));
                billOut.setBill_type_out(rs.getBoolean("bill_type"));
                billOut.setEmp_id_created_out(rs.getString("emp_id_created"));
                billOut.setCreated_out(rs.getDate("created"));
                billOut.setEmp_id_auth_out(rs.getString("emp_id_auth"));
                billOut.setAuth_date_out(rs.getDate("auth_date"));
                billOut.setBill_Status_out(rs.getInt("bill_status"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return billOut;
    }

    public List<Bill> find_bill_code_list(Scanner scanner, Bill bill) {
        System.out.println("Nhập vào bill code cần tìm: ");
        String find = scanner.nextLine();
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        List<Bill> billList = new ArrayList<>();
        try {
            callSt = conn.prepareCall("{call find_by_bill_code(?)}");
            callSt.setString(1, find);
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                bill = new Bill();
                bill.setBiull_id(rs.getInt("bill_id"));
                bill.setBill_code(rs.getString("bill_code"));
                bill.setBill_type(rs.getBoolean("bill_type"));
                bill.setEmp_id_created(rs.getString("emp_id_created"));
                bill.setCreated(rs.getDate("created"));
                bill.setEmp_id_auth(rs.getString("emp_id_auth"));
                bill.setAuth_date(rs.getDate("auth_date"));
                bill.setBill_Status(rs.getInt("bill_status"));
                billList.add(bill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return billList;

    }

    // lấy chi tiết phiếu nhập
    public List<Bill> find_bill_id(Integer integer) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        List<Bill> billList = new ArrayList<>();
        try {
            callSt = conn.prepareCall("{call find_by_id_bill(?)}");
            callSt.setInt(1, integer);
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                Bill bill = new Bill();
                bill.setBiull_id(rs.getInt("bill_id"));
                bill.setBill_code(rs.getString("bill_code"));
                bill.setBill_type(rs.getBoolean("bill_type"));
                bill.setEmp_id_created(rs.getString("emp_id_created"));
                bill.setCreated(rs.getDate("created"));
                bill.setEmp_id_auth(rs.getString("emp_id_auth"));
                bill.setAuth_date(rs.getDate("auth_date"));
                bill.setBill_Status(rs.getInt("bill_status"));
                billList.add(bill);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return billList;
    }

    public Bill find_bill_code(String s) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        Bill bill = null;
        try {
            callSt = conn.prepareCall("{call  find_by_bill_code(?)}");
            callSt.setString(1, s);
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                bill = new Bill();
                bill.setBiull_id(rs.getInt("bill_id"));
                bill.setBill_code(rs.getString("bill_code"));
                bill.setBill_type(rs.getBoolean("bill_type"));
                bill.setEmp_id_created(rs.getString("emp_id_created"));
                bill.setCreated(rs.getDate("created"));
                bill.setEmp_id_auth(rs.getString("emp_id_auth"));
                bill.setAuth_date(rs.getDate("auth_date"));
                bill.setBill_Status(rs.getInt("bill_status"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bill;
    }

    public void update_bill(Scanner scanner) {
        System.out.println("Nhập vào mã phiếu cần cập nhật: ");
        try {
            int bill = Integer.parseInt(scanner.nextLine());
            if (find_ID(bill) != null) {
                Connection connection1 = ConnectionDB.openConnection();
                CallableStatement callSt2 = null;
                try {
                    callSt2 = connection1.prepareCall("{call get_status_bill(?,?)}");
                    callSt2.setInt(1, bill);
                    callSt2.registerOutParameter(2, Types.INTEGER);
                    callSt2.execute();
                    int cnt_status = callSt2.getInt(2);
                    if (cnt_status == 0) {
                        boolean checkOut = true;
                        do {
                            Connection connection = ConnectionDB.openConnection();
                            CallableStatement callSt = null;
                            System.out.println("************ MENU UPDATE BILL IN *************");
                            System.out.println("1. Cập nhật lại mã nhân viên tạo phiếu nhập ");
                            System.out.println("2. Cập nhật lại ngày tạo");
                            System.out.println("3. Cập nhật lại mã nhân viên duyệt phiếu nhập");
                            System.out.println("4. Cập nhật lại ngày duyệt phiếu nhập");
                            System.out.println("5. Thoát");
                            System.out.println("Lựa chọn của bạn : ");
                            try {
                                int choice = Integer.parseInt(scanner.nextLine());
                                switch (choice) {
                                    case 1:
                                        this.Emp_id_created = inputCheckId_Emp(scanner, new Employee());
                                        callSt = connection.prepareCall("{call update_emp_create_by_bill_id(?,?)}");
                                        callSt.setInt(1, bill);
                                        callSt.setString(2, this.Emp_id_created);
                                        callSt.executeUpdate();
                                        System.err.println("Đã cập nhật thành công");
                                        List<Bill> billList1 = getAll();
                                        billList1.stream().forEach(bill1 -> bill1.displayDate());
                                        break;
                                    case 2:
                                        this.Created = checkDate_Bill_Create(scanner);
                                        callSt = connection.prepareCall("{call update_date_create_by_bill_id(?,?)}");
                                        callSt.setInt(1, bill);
                                        callSt.setDate(2, new java.sql.Date(this.Created.getTime()));
                                        callSt.executeUpdate();
                                        System.err.println("Đã cập nhật thành công");
                                        List<Bill> billList2 = getAll();
                                        billList2.stream().forEach(bill1 -> bill1.displayDate());
                                        break;
                                    case 3:
                                        this.Emp_id_auth = inputCheckId_Emp(scanner, new Employee());
                                        callSt = connection.prepareCall("{call update_emp_auth_by_bill_id(?,?)}");
                                        callSt.setInt(1, bill);
                                        callSt.setString(2, this.Emp_id_auth);
                                        callSt.executeUpdate();
                                        System.err.println("Đã cập nhật thành công");
                                        List<Bill> billList3 = getAll();
                                        billList3.stream().forEach(bill1 -> bill1.displayDate());
                                        break;
                                    case 4:
                                        this.Auth_date = checkDate_Bill_Create(scanner);
                                        callSt = connection.prepareCall("{call update_date_auth_by_bill_id(?,?)}");
                                        callSt.setInt(1, bill);
                                        callSt.setDate(2, new java.sql.Date(this.Auth_date.getTime()));
                                        callSt.executeUpdate();
                                        System.err.println("Đã cập nhật thành công");
                                        List<Bill> billList4 = getAll();
                                        billList4.stream().forEach(bill1 -> bill1.displayDate());
                                        break;
                                    case 5:
                                        checkOut = false;
                                        System.err.println("Thoát cập nhật");
                                        break;
                                    default:
                                        System.err.println("Hãy lựa chọn từ 1 - > 7");
                                }
                            } catch (NumberFormatException e) {
                                System.err.println("Hãy nhập vào số nguyên");
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                ConnectionDB.closeConnection(connection);
                            }
                        } while (checkOut);
                    } else if (cnt_status == 1) {
                        System.err.println("Phiếu bị hủy không thể cập nhật");
                    } else {
                        System.err.println("Phiếu này đã duyệt không cập nhật được!");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    ConnectionDB.closeConnection(connection1);
                }
            } else {
                System.err.println("Không tìm thât mã hóa đơn ");
            }
        } catch (NumberFormatException e) {
            System.err.println("Hãy nhập vào số nguyên");
        }
    }

    public void update_status(Scanner scanner,Bill_Detail billDetail) {
        System.out.println("Nhập vào mã phiếu cần cập nhật: ");
        int bill = Integer.parseInt(scanner.nextLine());
        if (find_ID(bill) != null) {
            Connection connection1 = ConnectionDB.openConnection();
            CallableStatement callSt2 = null;
            try {
                callSt2 = connection1.prepareCall("{call get_status_bill(?,?)}");
                callSt2.setInt(1, bill);
                callSt2.registerOutParameter(2, Types.INTEGER);
                callSt2.execute();
                int cnt_status = callSt2.getInt(2);
                if (cnt_status == 0) {
                    System.out.println("Thêm số lượng và giá tiền cho các sản phẩm");
                    billDetail.inputData(scanner);
                    boolean re = billDetail.create(billDetail);
                    if (re) {
                        callSt2 = connection1.prepareCall("{ call update_bill_status_true(?)}");
                        callSt2.setInt(1, bill);
                        callSt2.executeUpdate();
                        System.err.println("Cập nhật duyệt phiếu nhật thành công! ");
                    } else {
                        System.err.println("Lỗi khi thêm số lượng và giá tiền của sản phẩm");
                    }
                } else if (cnt_status == 1) {
                    System.err.println("Phiếu đã bị hủy không thể duyệt");
                } else {
                    System.err.println("Phiếu này đã duyệt trước đó!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                ConnectionDB.closeConnection(connection1);
            }
        } else {
            System.err.println("Bill code không tồn tại! ");
        }
    }

    @Override
    public void inputData(Scanner scanner) {
        this.bill_code = inputCheckBill_Code(scanner);
        this.bill_type = true;
        this.Emp_id_created = User.readDataTOFile().getEmpId();
        //  this.Emp_id_created = inputCheckId_Emp(scanner, new Employee());
        this.Created = checkDate_Bill_Create(scanner);
        this.Emp_id_auth = inputCheckId_Emp_Auth(scanner, new Employee());
        this.Auth_date = checkDate_Bill_Auth(scanner);
        this.Bill_Status = 0;
    }

    public String inputCheckBill_Code(Scanner scanner) {
        do {
            System.out.println("Nhâp vào mã bill code: ");
            String bill_code = scanner.nextLine();
            if (bill_code.trim().length() <= 10) {
                if (find_bill_code(bill_code) == null) {
                    return bill_code;
                } else {
                    System.err.println("Bill code đã tồn tại ");
                }
            } else {
                System.err.println("Bill code phải nhỏ hơn 10 ký tự");
            }
        } while (true);
    }

    public String inputCheckId_Emp(Scanner scanner, Employee employee) {
        do {
            System.out.println("Nhập vào mã nhân viên nhập tạo phiếu nhập: ");
            String id_emp = scanner.nextLine();
            if (employee.find_ID(id_emp) == null) {
                System.err.println("Mã nhân viên không tồn tại! Hãy nhập lại ");
            } else {
                return id_emp;
            }
        } while (true);
    }

    public String inputCheckId_Emp_Auth(Scanner scanner, Employee employee) {
        do {
            System.out.println("Nhập vào mã nhân viên duyệt phiếu: ");
            String id_emp = scanner.nextLine();
            if (employee.find_ID(id_emp) == null) {
                System.err.println("Mã nhân viên không tồn tại! Hãy nhập lại ");
            } else {
                return id_emp;
            }
        } while (true);
    }

    public Date checkDate_Bill_Auth(Scanner scanner) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date day_Auth = null;
        do {
            System.out.println("Nhập vào ngày duyệt: ");
            String birthDayStr = scanner.nextLine();
            try {
                day_Auth = dateFormat.parse(birthDayStr);
                if (day_Auth.after(this.Created)) {
                    return day_Auth;
                } else {
                    System.err.println("Ngày duyệt phải lớn hơn ngày tạo phiếu");
                }
            } catch (ParseException e) {
                System.err.println("Lỗi định dạng yyyy-MM-dd");
                e.printStackTrace();
            }
        } while (true);
    }

    public Date checkDate_Bill_Create(Scanner scanner) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date day = null;
        boolean checkOut = true;
        do {
            System.out.println("Nhập vào ngày duyệt phiếu: ");
            String dayin = scanner.nextLine();
            try {
                day = dateFormat.parse(dayin);
                checkOut = false;
            } catch (ParseException e) {
                System.err.println("Lỗi định dạng yyyy-MM-dd");
                e.printStackTrace();
            }
        } while (checkOut);
        return day;
    }

    public int inputCheckStatus(Scanner scanner) {
        do {
            System.out.println("Nhập vào trạng thái của phiếu: ");
            try {
                int status = Integer.parseInt(scanner.nextLine());
                if (status == 1 || status == 0 || status == 2) {
                    return status;
                } else {
                    System.err.println("Chỉ nhận giá trị 0 ,1 ,2");
                }
            } catch (NumberFormatException e) {
                System.err.println("Hãy nhâp vào số nguyên");
            }
        } while (true);
    }

    @Override
    public void displayDate() {
        System.out.printf("Mã phiếu: %d - Bill code : %s - Kiểu phiếu: %s - Mã nhân viên nhập: %s \n", this.biull_id, this.bill_code, (this.bill_type) ? "Phiếu nhâp" : "Phiếu Xuất", this.Emp_id_created);
        System.out.printf("Ngày tạo: %s - Mã nhân viên duyệt: %s - Ngày duyệt: %s - Trạng thái phiếu: %s \n", this.Created, this.Emp_id_auth, this.Auth_date, (this.Bill_Status == 0) ? "Phiếu tạo" : (this.Bill_Status == 1) ? "Phiếu hủy" : "Phiếu duyệt");
    }
    // của user

    // lấy mã user hiện tại

    public void inputUser(Scanner scanner, User user) {
        this.bill_code = inputCheckBill_Code(scanner);
        this.bill_type = true;
        this.Emp_id_created = User.readDataTOFile().getEmpId();
        this.Created = checkDate_Bill_Create(scanner);
        this.Emp_id_auth = inputCheckId_Emp_Auth(scanner, new Employee());
        this.Auth_date = checkDate_Bill_Auth(scanner);
        this.Bill_Status = inputCheckStatus(scanner);
    }

    public void update_bill_user(Scanner scanner) {
        System.out.println("Nhập vào bill code cần cập nhật: ");
        String bill = scanner.nextLine();
        if (find_bill_code(bill) != null) {
            Connection connection1 = ConnectionDB.openConnection();
            CallableStatement callSt2 = null;
            try {
                callSt2 = connection1.prepareCall("{call get_status_bill(?,?)}");
                callSt2.setString(1, bill);
                callSt2.registerOutParameter(2, Types.INTEGER);
                callSt2.execute();
                int cnt_status = callSt2.getInt(2);
                if (cnt_status == 0) {
                    boolean checkOut = true;
                    do {
                        Connection connection = ConnectionDB.openConnection();
                        CallableStatement callSt = null;
                        System.out.println("************ MENU UPDATE BILL IN *************");
                        System.out.println("1. Cập nhật lại mã nhân viên tạo phiếu nhập ");
                        System.out.println("2. Cập nhật lại ngày tạo");
                        System.out.println("3. Cập nhật lại mã nhân viên duyệt phiếu nhập");
                        System.out.println("4. Cập nhật lại ngày duyệt phiếu nhập");
                        System.out.println("5. Thoát");
                        System.out.println("Lựa chọn của bạn : ");
                        try {
                            int choice = Integer.parseInt(scanner.nextLine());
                            switch (choice) {
                                case 1:
                                    this.Emp_id_created = inputCheckId_Emp(scanner, new Employee());
                                    callSt = connection.prepareCall("{call update_emp_create_by_bill_code(?,?)}");
                                    callSt.setString(1, bill);
                                    callSt.setString(2, this.Emp_id_created);
                                    callSt.executeUpdate();
                                    System.err.println("Đã cập nhật thành công");
                                    List<Bill> billList1 = getAll();
                                    billList1.stream().forEach(bill1 -> bill1.displayDate());
                                    break;
                                case 2:
                                    this.Created = checkDate_Bill_Create(scanner);
                                    callSt = connection.prepareCall("{call update_date_create_by_bill_code(?,?)}");
                                    callSt.setString(1, bill);
                                    callSt.setDate(2, new java.sql.Date(this.Created.getTime()));
                                    callSt.executeUpdate();
                                    System.err.println("Đã cập nhật thành công");
                                    List<Bill> billList2 = getAll();
                                    billList2.stream().forEach(bill1 -> bill1.displayDate());
                                    break;
                                case 3:
                                    this.Emp_id_auth = inputCheckId_Emp(scanner, new Employee());
                                    callSt = connection.prepareCall("{call update_emp_auth_by_bill_code(?,?)}");
                                    callSt.setString(1, bill);
                                    callSt.setString(2, this.Emp_id_auth);
                                    callSt.executeUpdate();
                                    System.err.println("Đã cập nhật thành công");
                                    List<Bill> billList3 = getAll();
                                    billList3.stream().forEach(bill1 -> bill1.displayDate());
                                    break;
                                case 4:
                                    this.Auth_date = checkDate_Bill_Create(scanner);
                                    callSt = connection.prepareCall("{call update_date_auth_by_bill_code(?,?)}");
                                    callSt.setString(1, bill);
                                    callSt.setDate(2, new java.sql.Date(this.Auth_date.getTime()));
                                    callSt.executeUpdate();
                                    System.err.println("Đã cập nhật thành công");
                                    List<Bill> billList4 = getAll();
                                    billList4.stream().forEach(bill1 -> bill1.displayDate());
                                    break;
                                case 5:
                                    checkOut = false;
                                    System.err.println("Thoát cập nhật");
                                    break;
                                default:
                                    System.err.println("Hãy lựa chọn từ 1 - > 7");
                            }
                        } catch (NumberFormatException e) {
                            System.err.println("Hãy nhập vào số nguyên");
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            ConnectionDB.closeConnection(connection);
                        }
                    } while (checkOut);
                } else if (cnt_status == 1) {
                    System.err.println("Phiếu bị hủy không thể cập nhật");
                } else {
                    System.err.println("Phiếu này đã duyệt không cập nhật được!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                ConnectionDB.closeConnection(connection1);
            }
        } else {
            System.err.println("Bill Code không tồn tại ");
        }
    }
}