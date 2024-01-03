package ra.Interface;

import java.util.List;
import java.util.Scanner;

public interface IEployee<T, K> {
    // T kaf thực thể đối tuơng employee , k là khóa chính
    List<T> getAll();

    // thêm mới 1 nhân viên
    boolean create(T t);


    // 5. Tìm kiếm nhân viên theo tên
    T find_Name(K k);

    T find_ID(K k);

    void inputData(Scanner scanner);

    void displayDate();
}