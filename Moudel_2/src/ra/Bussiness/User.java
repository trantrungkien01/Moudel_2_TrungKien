package ra.Bussiness;

import ra.entily.Account;
import ra.persentation.OffLogIn;
import ra.util.ConnectionDB;
import java.io.*;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


public class User {
    public static Account logiOffAccount(String userName, String passWord) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        Account account = null;
        try {
            callSt = conn.prepareCall("{call logIn(?,?)}");
            callSt.setString(1, userName);
            callSt.setString(2, passWord);
            ResultSet rs = callSt.executeQuery();
            if (rs.next()) {
                account = new Account();
                account.setAccId(rs.getInt("acc_id"));
                account.setUserName(rs.getString("use_name"));
                account.setPassword(rs.getString("password"));
                account.setPermission(rs.getBoolean("permission"));
                account.setEmpId(rs.getString("emp_id"));
                account.setAccStatus(rs.getBoolean("acc_status"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (callSt != null) {
                try {
                    callSt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            ConnectionDB.closeConnection(conn);
        }
        return account;
    }

    // phương thức truy vấn để đăng nhập
    public static void login(Scanner scanner) {
        Account account = new Account();
        boolean checkOut = true;
        do {
            System.out.println("Nhập vào tên tài khoản: ");
            String useName = scanner.nextLine();
            System.out.println("Nhập vào mật khẩu: ");
            String pass = scanner.nextLine();
            /// gọi đến phương  thức lấy dữ liệu trong sql
            account = User.logiOffAccount(useName, pass);
            if (account == null) {// không tìm thấy
                System.err.println("Tên tài khoản mật khẩu không chính xác");
            } else if (!account.isAccStatus()) {// trạng thái đang bị khóa
                System.err.println("tài khoản tạm thời bị khóa");
            } else {// ghi lại thông tin của account
                User.writeDataToFile(account);
                if (account.isPermission()) {
                    OffLogIn.admin(scanner);
                    checkOut = false;
                } else {
                    OffLogIn.user(scanner);
                    checkOut = false;
                }
            }
        } while (checkOut);
    }

    public static Account readDataTOFile() {
        Account account = null;
        File file = new File("account.txt");
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            account = (Account) ois.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return account;
    }

    public static void writeDataToFile(Account account) {
        File file = new File("account.txt");
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(account);
            oos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
