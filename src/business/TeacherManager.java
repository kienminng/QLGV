package business;

import model.Payroll;
import model.Teacher;

import java.io.*;
import java.util.*;

public class TeacherManager {
    private List<Teacher> teacherList = new ArrayList<>();
    String teacherFile = "teachers.txt";

    public TeacherManager() {
        createFileIfNotExists(teacherFile);
        importTeachersFromFile(teacherFile);
    }

    public boolean Delete(Teacher teacher) {
        try {
            teacherList.remove(teacher);
            System.out.println("Xoá thành công");
            exportTeachersToTxt(teacherFile);
            return true;
        } catch (Exception e) {
            System.out.println("Lỗi hệ thống " + e.getMessage());
            return false;
        }
    }

    public void getAll() {
        if (teacherList.isEmpty()) {
            System.out.println("================================================================");
            System.out.println("Danh sách giáo viên trống");
            System.out.println("================================================================");
            System.out.println();
        } else {
            try {
                System.out.println("=================================================================");
                for (int i = 0; i < teacherList.size(); i++) {
                    System.out.println(teacherList.get(i).toString(i+1));
                }
                System.out.println("=================================================================");
                System.out.println();
            } catch (Exception e) {
                System.out.println("Lỗi hệ thống " + e.getMessage());
            }
        }
    }

    public Teacher getByIndex(int index){
        Teacher teacher = teacherList.get(index);
        teacher.toString(index);
        return teacher;
    }


    public boolean AddTeacher(Teacher teacher) {
        try {
            teacherList.add(teacher);
            exportTeachersToTxt(teacherFile);
            return true;
        }
        catch (Exception e) {
            System.out.println("Lỗi hệ thống " + e.getMessage());
            return false;
        }
    }

    public boolean Edit(int index , Teacher teacher) {
        try{
            teacherList.set(index,teacher);
            exportTeachersToTxt(teacherFile);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public void exportTeachersToTxt(String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            for (Teacher teacher : teacherList) {
                writer.write(teacher.getTeacherId() + "|" +
                        teacher.getName() + "|" +
                        teacher.getAge() + "|" +
                        teacher.getDepartment() + "|" +
                        teacher.getPosition() + "|" +
                        teacher.getWorkHours() + "|" +
                        teacher.getPayroll().getPayrollID() + "\n");
            }
            System.out.println("Xuất danh sách giáo viên ra file TXT thành công: " + filename);
        } catch (IOException e) {
            System.out.println("Lỗi khi ghi file TXT: " + e.getMessage());
        }
    }

    public void importTeachersFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 7) {

                    String teacherId = parts[0];
                    String name = parts[1];
                    int age = Integer.parseInt(parts[2]);
                    String department = parts[3];
                    String position = parts[4];
                    int workHours = Integer.parseInt(parts[5]);
                    String payrollId = (parts[6]);
                    PayrollManager payrollManager = new PayrollManager();
                    Payroll payroll = payrollManager.findPayrollById(payrollId);

                    Teacher teacher = new Teacher();
                    teacher.setTeacherId(teacherId);
                    teacher.setName(name);
                    teacher.setAge(age);
                    teacher.setDepartment(department);
                    teacher.setPosition(position);
                    teacher.setWorkHours(workHours);
                    teacher.setPayroll(payroll);
                    teacherList.add(teacher);
                } else {
                    System.out.println("Dòng dữ liệu không hợp lệ: " + line);
                }
            }
            System.out.println("Nhập danh sách giáo viên từ file thành công!");
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Dữ liệu số không hợp lệ trong file: " + e.getMessage());
        }
    }

    public void createFileIfNotExists(String filename) {
        String filePath = "data/" + filename;
        File file = new File(filePath);

        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
                System.out.println("Tạo file mới: " + filePath);
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi tạo file: " + e.getMessage());
        }
    }

    public void sortTeachers(int criteria) {
        switch (criteria) {
            case 1: // Sắp xếp theo tên
                Collections.sort(teacherList, Comparator.comparing(Teacher::getName));
                System.out.println("Danh sách giáo viên đã được sắp xếp theo tên.");
                break;
            case 2: // Sắp xếp theo tuổi
                Collections.sort(teacherList, Comparator.comparingInt(Teacher::getAge));
                System.out.println("Danh sách giáo viên đã được sắp xếp theo tuổi.");
                break;
            case 3: // Sắp xếp theo tổng lương
                Collections.sort(teacherList, Comparator.comparingDouble(Teacher::getTotalSalary));
                System.out.println("Danh sách giáo viên đã được sắp xếp theo tổng lương.");
                break;
            case 4: // Sắp xếp theo số giờ
                Collections.sort(teacherList,Comparator.comparingInt(Teacher::getWorkHours));
                System.out.println("Danh sách giáo viên đã được sắp xếp theo tổng số giờ làm việc.");
            default:
                System.out.println("Tiêu chí không hợp lệ!");
                break;
        }
    }

    public Optional<Teacher> findTeacherWithSmallestName() {
        return teacherList.stream()
                .min(Comparator.comparing(Teacher::getName));
    }

    // Tìm giáo viên có tên xếp theo thứ tự từ điển lớn nhất
    public Optional<Teacher> findTeacherWithLargestName() {
        return teacherList.stream()
                .max(Comparator.comparing(Teacher::getName));
    }

    // Tìm giáo viên có tuổi lớn nhất
    public Optional<Teacher> findTeacherWithMaxAge() {
        return teacherList.stream()
                .max(Comparator.comparingInt(Teacher::getAge));
    }

    // Tìm giáo viên có tuổi nhỏ nhất
    public Optional<Teacher> findTeacherWithMinAge() {
        return teacherList.stream()
                .min(Comparator.comparingInt(Teacher::getAge));
    }

    // Tìm giáo viên có số giờ làm việc lớn nhất
    public Optional<Teacher> findTeacherWithMaxWorkHours() {
        return teacherList.stream()
                .max(Comparator.comparingInt(Teacher::getWorkHours));
    }

    // Tìm giáo viên có số giờ làm việc nhỏ nhất
    public Optional<Teacher> findTeacherWithMinWorkHours() {
        return teacherList.stream()
                .min(Comparator.comparingInt(Teacher::getWorkHours));
    }

    // In kết quả tìm kiếm
    public void printResult(String criteria, Optional<Teacher> teacher) {
        if (teacher.isPresent()) {
            System.out.println("Giáo viên " + criteria + ": " + teacher.get());
        } else {
            System.out.println("Không tìm thấy giáo viên.");
        }
    }
}
