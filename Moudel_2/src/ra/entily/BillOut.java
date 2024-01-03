package ra.entily;
import ra.Interface.IBill;
import ra.Interface.IBillOut;
import ra.entily.Bill;
import ra.entily.Bill_Detail;
import ra.util.ConnectionDB;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class BillOut implements IBillOut<BillOut, Integer> {
    private int biull_id_out;
    private String bill_code_out;
    private boolean bill_type_out;
    private String Emp_id_created_out;
    private Date Created_out;
    private String Emp_id_auth_out;
    private Date Auth_date_out;
    private int Bill_Status_out;

    public BillOut() {
    }

    public BillOut(int biull_id_out, String bill_code_out, boolean bill_type_out, String emp_id_created_out, Date created_out, String emp_id_auth_out, Date auth_date_out, int bill_Status_out) {
        this.biull_id_out = biull_id_out;
        this.bill_code_out = bill_code_out;
        this.bill_type_out = bill_type_out;
        Emp_id_created_out = emp_id_created_out;
        Created_out = created_out;
        Emp_id_auth_out = emp_id_auth_out;
        Auth_date_out = auth_date_out;
        Bill_Status_out = bill_Status_out;
    }

    public int getBiull_id_out() {
        return biull_id_out;
    }

    public void setBiull_id_out(int biull_id_out) {
        this.biull_id_out = biull_id_out;
    }

    public String getBill_code_out() {
        return bill_code_out;
    }

    public void setBill_code_out(String bill_code_out) {
        this.bill_code_out = bill_code_out;
    }

    public boolean isBill_type_out() {
        return bill_type_out;
    }

    public void setBill_type_out(boolean bill_type_out) {
        this.bill_type_out = bill_type_out;
    }

    public String getEmp_id_created_out() {
        return Emp_id_created_out;
    }

    public void setEmp_id_created_out(String emp_id_created_out) {
        Emp_id_created_out = emp_id_created_out;
    }

    public Date getCreated_out() {
        return Created_out;
    }

    public void setCreated_out(Date created_out) {
        Created_out = created_out;
    }

    public String getEmp_id_auth_out() {
        return Emp_id_auth_out;
    }

    public void setEmp_id_auth_out(String emp_id_auth_out) {
        Emp_id_auth_out = emp_id_auth_out;
    }

    public Date getAuth_date_out() {
        return Auth_date_out;
    }

    public void setAuth_date_out(Date auth_date_out) {
        Auth_date_out = auth_date_out;
    }

    public int getBill_Status_out() {
        return Bill_Status_out;
    }

    public void setBill_Status_out(int bill_Status_out) {
        Bill_Status_out = bill_Status_out;
    }

    // thêm mới đối tượng
    @Override
    public boolean create(BillOut billOut) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        boolean result = false;
        try {
            callSt = conn.prepareCall("{call new_create_bill_out(?,?,?,?,?,?,?)}");
            callSt.setString(1, billOut.getBill_code_out());
            callSt.setBoolean(2, billOut.isBill_type_out());
            callSt.setString(3, billOut.getEmp_id_created_out());
            callSt.setDate(4, new java.sql.Date(billOut.getCreated_out().getTime()));
            callSt.setString(5, billOut.getEmp_id_auth_out());
            callSt.setDate(6, new java.sql.Date(billOut.getAuth_date_out().getTime()));
            callSt.setInt(7, billOut.getBill_Status_out());
            callSt.executeUpdate();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<BillOut> getAllOut() {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        List<BillOut> billOutsList = null;
        try {
            callSt = conn.prepareCall("{call get_All_out()}");
            billOutsList = new ArrayList<>();
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                BillOut billOut = new BillOut();
                billOut.setBiull_id_out(rs.getInt("bill_id"));
                billOut.setBill_code_out(rs.getString("bill_code"));
                billOut.setBill_type_out(rs.getBoolean("bill_type"));
                billOut.setEmp_id_created_out(rs.getString("emp_id_created"));
                billOut.setCreated_out(rs.getDate("created"));
                billOut.setEmp_id_auth_out(rs.getString("emp_id_auth"));
                billOut.setAuth_date_out(rs.getDate("auth_date"));
                billOut.setBill_Status_out(rs.getInt("bill_status"));
                billOutsList.add(billOut);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return billOutsList;
    }

    // tìm theo bill code out
    public BillOut find_out_by_code(String s) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        BillOut billOut = null;
        try {
            callSt = conn.prepareCall("{call  find_by_bill_code_out(?)}");
            callSt.setString(1, s);
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
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
    public List<Bill> find_bill_id(Integer integer) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        List<Bill> billList = new ArrayList<>();
        try {
            callSt = conn.prepareCall("{call find_by_id_bill_out(?)}");
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


    @Override
    public BillOut find_ID(Integer integer) {
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

    public void update_infor_out(Scanner scanner, BillOut billOut) {
        System.out.println("Nhập vào bill code cần cập nhật: ");
        String code = scanner.nextLine();
        if (code.trim().isEmpty()) {
            System.err.println("Không được bỏ trống");
        } else if (code.trim().length() <= 10) {
            if (find_out_by_code(code) != null) {
                Connection conn = ConnectionDB.openConnection();
                CallableStatement callSt = null;
                try {
                    callSt = conn.prepareCall("{ call get_status_bill_out(?,?)}");
                    callSt.setString(1, code);
                    callSt.registerOutParameter(2, Types.INTEGER);
                    callSt.execute();
                    int cnt_status = callSt.getInt(2);
                    if (cnt_status == 0) {
                        boolean checkOut = true;
                        do {
                            Connection connection = ConnectionDB.openConnection();
                            CallableStatement callSt1 = null;
                            System.out.println("********** MENU UPDATE BILL OUT ******************");
                            System.out.println("1. Cập nhật lại mã nhân viên xuất phiếu ");
                            System.out.println("2. Cập nhật lại ngày tạo phiếu xuất");
                            System.out.println("3. Cập nhật lại mã nhân viên duyệt phiếu xuất ");
                            System.out.println("4. Cập nhật lại ngày xuất phiếu ");
                            System.out.println("5. Thoát cập nhật");
                            System.out.println("Lựa chọn của bạn:  ");
                            try {
                                int choice = Integer.parseInt(scanner.nextLine());
                                switch (choice) {
                                    case 1:
                                        this.Emp_id_created_out = inputCheckId_Emp_out(scanner, new Employee());
                                        callSt1 = connection.prepareCall("{call update_emp_create_by_bill_code_out(?,?)}");
                                        callSt1.setString(1, code);
                                        callSt1.setString(2, this.Emp_id_created_out);
                                        callSt1.executeUpdate();
                                        System.err.println("Đã cập nhật thành công");
                                        List<BillOut> billOutsList = billOut.getAllOut();
                                        for (BillOut bo : billOutsList) {
                                            bo.displayDate();
                                        }
                                        break;
                                    case 2:
                                        this.Created_out = checkDate_Bill_Create_out(scanner, new Bill());
                                        callSt1 = connection.prepareCall("{call update_date_create_by_bill_code_out(?,?)}");
                                        callSt1.setString(1, code);
                                        callSt1.setDate(2, new java.sql.Date(this.Created_out.getTime()));
                                        callSt1.executeUpdate();
                                        System.err.println("Đã cập nhật thành công");
                                        List<BillOut> billOutsList2 = billOut.getAllOut();
                                        for (BillOut bo : billOutsList2) {
                                            bo.displayDate();
                                        }
                                        break;
                                    case 3:
                                        this.Emp_id_auth_out = inputCheckId_Emp_Auth_out(scanner, new Employee());
                                        callSt1 = connection.prepareCall("{call update_emp_auth_by_bill_code_out(?,?)}");
                                        callSt1.setString(1, code);
                                        callSt1.setString(2, this.Emp_id_auth_out);
                                        callSt1.executeUpdate();
                                        System.err.println("Đã cập nhật thành công");
                                        List<BillOut> billOutsList5 = billOut.getAllOut();
                                        for (BillOut bo : billOutsList5) {
                                            bo.displayDate();
                                        }
                                        break;
                                    case 4:
                                        this.Auth_date_out = checkDate_Bill_Auth_Out(scanner);
                                        callSt1 = connection.prepareCall("{call update_date_auth_by_bill_code_out(?,?)}");
                                        callSt1.setString(1, code);
                                        callSt1.setDate(2, new java.sql.Date(this.Auth_date_out.getTime()));
                                        callSt1.executeUpdate();
                                        System.err.println("Đã cập nhật thành công");
                                        List<BillOut> billOutsList3 = billOut.getAllOut();
                                        for (BillOut bo : billOutsList3) {
                                            bo.displayDate();
                                        }
                                        break;
                                    case 5:
                                        checkOut = false;
                                        System.err.println("Thoát cập nhật");
                                        break;
                                    default:
                                        System.err.println("Hãy lựa cập nhật từ 1-> 5 ");
                                }
                            } catch (NumberFormatException e) {
                                System.err.println("Hãy nhập vào số nguyên");
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                ConnectionDB.closeConnection(conn);
                            }
                        } while (checkOut);
                    } else if (cnt_status == 1) {
                        System.err.println("Phiếu xuất đã được hủy. Không thể cập nhật");
                    } else {
                        System.err.println("Phiếu đã được duyệt không thể cập nhật được ");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    ConnectionDB.closeConnection(conn);
                }
            } else {
                System.err.println("Bill code  không tồn tại");
            }
        } else {
            System.err.println("Hãy nhập mã bill code nhỏ hơn 10 ký tự ");
        }

    }

    public void update_status_out(Scanner scanner, Bill_Detail billDetail, BillOut billOut) {
        System.out.println("Nhập vào mã bill code xuất phiếu: ");
        String billCodeOut = scanner.nextLine();
        if (billCodeOut.trim().isEmpty()) {
            System.err.println("Không được bỏ trống");
        } else if (billCodeOut.trim().length() <= 10) {
            if (find_out_by_code(billCodeOut) != null) {
                Connection connection = ConnectionDB.openConnection();
                CallableStatement callSt = null;
                try {
                    callSt = connection.prepareCall("{call get_status_bill_out(?,?)}");
                    callSt.setString(1, billCodeOut);
                    callSt.registerOutParameter(2, Types.INTEGER);
                    callSt.execute();
                    int cnt_status = callSt.getInt(2);
                    if (cnt_status == 0) {
                        System.err.println("Nhập vào các giá trị xuất phiếu ra");
                        billDetail.inputDataOut(scanner);
                        boolean rs = billDetail.createe(billDetail);
                        if (rs) {
                            callSt = connection.prepareCall("{ call update_bill_status_true_out(?)}");
                            callSt.setString(1, billCodeOut);
                            callSt.executeUpdate();
                            System.err.println("Cập nhật duyệt thành công phiếu xuất ");
                            List<BillOut> billOutsList = billOut.getAllOut();
                            for (BillOut bo : billOutsList) {
                                bo.displayDate();
                            }
                        }
                    } else if (cnt_status == 1) {
                        System.err.println("Phiếu xuất đã hủy! Không thể cập nhật ");
                    } else {
                        System.err.println("Phiếu xuất đã được duyệt! Không thể cập nhật");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    ConnectionDB.closeConnection(connection);
                }

            } else {
                System.err.println("Mã bill code không tồn tại");
            }
        } else {
            System.err.println("Bill code không được lớn hơn 10 ký tự");
        }
    }

    @Override
    public void inputData(Scanner scanner) {
        this.bill_code_out = inputCheckBillCode(scanner);
        this.bill_type_out = false;
        this.Emp_id_created_out = inputCheckId_Emp_out(scanner, new Employee());
        this.Created_out = checkDate_Bill_Create_out(scanner, new Bill());
        this.Emp_id_auth_out = inputCheckId_Emp_Auth_out(scanner, new Employee());
        this.Auth_date_out = checkDate_Bill_Auth_Out(scanner);
        this.Bill_Status_out = inputCheckStatus(scanner);
    }

    public int inputCheckStatus(Scanner scanner) {
        do {
            System.out.println("Nhập vào trạng thái của phiếu xuât: ");
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

    public Date checkDate_Bill_Auth_Out(Scanner scanner) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date day_Auth = null;
        do {
            System.out.println("Nhập vào ngày duyệt phiếu xuất : ");
            String birthDayStr = scanner.nextLine();
            try {
                day_Auth = dateFormat.parse(birthDayStr);
                if (day_Auth.after(this.Created_out)) {
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

    public String inputCheckId_Emp_Auth_out(Scanner scanner, Employee employee) {
        do {
            System.out.println("Nhập vào mã nhân viên duyệt phiếu xuất: ");
            String id_emp_out = scanner.nextLine();
            if (id_emp_out.trim().isEmpty()) {
                System.err.println("Không được để trống mã nhân viên! Hãy nhập lại");
            } else if (id_emp_out.trim().length() <= 5) {
                if (employee.find_ID(id_emp_out) == null) {
                    System.err.println("Mã nhân viên không tồn tại! Hãy nhập lại ");
                } else {
                    return id_emp_out;
                }
            } else {
                System.err.println("Mã nhân viên không vượt quá 5 ký tự! Hãy nhập lại");
            }
        } while (true);
    }

    public Date checkDate_Bill_Create_out(Scanner scanner, Bill bill) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date day = null;
        boolean chekout = true;
        do {
            System.out.println("Nhập vào ngày tạo phiếu xuất: ");
            String dayOut = scanner.nextLine();
            try {
                day = dateFormat.parse(dayOut);
                chekout = false;
            } catch (ParseException e) {
                System.err.println("Lỗi định dạng yyyy-MM-dd");
                e.printStackTrace();
            }
        } while (chekout);
        return day;
    }

    public String inputCheckId_Emp_out(Scanner scanner,Employee employee) {
        do {
            System.out.println("Nhập vào mã nhân viên nhập phiếu xuất: ");
            String id_emp = scanner.nextLine();
            if (id_emp.trim().isEmpty()) {
                System.err.println("Không được bỏ trống mã nhân viên");
            } else if (id_emp.trim().length() <= 5) {
                if (employee.find_ID(id_emp) == null) {
                    System.err.println("Mã nhân viên không tồn tại! Hãy nhập lại ");
                } else {
                    return id_emp;
                }
            } else {
                System.err.println("Mã nhân viên không được vượt quá 5 ký tự ");
            }
        } while (true);
    }

    public String inputCheckBillCode(Scanner scanner) {
        do {
            System.out.println("Nhâp vào mã bill code: ");
            String bill_code_out = scanner.nextLine();
            if (bill_code_out.trim().length() <= 10) {
                if (find_out_by_code(bill_code_out) == null) {
                    return bill_code_out;
                } else {
                    System.err.println("Bill code đã tồn tại ");
                }
            } else {
                System.err.println("Bill code phải nhỏ hơn 10 ký tự");
            }
        } while (true);
    }

    @Override
    public void displayDate() {
        System.out.printf("Mã phiếu: %d - Bill code : %s - Kiểu phiếu: %s - Mã nhân viên nhập: %s \n", this.biull_id_out, this.bill_code_out, (this.bill_type_out) ? "Phiếu nhâp" : "Phiếu Xuất", this.Emp_id_created_out);
        System.out.printf("Ngày tạo: %s - Mã nhân viên duyệt: %s - Ngày duyệt: %s - Trạng thái phiếu: %s \n", this.Created_out, this.Emp_id_auth_out, this.Auth_date_out, (this.Bill_Status_out == 0) ? "Phiếu tạo" : (this.Bill_Status_out == 1) ? "Phiếu hủy" : "Phiếu duyệt");

    }


}
