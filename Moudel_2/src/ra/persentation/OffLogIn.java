package ra.persentation;

import com.sun.jdi.connect.Connector;
import ra.Bussiness.User;
import ra.entily.*;
import ra.util.ConnectionDB;

import javax.print.attribute.standard.PagesPerMinute;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class OffLogIn {

    public static void admin(Scanner scanner) {
        boolean checkOut = true;
        do {
            System.out.println("************* MENU ADMIN ***********");
            System.out.println("1. Quản lý sản phẩm");
            System.out.println("2. Quản lý nhân viên");
            System.out.println("3. Quản lý tài khoản");
            System.out.println("4. Quản lý phiếu nhập");
            System.out.println("5. Quản lý phiếu xuất");
            System.out.println("6. Quản lý báo cáo");
            System.out.println("7. Thoát");
            System.out.println("Nhập vào lựa chọn của bạn:  ");
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        Ql_product(scanner, new Product());
                        break;
                    case 2:
                        Ql_NhanVien();
                        break;
                    case 3:
                        Ql_Account(scanner, new Account());
                        break;
                    case 4:
                        Ql_PhieuNhap(scanner, new Bill(), new Bill_Detail());
                        break;
                    case 5:
                        Ql_PhieuXuat(scanner, new BillOut(), new Bill_Detail());
                        break;
                    case 6:
                        Report(scanner);
                        break;
                    case 7:
                        User.writeDataToFile(null);
                        System.err.println("Đang xuất thành công");
                        //   User.login(scanner);
                        System.exit(0);
                        break;

                }
            } catch (Exception e) {
                System.err.println("Lỗi khi nhập vào lựa chọn");
            }
        } while (checkOut);
    }

    public static void Report(Scanner scanner) {
        boolean checkOut = true;
        do {
            System.out.println("************ REPORT MANAGEMENT **********");
            System.out.println("1. Thống kê chi phí theo ngày, tháng, năm");
            System.out.println("2. Thống kê chi phí theo khoảng thời gian");
            System.out.println("3. Thống kê doanh thu theo ngày, tháng, năm");
            System.out.println("4. Thống kê doanh thu theo khoảng thời gian");
            System.out.println("5. Thống kê số nhân viên theo từng trạng thái");
            System.out.println("6. Thống kê sản phẩm nhập nhiều nhất trong khoảng thời gian");
            System.out.println("7. Thống kê sản phẩm nhập ít nhất trong khoảng thời gian");
            System.out.println("8. Thống kê sản phẩm xuất nhiều nhất trong khoảng thời gian");
            System.out.println("9. Thống kê sản phẩm xuất ít nhất trong khoảng thời gian");
            System.out.println("10.Thoát");
            System.out.println("Lựa chọn của bạn: ");
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    case 8:
                        break;
                    case 9:
                        break;
                    case 10:
                        checkOut = false;
                        System.err.println("Thoát thống kê");
                        break;
                    default:
                        System.err.println("Hãy lựa chọn từ 1 - > 10");
                }
            } catch (NumberFormatException e) {
                System.err.println("Hãy nhập vào số nguyên");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (checkOut);
    }

    public static void Ql_PhieuXuat(Scanner scanner, BillOut billOut, Bill_Detail billDetail) {
        boolean chekcOut = true;
        do {
            System.out.println("************BILL MANAGEMENT***********");
            System.out.println("1. Danh sách phiếu xuất");
            System.out.println("2. Tạo phiếu xuất");
            System.out.println("3. Cập nhật thông tin phiếu xuất");
            System.out.println("4.Chi tiết phiếu xuất");
            System.out.println("5. Duyệt phiếu xuất");
            System.out.println("6. Tìm kiếm phiếu xuất");
            System.out.println("7. Thoát");
            System.out.println("Lựa chọn của bạn: ");
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        List<BillOut> billOutsList = billOut.getAllOut();
                        if (!billOutsList.isEmpty()) {
                            for (BillOut bo : billOutsList) {
                                bo.displayDate();
                            }
                        } else {
                            System.err.println("Hiện tại chưa có phiếu xuất nào");
                        }
                        break;
                    case 2:
                        billOut.inputData(scanner);
                        boolean check = billOut.create(billOut);
                        if (check) {
                            System.err.println("Đã thêm thành công");
                            List<BillOut> billOutsList1 = billOut.getAllOut();
                            for (BillOut bo : billOutsList1) {
                                bo.displayDate();
                            }
                        } else {
                            System.err.println("Thêm thất bại");
                        }
                        break;
                    case 3:
                        billOut.update_infor_out(scanner, new BillOut());
                        break;
                    case 4:
                        System.out.println("Nhập vào mã phiếu cần xem chi tiết: ");
                        int idFind = Integer.parseInt(scanner.nextLine());
                        List<Bill> billLis5 = billOut.find_bill_id(idFind);
                        if (!billLis5.isEmpty()) {
                            for (Bill v : billLis5) {
                                v.displayDate();
                            }
                        } else {
                            System.err.println("Không tìm thấy mã hóa đơn : " + idFind);
                        }
                        List<Bill_Detail> billDetailList = billDetail.find_id_bill_detail(idFind);
                        if (!billDetailList.isEmpty()) {
                            for (Bill_Detail bd : billDetailList) {
                                bd.displayDate();
                            }
                        } else {
                            System.err.println("Không tìm thấy mã hóa đơn :" + idFind);
                        }
                        break;
                    case 5:
                        billOut.update_status_out(scanner, new Bill_Detail(), new BillOut());
                        break;
                    case 6:
                        break;
                    case 7:
                        chekcOut = false;
                        System.err.println("Đã thoát");
                        break;
                }
            } catch (NumberFormatException e) {
                System.err.println("Hãy nhập vào số nguyên");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (chekcOut);
    }

    public static void Ql_product(Scanner scanner, Product product) {
        boolean checkOut = true;
        do {
            System.out.println("***********PRODUCT MANAGEMENT***********");
            System.out.println("1. Danh sách sản phẩm");
            System.out.println("2. Thêm mới sản phẩm");
            System.out.println("3. Cập nhật sản phẩm");
            System.out.println("4. Tìm kiếm sản phẩm");
            System.out.println("5. Cập nhật trạng thái sản phẩm");
            System.out.println("6. Thoát");
            System.out.println("Lựa chọn của bạn: ");
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        List<Product> productList = product.getAll();
                        if (!productList.isEmpty()) {
                            productList.stream().forEach(product1 -> product1.displayData());
                        } else {
                            System.err.println("Danh sách sản phẩm hiện tại chưa có sản phẩm nào");
                        }
                        break;
                    case 2:
                        Product product1 = new Product();
                        product1.inputData(scanner);
                        boolean resultCreate = product1.create(product1);
                        if (resultCreate) {
                            System.err.println("Thêm thành công");
                        } else {
                            System.err.println("Thêm thất bại");
                        }
                        break;
                    case 3:
                        product.updateProduct(scanner, product);
                        break;
                    case 4:
                        System.out.println("Nhập vào tên sản phẩm cần tìm:  ");
                        String findName = scanner.nextLine();
                        List<Product> productList1 = product.find_Name(findName.trim());
                        if (!productList1.isEmpty()) {// nếu danh sách rỗng thì in ra , ngược lại thì không
                            for (Product product2 : productList1) {
                                product2.displayData();
                            }
                        } else {
                            System.err.println("Không tim thấy với tên sản phẩm: " + findName);
                        }
                        break;
                    case 5:
                        product.update_product(scanner);
                        break;
                    case 6:
                        checkOut = false;
                        break;
                    default:
                        System.err.println("hãy lựa chọn từ  1- > 6 ");
                }
            } catch (Exception e) {
                System.err.println("Có lỗi khi nhập vào lựa chon");
            }
        } while (checkOut);
    }

    public static void Ql_NhanVien() {
        Scanner scanner = new Scanner(System.in);
        Employee e = new Employee();
        boolean checkOut = true;
        do {
            System.out.println(" ************** MENU EMPLOYEE ************");
            System.out.println("1. Danh sách nhân viên");
            System.out.println("2. Thêm mới nhân viên");
            System.out.println("3. Cập nhật thông tin nhân viên");
            System.out.println("4. Cập nhật trạng thái nhân viên");
            System.out.println("5. Tìm kiếm nhân viên");
            System.out.println("6. Thoát");
            System.out.println("Nhập vào lựa chọn của bạn: ");
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        List<Employee> emphoyeeList = e.getAll();
                        if (!emphoyeeList.isEmpty()) {
                            emphoyeeList.stream().forEach(emphoyee -> emphoyee.displayDate());
                        } else {
                            System.err.println("Hiện tại chưa có nhân viên nào");
                        }
                        break;
                    case 2:
                        Employee employee = new Employee();
                        employee.inputData(scanner);
                        boolean resultCreate = employee.create(employee);
                        if (resultCreate) {
                            System.err.println("Đã thêm thành công");
                            List<Employee> emphoyeeList2 = e.getAll();
                            if (!emphoyeeList2.isEmpty()) {
                                emphoyeeList2.stream().forEach(emphoyee -> emphoyee.displayDate());
                            } else {
                                System.err.println("Hiện tại chưa có nhân viên nào");
                            }
                        } else {
                            System.err.println("Lỗi khi thêm");
                        }
                        break;
                    case 3:
                        e.updateEmployee(scanner, e);
                        break;
                    case 4:
                        e.updateEmployee_Status(scanner, e);
                        break;
                    case 5:
                        System.out.println("Nhập vào mã nhân viên cần tìm kiếm: ");
                        String name = scanner.nextLine();
                        List<Employee> employeeList = e.find_NameEmp(name.trim());
                        if (name.trim().isEmpty()) {
                            System.err.println("Không đươc bỏ trống tên tìm kiếm");
                        } else if (!employeeList.isEmpty()) {
                            for (Employee employee1 : employeeList) {
                                employee1.displayDate();
                            }
                        } else {
                            System.err.println("Không tồn tại mã này");
                        }
                        break;
                    case 6:
                        checkOut = false;
                        break;
                    default:
                        System.err.println("Hãy lựa chọn từ 1 -> 6 ");
                }
            } catch (NumberFormatException ex) {
                System.err.println("Hãy nhập vào số nguyên");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } while (checkOut);

    }

    public static void Ql_Account(Scanner scanner, Account account) {
        boolean checkOut = true;
        do {
            System.out.println("********** ACCOUNT MANAGEMENT*************** ");
            System.out.println("1. Danh sách tài khoản");
            System.out.println("2. Tạo tài khoản mới");
            System.out.println("3. Cập nhật tài khoản");
            System.out.println("4. Tìm kiếm tài khoản");
            System.out.println("5. Thoát");
            System.out.println("Nhập vào lựa chọn của bạn: ");
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        List<Account> accountList = account.getAll();
                        accountList.stream().forEach(account1 -> account1.displayDate());
                        break;
                    case 2:
                        account.inputData(scanner);
                        boolean result = account.create(account);
                        if (result) {
                            System.err.println("Đã thêm thành công");
                        } else {
                            System.err.println("Lỗi khi thêm tài khoản");
                        }
                        break;
                    case 3:
                        account.update_Account(scanner, account);
                        break;
                    case 4:
                        account.updateStatus(scanner, account);
                    case 5:
                        checkOut = false;
                        break;
                    default:
                        System.err.println("Hãy lựa chọn từ 1 -> 5 ");
                }
            } catch (NumberFormatException e) {
                System.err.println("Hãy nhập vào kiểu số nguyên");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (checkOut);
    }

    public static void Ql_PhieuNhap(Scanner scanner, Bill bill, Bill_Detail billDetail) {
        boolean checkOut = true;
        do {
            System.out.println("*************RECEIPT MANAGEMENT**********");
            System.out.println("1. Danh sách phiếu nhập");
            System.out.println("2. Tạo phiếu nhập");
            System.out.println("3. Cập nhật thông tin phiếu nhập");
            System.out.println("4. Chi tiết phiếu nhập");
            System.out.println("5. Duyệt phiếu nhập");
            System.out.println("6. Tìm kiếm phiếu nhập");
            System.out.println("7. Thoát");
            System.out.println("Nhập vào lựa chọn của bạn: ");
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        List<Bill> billList = bill.getAll();
                        billList.stream().forEach(bill1 -> bill1.displayDate());
                        break;
                    case 2:
                        bill.inputData(scanner);
                        boolean result = bill.create(bill);
                        if (result) {
                            System.err.println("Đã cập nhật thành công");
                            List<Bill> billList1 = bill.getAll();
                            billList1.stream().forEach(bill1 -> bill1.displayDate());
                        } else {
                            System.err.println("Cập nhật thất bại");
                        }
                        break;
                    case 3:
                        bill.update_bill(scanner);
                        break;
                    case 4:
                        System.out.println("Nhập vào mã phiếu cần xem chi tiết: ");
                        int idFind = Integer.parseInt(scanner.nextLine());
                        List<Bill> billLis5 = bill.find_bill_id(idFind);
                        if (!billLis5.isEmpty()) {
                            for (Bill v : billLis5) {
                                v.displayDate();
                            }
                        } else {
                            System.err.println("Không tìm thấy mã hóa đơn : " + idFind);
                        }
                        List<Bill_Detail> billDetailList = billDetail.find_id_bill_detail(idFind);
                        if (!billDetailList.isEmpty()) {
                            for (Bill_Detail bd : billDetailList) {
                                bd.displayDate();
                            }
                        } else {
                            System.err.println("Không tìm thấy mã hóa đơn :" + idFind);
                        }
                        break;
                    case 5:
                        bill.update_status(scanner, new Bill_Detail());
                        break;
                    case 6:
                        List<Bill> billList1 = bill.find_bill_code_list(scanner, new Bill());
                        if (!billList1.isEmpty()) {
                            for (Bill b : billList1) {
                                b.displayDate();
                            }
                        } else {
                            System.err.println("Không tồn tại mã code đấy");
                        }
                        break;
                    case 7:
                        checkOut = false;
                        break;
                    default:
                        System.err.println("Hãy lựa chọn từ 1 - > 7");
                }
            } catch (NumberFormatException e) {
                System.err.println("Hãy nhập vào số nguyên: ");
            }
        } while (checkOut);
    }

    public static void user(Scanner scanner) {
        Bill bill = new Bill();
        BillOut billOut = new BillOut();
        boolean checkOut = true;
        do {
            System.out.println("************* WAREHOUSE MANAGEMENT*********");
            System.out.println("1. Danh sách phiếu nhập theo trạng thái");
            System.out.println("2. Tạo phiếu nhập");
            System.out.println("3. Cập nhật phiếu nhập");
            System.out.println("4. Tìm kiếm phiếu nhập");
            System.out.println("5. Danh sách phiếu xuất theo trạng thái");
            System.out.println("6. Tạo phiếu xuất");
            System.out.println("7. Cập nhật phiếu xuất");
            System.out.println("8. Tìm kiếm phiếu xuất");
            System.out.println("9. Thoát");
            System.out.println("lựa chọn của bạn: ");
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        List<Bill> billList = bill.getAll();
                        billList.stream().forEach(bill1 -> bill1.displayDate());
                        break;
                    case 2:
                        bill.inputUser(scanner, new User());
                        boolean result = bill.create(bill);
                        if (result) {
                            System.err.println("Đã cập nhật thành công");
                            List<Bill> billList1 = bill.getAll();
                            billList1.stream().forEach(bill1 -> bill1.displayDate());
                        } else {
                            System.err.println("Cập nhật thất bại");
                        }
                        break;
                    case 3:
                        bill.update_bill(scanner);
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    case 8:
                        break;
                    case 9:
                        User.writeDataToFile(null);
                        System.err.println("Đang xuất thành công");
                        User.login(scanner);
                        break;
                    case 0:
                        checkOut = false;
                }
            } catch (Exception e) {
                System.err.println("Lỗi khi nhập vào lựa chọn");
            }
        } while (checkOut);
    }
}
