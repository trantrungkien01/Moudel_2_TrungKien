import ra.Bussiness.User;
import ra.entily.Account;
import ra.entily.Account;
import ra.persentation.OffLogIn;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
       Account account = User.readDataTOFile();
        Scanner scanner = new Scanner(System.in);
        if (account != null) {
            if (account.isPermission()) {
                OffLogIn.admin(scanner);
            } else {
                OffLogIn.user(scanner);
            }
            User.login(scanner);
        } else {
            User.login(scanner);
            System.err.println("Không tìm thấy tài khoan");
        }
    }
}
