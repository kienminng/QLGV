package menu;

import business.PayrollManager;
import business.TeacherManager;
import model.Department;
import model.Payroll;
import model.Teacher;

import java.sql.Struct;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class TeacherMenu {
    TeacherManager teacherManager = new TeacherManager();
    PayrollManager payrollManager = new PayrollManager();
    Scanner sc = new Scanner(System.in);

    public void showMenu(){
        System.out.println("1.Danh sách giáo viên");
        System.out.println("2.Thêm giáo viên");
        System.out.println("3.Sửa thông tin giáo viên");
        System.out.println("4.Xoá thông tin giáo viên");
        System.out.println("5.Sắp xếp danh sách giáo viên");
        System.out.println("6.Tìm kiếm giáo viên lớn nhất/nhỏ nhất");
        System.out.println("7.Tìm kiếm danh sách giáo viên theo khoa ");
        System.out.println("0.Exit");
        System.out.println("Chọn mục mong muốn ");
        try {
            int index = Integer.parseInt(sc.nextLine());
            action(index);

        }  catch (NumberFormatException e) {
            System.out.println("Vui lòng nhập số hợp lệ!");
            showMenu();
        }
    }

    public void action(int index){
        switch (index) {
            case 1:
                getAll();
                break;
            case 2:
                addTeacherFromInput();
                break;
            case 3:
                editTeacher();
                showMenu();
                break;
            case 4:
                deleteTeacher();
                showMenu();
                break;
            case 5:
                sort();
                break;
            case 6:
                showFindMenu();
                break;
            case 7:
                findTeachersByDepartment();
                break;
            case 0:
                System.out.println("Thoát chương trình.");
                System.exit(0);
            default:
                System.out.println("Lựa chọn không hợp lệ! Vui lòng thử lại.");
                showMenu();
        }
    }

    public void getAll() {
        teacherManager.getAll();
        showMenu();
    }

    public void addTeacherFromInput() {
        try {
            //System.out.print("Nhập ID giáo viên: ");
            String teacherId = UUID.randomUUID().toString();

            System.out.print("Nhập tên giáo viên: ");
            String name = sc.nextLine();

            System.out.print("Nhập tuổi: ");
            int age = Integer.parseInt(sc.nextLine());
            String department;
            int dpi;
            while (true) {
                System.out.print("Nhập bộ phận: ");
                System.out.println("nếu bạn nhập một ký tự số tt không đúng chúng tôi sẽ mặc định chọn công nghệ");
                System.out.println("1.Công nghệ");
                System.out.println("2.Quản trị");
                System.out.println("3.Tài chính");
                System.out.println("4.Ngoại ngữ");
                try {
                    dpi = Integer.parseInt(sc.nextLine());
                    break;
                } catch (Exception e){
                    dpi = 1;
                    break;
                }
            }

            switch (dpi) {
                case 1:
                    department = Department.CN.name();
                    break;
                case 2:
                    department = Department.QT.name();
                    break;
                case 3:
                    department = Department.TC.name();
                    break;
                case 4:
                    department = Department.NN.name();
                    break;
                default:
                    department = Department.CN.name();
            }

            System.out.print("Nhập chức vụ: ");
            String position = sc.nextLine();

            System.out.print("Nhập số giờ làm việc: ");
            int workHours = Integer.parseInt(sc.nextLine());
            Payroll payroll = InputSalaryRank();
            while (payroll == null) {
                System.out.println("Không tìm thấy thông tin của mức lương");
                System.out.println("1.Thoát");
                System.out.println("2.Nhập lại");
                int choice = Integer.parseInt(sc.nextLine());
                switch (choice) {
                    case 1:
                        return;
                    case 2:
                        payroll = InputSalaryRank();
                    default:
                        System.out.println("Không có lựa chọn này");
                }
            }


            Teacher teacher = new Teacher(teacherId, name, age, department, position, workHours, payroll);
            teacherManager.AddTeacher(teacher);
            System.out.println("Thêm giáo viên thành công!");
            System.out.println("--------------------------");
            showMenu();
        } catch (Exception e) {
            System.out.println("Lỗi nhập dữ liệu: " + e.getMessage());
        }
    }

    public void editTeacher() {
        System.out.print("Nhập stt giáo viên cần sửa: ");
        int id = Integer.parseInt(sc.nextLine());

        Teacher teacher = teacherManager.getByIndex(id-1);
        if (teacher == null) {
            System.out.println("Không tìm thấy giáo viên với stt: " + id);
            return;
        }

        try {
            System.out.print("Nhập tên mới (để trống nếu không đổi): ");
            String name = sc.nextLine();
            if (!name.isEmpty()) {
                teacher.setName(name);
            }

            System.out.print("Nhập tuổi mới (để trống nếu không đổi): ");
            String age = sc.nextLine();
            if (!age.isEmpty()) {
                teacher.setAge(Integer.parseInt(age));
            }

            System.out.print("Nhập bộ môn mới (để trống nếu không đổi): ");
            String department = sc.nextLine();
            if (!department.isEmpty()) {
                teacher.setDepartment(department);
            }

            System.out.print("Nhập chức vụ mới (để trống nếu không đổi): ");
            String position = sc.nextLine();
            if (!position.isEmpty()) {
                teacher.setPosition(position);
            }

            Payroll payroll = new Payroll();
            System.out.print("Nhập mức lương mới (để trống nếu không đổi): Y/N ");
            String choice1 = sc.nextLine();
            if (!choice1.isEmpty()) {
                teacher.setPosition(position);
            } else {
                payroll = InputSalaryRank();
                while (payroll == null) {
                    System.out.println("Không tìm thấy thông tin của mức lương");
                    System.out.println("1.Thoát");
                    System.out.println("2.Nhập lại");
                    int choice = Integer.parseInt(sc.nextLine());
                    switch (choice) {
                        case 1:
                            showMenu();
                            break;
                        case 2:
                            payroll = InputSalaryRank();
                        default:
                            System.out.println("Không có lựa chọn này");
                    }
                }
            }

            teacher.setPayroll(payroll);

            System.out.print("Nhập số giờ làm việc mới (để trống nếu không đổi): ");
            String workHours = sc.nextLine();
            if (!workHours.isEmpty()) {
                teacher.setWorkHours(Integer.parseInt(workHours));
            }

            teacherManager.Edit((id-1), teacher);
            System.out.println("Cập nhật thông tin giáo viên thành công!");
        } catch (Exception e) {
            System.out.println("Lỗi khi cập nhật: " + e.getMessage());
        }
    }

    public void deleteTeacher() {
        System.out.print("Nhập stt giáo viên cần xoá: ");
        int id = Integer.parseInt(sc.nextLine());

        Teacher teacher = teacherManager.getByIndex(id-1);
        if (teacher != null) {
            teacherManager.Delete(teacher);
            System.out.println("Xoá giáo viên thành công!");
        } else {
            System.out.println("Không tìm thấy giáo viên với ID: " + (id-1));
        }
    }

    public void sort(){
        System.out.println("Chọn tiêu chí sắp xếp:");
        System.out.println("1. Theo tên");
        System.out.println("2. Theo tuổi");
        System.out.println("3. Theo tổng lương");
        System.out.println("4. Theo tổng số giờ làm việc");
        System.out.println("5. Thoát");
        System.out.print("Lựa chọn: ");

        try {
            int criteria = Integer.parseInt(sc.nextLine());
            if (criteria == 5) {
                showMenu();
            } else {
                teacherManager.sortTeachers(criteria);
                getAll();
            }
        } catch (NumberFormatException e) {
            System.out.println("Vui lòng nhập số hợp lệ!");
            sort();
        }
    }

    private Payroll InputSalaryRank() {
        System.out.println("Chọn danh sách múc lương");
        System.out.println("=================================");
        payrollManager.getAllPayrolls();
        System.out.println("=================================");
        int payRollIndex = Integer.parseInt(sc.nextLine());

        Payroll payroll = payrollManager.getByIndex(payRollIndex-1);
        return payroll;
    }

    public void showFindMenu() {
        System.out.println("Chọn tiêu chí tìm kiếm:");
        System.out.println("1. Tên nhỏ nhất (A-Z)");
        System.out.println("2. Tên lớn nhất (Z-A)");
        System.out.println("3. Tuổi lớn nhất");
        System.out.println("4. Tuổi nhỏ nhất");
        System.out.println("5. Số giờ làm việc lớn nhất");
        System.out.println("6. Số giờ làm việc nhỏ nhất");
        System.out.println("7. trở lại màn hình chính");
        while (true) {
            System.out.print("Lựa chọn kiểu : ");

            try {
                int choice = Integer.parseInt(sc.nextLine());
                switch (choice) {
                    case 1:
                        teacherManager.printResult("có tên nhỏ nhất", teacherManager.findTeacherWithSmallestName());
                        break;
                    case 2:
                        teacherManager.printResult("có tên lớn nhất", teacherManager.findTeacherWithLargestName());
                        break;
                    case 3:
                        teacherManager.printResult("có tuổi lớn nhất", teacherManager.findTeacherWithMaxAge());
                        break;
                    case 4:
                        teacherManager.printResult("có tuổi nhỏ nhất", teacherManager.findTeacherWithMinAge());
                        break;
                    case 5:
                        teacherManager.printResult("có số giờ làm việc lớn nhất", teacherManager.findTeacherWithMaxWorkHours());
                        break;
                    case 6:
                        teacherManager.printResult("có số giờ làm việc nhỏ nhất", teacherManager.findTeacherWithMinWorkHours());
                        break;
                    case 7:
                        showMenu();
                        break;
                    default:
                        System.out.println("Lựa chọn không hợp lệ!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số hợp lệ!");
            }
        }

    }

    public void findTeachersByDepartment() {
        boolean back = false;
        while (true) {
            System.out.println("============================================================");
            System.out.println(" chọn bộ phận muốn tìm kiếm ");
            System.out.println("1.Công nghệ thông tin ");
            System.out.println("2.Quản trị");
            System.out.println("3.Tài chính ");
            System.out.println("4.Ngoại ngữ");
            System.out.println("5.về menu");
            int index;
            int flag = 0;

            try{
                index = Integer.parseInt(sc.nextLine());
                if (index == 5) back = true;
            }catch (Exception e) {
                System.out.println("vì nhập không phải là số nên mặc định sẽ chọn công nghệ thông tin");
                index = 1;
            }

            List<Teacher> teachers = new ArrayList<>();

            switch (index) {
                case 1 :
                    teachers = teacherManager.findByDepartment(Department.CN);
                    DpI(teachers);
                    break;
                case 2:
                    teachers = teacherManager.findByDepartment(Department.QT);
                    DpI(teachers);
                    break;
                case 3:
                    teachers = teacherManager.findByDepartment(Department.TC);
                    DpI(teachers);
                    break;
                case 4:
                    teachers = teacherManager.findByDepartment(Department.NN);
                    DpI(teachers);
                    break;
                case 5 :
                    showMenu();
                    break;
                default:
                    System.out.println("không có lựa chọn này ");
            }
        }

    }

    public void TrungBinhLuong(List<Teacher> teachers){
        double count = 0;
        for (Teacher teacher: teachers) {
            count += teacher.getTotalSalary();
        }

        double tb = count/teachers.size();
        System.out.println("Lương trung bình là");
        System.out.print(tb);
    }

    public void ageAvg(List<Teacher> teachers){
        int count = 0;
        for (Teacher teacher:teachers) {
            count+=teacher.getAge();
        }
        System.out.println("Tuổi trung bình là : " + (count / teachers.size()));
    }

    public void totalSalary(List<Teacher> teachers){
        double count = 0;
        for (Teacher t : teachers) {
            count+= t.getTotalSalary();
        }

        System.out.println("Tổng lương của phòng ban là: " + count);
    }

    public void totalHours(List<Teacher> teachers) {
        double count = 0;
        for (Teacher t : teachers) {
            count+= t.getWorkHours();
        }
        System.out.println("Tổng số giờ làm của phòng ban là: " + count);
    }

    public void  avgHours(List<Teacher> teachers){
        double count = 0;
        for (Teacher t : teachers) {
            count+= t.getWorkHours();
        }
        System.out.println("số giờ làm trung bình của phòng ban là: " + (count/teachers.size()));
    }

    public void DpI(List<Teacher> teachers) {
        if (teachers.size() == 0) {
            findTeachersByDepartment();
            return;
        }
        System.out.println("=========================");
        System.out.println("1.Tính mức lương trung bình của phòng/ban");
        System.out.println("2.Tính tổng lương của phòng/ban");
        System.out.println("3.Tuổi trung bình của phòng/ban");
        System.out.println("4.Tổng số giờ làm của phòng/ban");
        System.out.println("5.Số giờ làm trung bình của môi người trong phòng/ban");
        System.out.println("6.Quay lại");

        int action = Integer.parseInt(sc.nextLine());

        switch (action){
            case 1:
                TrungBinhLuong(teachers);
                DpI(teachers);
                break;
            case 2:
                totalSalary(teachers);
                DpI(teachers);
                break;
            case 3:
                ageAvg(teachers);
                DpI(teachers);
                break;
            case 4:
                totalHours(teachers);
                DpI(teachers);
                break;
            case 5:
                avgHours(teachers);
                DpI(teachers);
                break;
            case 6 :
                findTeachersByDepartment();
                break;
            default:
                System.out.println("không có chức năng này");
                DpI(teachers);
                break;
        }
    }
}
