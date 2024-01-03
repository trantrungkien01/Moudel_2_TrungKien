package ra.entily;


import ra.Interface.IAccount;
import ra.util.ConnectionDB;


import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Account implements Serializable, IAccount<Account, Integer> {
   // private static final long serialVersionUID = 456282848112217961L;
   private static final long serialVersionUID = -5845776997668832401L;

    private int accId;
    private String userName;
    private String password;
    private boolean permission;
    private String empId;
    private boolean accStatus;

    public Account() {
    }

    public Account(int accId, String userName, String password, boolean permission, String empId, boolean accStatus) {
        this.accId = accId;
        this.userName = userName;
        this.password = password;
        this.permission = permission;
        this.empId = empId;
        this.accStatus = accStatus;
    }

    public int getAccId() {
        return accId;
    }

    public void setAccId(int accId) {
        this.accId = accId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isPermission() {
        return permission;
    }

    public void setPermission(boolean permission) {
        this.permission = permission;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public boolean isAccStatus() {
        return accStatus;
    }

    public void setAccStatus(boolean accStatus) {
        this.accStatus = accStatus;
    }

    @Override
    public List<Account> getAll() {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        List<Account> accountList = null;// trả về 1 cái list
        try {
            callSt = conn.prepareCall("{call getAll_Account()}");
            ResultSet rs = callSt.executeQuery();
            accountList = new ArrayList<>();
            while (rs.next()) {
                Account account = new Account();
                account.setAccId(rs.getInt("acc_id"));
                account.setUserName(rs.getString("use_name"));
                account.setPassword(rs.getString("password"));
                account.setPermission(rs.getBoolean("permission"));
                account.setEmpId(rs.getString("emp_id"));
                account.setAccStatus(rs.getBoolean("acc_status"));
                accountList.add(account);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Có lỗi khi lấy dữ liệu");
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return accountList;
    }

    @Override
    public boolean create(Account account) {
        Connection con = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        boolean result = false;
        try {
            con.setAutoCommit(false);
            callSt = con.prepareCall("{call new_Accout(?,?,?,?,?,?)}");
            callSt.setInt(1, account.getAccId());
            callSt.setString(2, account.getUserName());
            callSt.setString(3, account.getPassword());
            callSt.setBoolean(4, account.isPermission());
            callSt.setString(5, account.getEmpId());
            callSt.setBoolean(6, account.isAccStatus());
            callSt.executeUpdate();
            con.commit();
            result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(con);
        }
        return result;
    }

    public List<Account> find_NameAcount(String s) {
        Connection connection = ConnectionDB.openConnection();
        CallableStatement callst = null;
        List<Account> accountList = new ArrayList<>();
        try {
            callst = connection.prepareCall("{call find_by_nameAccount(?)}");
            callst.setString(1, s);
            ResultSet rs = callst.executeQuery();
            while (rs.next()) {
                Account account = new Account();
                account.setAccId(rs.getInt("acc_id"));
                account.setUserName(rs.getString("use_name"));
                account.setPassword(rs.getString("password"));
                account.setPermission(rs.getBoolean("permission"));
                account.setEmpId(rs.getString("emp_id"));
                account.setAccStatus(rs.getBoolean("acc_status"));
                accountList.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(connection);
        }
        return accountList;
    }

    public Account find_Name(String s) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        Account account = null;
        try {
            callSt = conn.prepareCall("{call find_by_nameAccount(?)}");
            callSt.setString(1, s);
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                account = new Account();
                account.setAccId(rs.getInt("acc_id"));
                account.setUserName(rs.getString("use_name"));
                account.setPassword(rs.getString("password"));
                account.setPermission(rs.getBoolean("permission"));
                account.setEmpId(rs.getString("emp_id"));
                account.setAccStatus(rs.getBoolean("acc_status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return account;
    }

    public void update_Account(Scanner scanner, Account account) {
        System.out.println("Nhập tên tài khoản cần cập nhât: ");
        String nameAcc = scanner.nextLine();
        if (find_Name(nameAcc) != null) {
            boolean checkOut = true;
            do {
                Connection conn = ConnectionDB.openConnection();
                CallableStatement callSt = null;
                System.out.println("********** MENU UPDATE ACCOUNT***************");
                System.out.println("1. Cập nhật lại mật khẩu");
                System.out.println("2. Cập nhật quền truy cập");
                System.out.println("3. Cập nhật lại mã nhân viên");
                System.out.println("4. Cập nhật lại trạng thái cho tài khoản");
                System.out.println("5. Thoát cập nhật");
                System.out.println("Lựa chọn của bạn: ");
                try {
                    int choice = Integer.parseInt(scanner.nextLine());
                    switch (choice) {
                        case 1:
                            this.password = checkPassWord(scanner);
                            callSt = conn.prepareCall("{call update_pass_by_name(?,?)}");
                            callSt.setString(1, nameAcc);
                            callSt.setString(2, account.getPassword());
                            callSt.executeUpdate();
                            System.err.println("Cập nhật thành công");
                            break;
                        case 2:
                            this.permission = checkPermission(scanner);
                            callSt = conn.prepareCall("{call update_permission(?,?)}");
                            callSt.setString(1, nameAcc);
                            callSt.setBoolean(2, account.isPermission());
                            callSt.executeUpdate();
                            System.err.println("Cập nhật thành công");
                            break;
                        case 3:
                            this.empId = check_Emp_ID(scanner);
                            callSt = conn.prepareCall("{call update_Emp_Id(?,?)}");
                            callSt.setString(1, nameAcc);
                            callSt.setString(2, account.getEmpId());
                            callSt.executeUpdate();
                            System.err.println("Cập nhật thành công");
                            break;
                        case 4:
                            if (isAccStatus()) {
                                callSt = conn.prepareCall("{call update_accStatus_false(?)}");
                                callSt.setString(1, nameAcc);
                                callSt.executeUpdate();
                                System.err.println("Đã dừng hoạt động tài khoản");
                                System.err.println("Cập nhật thành công");
                            } else {
                                callSt = conn.prepareCall("{call update_accStatus_true(?)}");
                                callSt.setString(1, nameAcc);
                                callSt.executeUpdate();
                                System.err.println("Đã cập nhật hoạt động");
                                System.err.println("Cập nhật thành công");
                            }
                            break;
                        case 5:
                            checkOut = false;
                            System.err.println("Thoát cập nhật");
                            break;
                        default:
                            System.err.println("Hãy lựa chọn từ 1 -> 5");
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Hãy nhập vào số nguyên!! Hãy nhập lại");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    ConnectionDB.closeConnection(conn);
                }
            } while (checkOut);
        } else {
            System.err.println("Tên tài khoản không tồn tạo");
        }
    }

    public void updateStatus(Scanner scanner, Account account) {
        System.out.println("Nhập vào tên tài khoản cần tìm: ");
        String find_nameAcc = scanner.nextLine();
        List<Account> accountList1 = account.find_NameAcount(find_nameAcc.trim());
        if (!accountList1.isEmpty()) {// nếu danh sách rỗng thì in ra , ngược lại thì không\
            boolean checkout = true;
            do {
                for (Account account1 : accountList1) {
                    account1.displayDate();
                    Connection conn = ConnectionDB.openConnection();
                    CallableStatement callSt = null;
                    System.out.println("Bạn có muốn cập nhật trạng thái cho tài khoản này không? ");
                    System.out.println("1. Có");
                    System.out.println("2. Thoát");
                    System.out.println("Lựa chọn của bạn: ");
                    try {
                        int luachon = Integer.parseInt(scanner.nextLine());
                        switch (luachon) {
                            case 1:
                                System.out.println("Trạng thái tài khoản đang là: " + ((account1.accStatus) ? "Đang  hoạt động" : "Đã dừng hoạt động"));
                                System.out.println("Bạn muốn cập nhật cho trạng thái cho tài khoản bạn muộn");
                                System.out.println("1 : Hoạt động");
                                System.out.println("2. Dừng hoạt động");
                                System.out.println("Lựa chọn của bạn: ");
                                try {
                                    int noname = Integer.parseInt(scanner.nextLine());
                                    switch (noname) {
                                        case 1:
                                            callSt = conn.prepareCall("{call update_accStatus_true(?)}");
                                            callSt.setString(1, find_nameAcc);
                                            callSt.executeUpdate();
                                            System.err.println("Đã cập nhật hoạt động");
                                            System.err.println("Cập nhật thành công");
                                            checkout = false;
                                            break;
                                        case 2:
                                            callSt = conn.prepareCall("{call update_accStatus_false(?)}");
                                            callSt.setString(1, find_nameAcc);
                                            callSt.executeUpdate();
                                            System.err.println("Đã dừng hoạt động tài khoản");
                                            System.err.println("Cập nhật thành công");
                                            checkout = false;
                                            break;
                                        default:
                                            System.err.println("Hãy lựa chọn từ 1 - > 2");
                                    }
                                } catch (NumberFormatException e) {
                                    System.err.println("Hãy nhập vào số nguyên");
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                } finally {
                                    ConnectionDB.closeConnection(conn);
                                }
                                break;
                            case 2:
                                System.err.println("Đã thoát ra");
                                ConnectionDB.closeConnection(conn);
                                break;
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Hãy nhập vào số nguyên");
                    }
                }
            } while (checkout);
        } else {
            System.err.println("Không tim thấy với tên sản phẩm: " + find_nameAcc);
        }
    }

    @Override
    public Account find_ID(Integer integer) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        Account account = null;
        try {
            callSt = conn.prepareCall("{call find_by_IdAccount(?)}");
            callSt.setInt(1, integer);
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                account = new Account();
                account.setAccId(rs.getInt("acc_id"));
                account.setUserName(rs.getString("use_name"));
                account.setPassword(rs.getString("password"));
                account.setPermission(rs.getBoolean("permission"));
                account.setEmpId(rs.getString("emp_id"));
                account.setAccStatus(rs.getBoolean("acc_status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return account;
    }

    public Employee find_Id_Emp(String s) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        Employee employee = null;
        try {
            callSt = conn.prepareCall("{call find_id_employee(?)}");
            callSt.setString(1, s);
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                employee = new Employee();
                employee.setEmployeeId(rs.getString("emp_id"));
                employee.setEmployeeName(rs.getString("emp_name"));
                employee.setBirthOfDate(rs.getDate("birth_of_date"));
                employee.setEmail(rs.getString("email"));
                employee.setPhone(rs.getString("phone"));
                employee.setAddress(rs.getString("address"));
                employee.setEmployeeStatus(rs.getInt("emp_status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employee;
    }

    @Override
    public void inputData(Scanner scanner) {
        this.accId = checkId_Account(scanner);
        this.userName = check_userName(scanner);
        this.password = checkPassWord(scanner);
        this.permission = checkPermission(scanner);
        this.empId = check_Emp_ID(scanner);
        this.accStatus = checkStatusAccount(scanner);
    }

    public int checkId_Account(Scanner scanner) {
        do {
            System.out.println("Nhập vào mã tài khoản: ");
            try {
                int id = Integer.parseInt(scanner.nextLine());
                if (find_ID(id) == null) {
                    return id;
                } else {
                    System.err.println("Mã tài khoản đã tồn tại");
                }
            } catch (NumberFormatException e) {
                System.err.println("Hãy nhập vào số nguyên");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (true);
    }

    public String check_userName(Scanner scanner) {
        do {
            System.out.println("Nhập vào tên đăng nhập tài khoản: ");
            String userNamee = scanner.nextLine();
            if (userNamee.isEmpty()) {
                System.err.println("Tên không được để trống!.Hãy nhập lại");
            } else {
                if (find_Name(userNamee) == null) {
                    return userNamee;
                } else {
                    System.err.println("Tên tài khoản này đã tồn tại! Hãy nhập lại");
                }
            }
        } while (true);
    }

    public String checkPassWord(Scanner scanner) {
        do {
            System.out.println("Nhập vào mật khẩu cho tài khoản: ");
            String pass = scanner.nextLine();
            if (pass.isEmpty()) {
                System.err.println("Mật khẩu tài khoản không được bỏ trống");
            } else {
                return pass;
            }
        } while (true);
    }

    public boolean checkPermission(Scanner scanner) {
        do {
            System.out.println("Nhập vào quyền truy cập cho tài khoản: ");
            String status = scanner.nextLine();
            if (status.trim().equals("true") || status.trim().equals("false")) {
                return Boolean.parseBoolean(status);
            } else {
                System.err.println("Chỉ nhận gias trị true hoặc false");
            }

        } while (true);
    }

    public String check_Emp_ID(Scanner scanner) {
        do {
            System.out.println("Nhập vào mã nhân viên: ");
            String emp_id = scanner.nextLine();
            if (emp_id.isEmpty()) {
                System.err.println("Không được để trống");
            } else if (find_Id_Emp(emp_id) != null) {
                return emp_id;
            } else {
                System.err.println("Mã nhân viên Không tồn tại ");
            }
        } while (true);
    }

    public boolean checkStatusAccount(Scanner scanner) {
        do {
            System.out.println("Nhập vào trạng thái của tài khoản: ");
            String statusAcc = scanner.nextLine();
            if (statusAcc.isEmpty()) {
                System.err.println("Không được để trống trạng thái của tài khoản");
            } else if (statusAcc.trim().equals("true") || statusAcc.trim().equals("false")) {
                return Boolean.parseBoolean(statusAcc);
            } else {
                System.err.println("Trạng thái tài khoản chỉ nhận giá trị true hoặc false");
            }
        } while (true);
    }

    @Override
    public void displayDate() {
        System.out.printf("Mã tài khoản: %s - Tên đăng nhâp: %s - Mật khẩu tài khoản: %s - Quyền truy cấp: %s \n", this.accId, this.userName, this.password, (this.permission) ? "Adim" : "User");
        System.out.printf("Mã nhân viên sử dụng: %s - Trạng thái của tài khoản: %s\n", this.empId, (this.accStatus) ? "Hoạt động" : "Dừng hoạt động");
    }
}