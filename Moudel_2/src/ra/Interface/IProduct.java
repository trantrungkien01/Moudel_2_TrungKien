package ra.Interface;

import java.util.List;
import java.util.Scanner;

public interface IProduct<T, K> {
    // T là đối tương, k là khóa chiính
    // 1 hiển thị tất cả đối tuượng trong sql
    List<T> getAll();

    // 2 thêm mới vào 1 đối tượng sql
    boolean create(T t);

    //3 cập nhật thông tin cho đối tượng sql
    boolean update(T t);
    // tìm kiếm san phẩm theo id sản phẩm
    T findByID(K k);
    // tìm kiếm sản phẩm theo tên sản phẩm
    T findByName(K k);


    boolean update_Status(K k);

    void inputData(Scanner scanner);

    void displayData();


}

