package menu;

import business.PayrollManager;
import model.Payroll;

import java.util.Scanner;
import java.util.UUID;

public class PayrollMenu {
    private PayrollManager payrollManager;
    Scanner sc = new Scanner(System.in);

    public PayrollMenu() {
        payrollManager = new PayrollManager();
    }

    public void showMenu(){
        System.out.println("======================Payroll====================");
        System.out.println("1.Hiển thị danh sách");
        System.out.println("2.Thêm mức lương mới");
        System.out.println("3.Sửa mức lương");
        System.out.println("4.Xoá mức lương ");
        System.out.println("0.Thoát");
        System.out.println("==================================================");

        int index  = Integer.parseInt(sc.nextLine());
        action(index);
    }

    public void action(int index) {
        switch (index) {
            case 1:
                getAll();
                showMenu();
                break;
            case 2:
                add();
                showMenu();
                break;
            case 0:
                System.exit(0);
            default:
                System.out.println("không tồn tại yêu cầu này ");
                showMenu();
        }
    }

    public void add(){
        String id = UUID.randomUUID().toString();
        System.out.println("Nhập mức lương cơ bản");
        double baseSalary = Double.parseDouble(sc.nextLine());
        System.out.println("Nhập hệ sống lương");
        double salaryMultiplier = Double.parseDouble(sc.nextLine());
        Payroll payroll = new Payroll(id,baseSalary,salaryMultiplier);

        payrollManager.addPayroll(payroll);

        showMenu();
    }

    public void edit() {
        System.out.println("Nhập stt ");
    }

    public void getAll() {
        payrollManager.getAllPayrolls();
    }
}
