package ra.Interface;

import java.util.List;
import java.util.Scanner;

public interface IAccount<T, K> {
    // T là thực thể , K là kiểu dữ liệu của khóa chính
    List<T> getAll();

    // thêm mới 1 nhân viên
    boolean create(T t);


    T find_ID(K k);

    void inputData(Scanner scanner);

    void displayDate();
}