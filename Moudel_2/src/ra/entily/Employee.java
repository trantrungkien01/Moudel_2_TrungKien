package  ra.entily;

import ra.Interface.IEployee;
import ra.util.ConnectionDB;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Employee implements IEployee<Employee, String> {
    private String employeeId;
    private String employeeName;
    private Date birthOfDate;
    private String email;
    private String phone;
    private String address;
    private int employeeStatus;

    public Employee() {
    }

    public Employee(String employeeId, String employeeName, Date birthOfDate, String email, String phone, String address, int employeeStatus) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.birthOfDate = birthOfDate;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.employeeStatus = employeeStatus;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Date getBirthOfDate() {
        return birthOfDate;
    }

    public void setBirthOfDate(Date birthOfDate) {
        this.birthOfDate = birthOfDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getEmployeeStatus() {
        return employeeStatus;
    }

    public void setEmployeeStatus(int employeeStatus) {
        this.employeeStatus = employeeStatus;
    }

    @Override
    public List<Employee> getAll() {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        List<Employee> emphoyeeList = null;// trả về 1 cái list
        try {
            callSt = conn.prepareCall("{call get_All_Employee()}");
            ResultSet rs = callSt.executeQuery();
            emphoyeeList = new ArrayList<>();
            while (rs.next()) {
                Employee emphoyee = new Employee();
                emphoyee.setEmployeeId(rs.getString("emp_id"));
                emphoyee.setEmployeeName(rs.getString("emp_name"));
                emphoyee.setBirthOfDate(rs.getDate("birth_of_date"));
                emphoyee.setEmail(rs.getString("email"));
                emphoyee.setPhone(rs.getString("phone"));
                emphoyee.setAddress(rs.getString("address"));
                emphoyee.setEmployeeStatus(rs.getInt("emp_status"));
                emphoyeeList.add(emphoyee);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Có lỗi khi lấy dữ liệu");
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return emphoyeeList;
    }

    @Override
    public boolean create(Employee emphoyee) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        boolean result = false;
        try {
            callSt = conn.prepareCall("{call new_Employee(?,?,?,?,?,?,?)}");
            // stes giá trị
            callSt.setString(1, emphoyee.getEmployeeId());
            callSt.setString(2, emphoyee.getEmployeeName());
            callSt.setDate(3, new java.sql.Date(emphoyee.getBirthOfDate().getTime()));
            callSt.setString(4, emphoyee.getEmail());
            callSt.setString(5, emphoyee.getPhone());
            callSt.setString(6, emphoyee.getAddress());
            callSt.setInt(7, emphoyee.getEmployeeStatus());
            callSt.executeUpdate();
            result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return result;
    }


    @Override
    public Employee find_Name(String s) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        Employee employee = null;
        try {
            callSt = conn.prepareCall("{call find_name_employee(?)}");
            callSt.setString(1, s);
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                Employee emphoyee = new Employee();
                emphoyee.setEmployeeId(rs.getString("emp_id"));
                emphoyee.setEmployeeName(rs.getString("emp_name"));
                emphoyee.setBirthOfDate(rs.getDate("birth_of_date"));
                emphoyee.setEmail(rs.getString("email"));
                emphoyee.setPhone(rs.getString("phone"));
                emphoyee.setAddress(rs.getString("address"));
                emphoyee.setEmployeeStatus(rs.getInt("emp_status"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return employee;
    }

    public void updateEmployee_Status(Scanner scanner, Employee employee) {
        boolean checkOut = true;
        System.out.println("Nhập vào mã  nhân viên cần cập nhật: ");
        String updateE_ID_Status = scanner.nextLine();
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        try {
            if (find_ID(updateE_ID_Status) != null) {
                do {
                    System.out.println("Thông tin cập nhật bao gồm:");
                    System.out.println("0: Hoạt động");
                    System.out.println("1.Nghỉ chế độ");
                    System.out.println("2. Nghỉ việc");
                    System.out.println("Lựa chọn của bạn");
                    try {
                        int choice = Integer.parseInt(scanner.nextLine());
                        switch (choice) {
                            case 0:
                                this.employeeStatus = 0;
                                try {
                                    callSt = conn.prepareCall("{ call update_amp_status(?,?)}");
                                    callSt.setString(1, updateE_ID_Status);
                                    callSt.setInt(2, this.employeeStatus);
                                    callSt.executeUpdate();
                                    callSt = conn.prepareCall("{call update_amp_status_true(?)}");
                                    callSt.setString(1, updateE_ID_Status);
                                    callSt.executeUpdate();
                                    System.err.println("Đã cập nhật thành công ");
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                } finally {
                                    ConnectionDB.closeConnection(conn);
                                }
                                checkOut = false;
                                break;
                            case 1:
                                this.employeeStatus = 1;
                                try {
                                    callSt = conn.prepareCall("{ call update_amp_status(?,?)}");
                                    callSt.setString(1, updateE_ID_Status);
                                    callSt.setInt(2, this.employeeStatus);
                                    callSt.executeUpdate();
                                    callSt = conn.prepareCall("{call update_amp_status_false(?)}");
                                    callSt.setString(1, updateE_ID_Status);
                                    callSt.executeUpdate();
                                    System.err.println("Đã cập nhật thành công ");
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                } finally {
                                    ConnectionDB.closeConnection(conn);
                                }
                                checkOut = false;
                                break;
                            case 2:
                                this.employeeStatus = 2;
                                try {
                                    callSt = conn.prepareCall("{ call update_amp_status(?,?)}");
                                    callSt.setString(1, updateE_ID_Status);
                                    callSt.setInt(2, this.employeeStatus);
                                    callSt.executeUpdate();
                                    callSt = conn.prepareCall("{call update_amp_status_false(?)}");
                                    callSt.setString(1, updateE_ID_Status);
                                    callSt.executeUpdate();
                                    System.err.println("Đã cập nhật thành công");
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                } finally {
                                    ConnectionDB.closeConnection(conn);
                                }
                                checkOut = false;
                                break;
                            default:
                                System.err.println("Hãy lựa chọn tủ 1 -> 3");
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Hãy nhập vào số nguyên");
                    }
                } while (checkOut);
            } else {
                System.err.println("Không tìm thấy mã nhân viên này");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public Employee find_ID(String s) {
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
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return employee;
    }

    @Override
    public void inputData(Scanner scanner) {
        this.employeeId = checkId(scanner);
        this.employeeName = checkName(scanner);
        this.birthOfDate = checkDate(scanner);
        this.email = checkEmail(scanner);
        this.phone = checkSDT(scanner);
        this.address = checkAddress(scanner);
        this.employeeStatus = checkEmployeeStatus(scanner);
    }

    public String checkId(Scanner scanner) {
        do {
            System.out.println("Nhập vào ID nhân viên (5 ký tự): ");
            String employeeID = scanner.nextLine();
            if (employeeID.trim().length() == 5) {
                if (find_ID(employeeID) == null) {
                    return employeeID;
                } else {
                    System.err.println("Mã đã tồn tại");
                }
            } else {
                System.err.println("Mã nhân viên bao gồm 5 ký tự");
            }
        } while (true);
    }

    public String checkEmail(Scanner scanner) {
        do {
            System.out.println("Nhập vào email: ");
            String emailEmployee = scanner.nextLine();
            String regex = "[a-zA-Z0-9._]+@gmail.com";
            boolean result = Pattern.matches(regex, emailEmployee);
            if (result) {
                return emailEmployee;
            } else {
                System.err.println("Không đúng định dạng email");
            }
        } while (true);
    }


    public String checkName(Scanner scanner) {
        do {
            System.out.println("Nhập vào tên nhân viên: ");
            String nameEmployee = scanner.nextLine();
            if (nameEmployee.trim().isEmpty()) {
                System.err.println("Không được để trống tên nhân viên");
            } else {
                if (find_Name(nameEmployee) == null) {
                    return nameEmployee;
                } else {
                    System.err.println("Tên này đã tồn tại ! Hãy nhập vào tên khác ");
                }
            }
        } while (true);
    }

    public String checkAddress(Scanner scanner) {
        do {
            System.out.println("Nhập vào địa chỉ của nhân viên: ");
            String employeeAddress = scanner.nextLine();
            if (employeeAddress.trim().isEmpty()) {
                System.err.println("Không được để trống địa chỉ của nhân viên ");
            } else {
                return employeeAddress;
            }
        } while (true);
    }


    public int checkEmployeeStatus(Scanner scanner) {
        do {
            System.out.println("Nhập vào trạng thái nhân viên: ");
            try {
                int status = Integer.parseInt(scanner.nextLine());
                if (status == 0 || status == 1 | status == 2) {
                    return status;
                } else {
                    System.err.println("Chỉ nhận giá trị 0,1,2");
                }
            } catch (NumberFormatException e) {
                System.err.println("Hãy nhập vào số nguyên");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (true);
    }

    public String checkSDT(Scanner scanner) {
        do {
            System.out.println("Nhập vào sdt: ");
            String sdt = scanner.nextLine();
            String regax = "0[0-9]{9}";
            boolean result = Pattern.matches(regax, sdt);
            if (result == true) {
                return sdt;
            } else {
                System.err.println("Chưa đúng dạng số điện thoại");
            }
        } while (true);
    }

    public Date checkDate(Scanner scanner) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDay = null;
        boolean checkOut = true;
        do {
            System.out.println("Nhập vào ngày sình(yyyy-MM-dd): ");
            String birthDayStr = scanner.nextLine();
            try {
                birthDay = dateFormat.parse(birthDayStr);
                checkOut = false;
            } catch (ParseException e) {
                System.err.println("Lỗi định dạng yyyy-MM-dd");
                e.printStackTrace();
            }
        } while (checkOut);
        return birthDay;
    }

    public void updateEmployee(Scanner scanner, Employee employee) {
        boolean checkOut = true;
        System.out.println("Nhập vào mã  nhân viên cần cập nhật: ");
        String updateE_ID = scanner.nextLine();
        if (updateE_ID.trim().isEmpty()) {
            System.err.println("Không đươc để trống mã nhân viên");
        } else if (find_ID(updateE_ID) != null) {
            do {
                Connection conn = ConnectionDB.openConnection();
                CallableStatement callSt = null;
                System.out.println("************* MENU UPDATE EMPLOYEE*****************");
                System.out.println("1. Cập nhật lại tên mới");
                System.out.println("2. Cập nhật lại ngày sinh");
                System.out.println("3. Cập nhật lại email");
                System.out.println("4. Cập nhật lại số điện thoại");
                System.out.println("5. Cập nhật lại địa chỉ của nhân viên");
                System.out.println("6. Thoát cập nhật");
                System.out.println("Lựa chọn của bạn: ");
                try {
                    int choice = Integer.parseInt(scanner.nextLine());
                    switch (choice) {
                        case 1:
                            this.employeeName = checkName(scanner);
                            callSt = conn.prepareCall("{call update_nameE(?,?)}");
                            callSt.setString(1, updateE_ID);
                            callSt.setString(2, employee.getEmployeeName());
                            callSt.executeUpdate();
                            System.err.println("Đã cập nhật thành công");
                            break;
                        case 2:
                            this.birthOfDate = checkDate(scanner);
                            callSt = conn.prepareCall("{call update_Date_E(?,?)}");
                            callSt.setString(1, updateE_ID);
                            java.sql.Date sqlDate = new java.sql.Date(employee.getBirthOfDate().getTime());
                            callSt.setDate(2, sqlDate);
                            callSt.executeUpdate();
                            System.err.println("Đã cập nhật thành công");
                            break;
                        case 3:
                            this.email = checkEmail(scanner);
                            callSt = conn.prepareCall("{call update_emailE(?,?)}");
                            callSt.setString(1, updateE_ID);
                            callSt.setString(2, employee.getEmail());
                            callSt.executeUpdate();
                            System.err.println("Đã cập nhật thành công");
                            break;
                        case 4:
                            this.phone = checkEmail(scanner);
                            callSt = conn.prepareCall("{call update_phoneE(?,?)}");
                            callSt.setString(1, updateE_ID);
                            callSt.setString(2, employee.getPhone());
                            callSt.executeUpdate();
                            System.err.println("Đã cập nhật thành công");
                            break;
                        case 5:
                            this.address = checkEmail(scanner);
                            callSt = conn.prepareCall("{call update_Address(?,?)}");
                            callSt.setString(1, updateE_ID);
                            callSt.setString(2, employee.getAddress());
                            callSt.executeUpdate();
                            System.err.println("Đã cập nhật thành công");
                            break;
                        case 6:
                            checkOut = false;
                            break;
                        default:
                            System.err.println("Hãy lựa chọn từ 1-> 6");
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
            System.err.println("Mã nhân viên không tồn tại!");
        }
    }
    public List<Employee> find_NameEmp(String s) {
        Connection connection = ConnectionDB.openConnection();
        CallableStatement callst = null;
        List<Employee> employeeList = new ArrayList<>();
        try {
            callst = connection.prepareCall("{call find_id_employee(?)}");
            callst.setString(1, s);
            ResultSet rs = callst.executeQuery();
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setEmployeeId(rs.getString("emp_id"));
                employee.setEmployeeName(rs.getString("emp_name"));
                employee.setBirthOfDate(rs.getDate("birth_of_date"));
                employee.setEmail(rs.getString("email"));
                employee.setPhone(rs.getString("phone"));
                employee.setAddress(rs.getString("address"));
                employee.setEmployeeStatus(rs.getInt("emp_status"));
                employeeList.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(connection);
        }
        return employeeList;
    }

    @Override
    public void displayDate() {
        System.out.printf("Mã nhân viên: %s - Tên nhân viên: %s  - Ngày sinh: %s \n", this.employeeId, this.employeeName, this.birthOfDate);
        System.out.printf("Email: %s - SĐT: %s - Địa chỉ: %s - Trạng thái: %s \n", this.email, this.phone, this.address, (this.employeeStatus == 0) ? "Hoạt động" : (this.employeeStatus == 1) ? "Nghỉ chế độ" : "Nghỉ việc");
    }
}
