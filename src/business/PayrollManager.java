package business;

import model.Payroll;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PayrollManager {
    private List<Payroll> payrollList = new ArrayList<>();
    private String filename = "payrolls.txt";

    public PayrollManager() {
        importPayrollsFromFile(filename);
    }

    public void addPayroll(Payroll payroll) {
        payrollList.add(payroll);
        exportPayrollsToFile(filename);
        System.out.println("Thêm bảng lương thành công!");
    }

    public void PayrollEdit(int index , Payroll payroll){
        try {
            payrollList.set(index,payroll);
            System.out.println("Sửa thành công");
        } catch (Exception e){
            System.out.println("lỗi " + e.getMessage());
        }
    }

    public void deleteByIndex(int index){
        try {
            payrollList.remove(index);
            System.out.println("Xoá thành công");
        } catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("lỗi không xoá được");
        }
    }

    public void delete(Payroll payroll){
        try {
            payrollList.remove(payroll);
            System.out.println("xoá thành công");
        }catch (Exception e){
            System.out.println("Lỗi " + e.getMessage());
        }
    }

    public void getAllPayrolls() {
        if (payrollList.isEmpty()) {
            System.out.println("Danh sách mức lương trống.");
        } else {
            System.out.println("Danh sách mức lương");

            for (int i = 0; i < payrollList.size(); i++) {
                System.out.println((i+1) + ". " + payrollList.get(i).toString());
            }
        }
    }

    public Payroll getByIndex(int index) {
        Payroll payroll = new Payroll();
        payroll = payrollList.get(index);
        return payroll;
    }

    public void exportPayrollsToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Payroll payroll : payrollList) {
                writer.write(payroll.getPayrollID() + "," + payroll.getBaseSalary() + "," +
                        payroll.getSalaryMultiplier() + "," + payroll.GetTotalSalary(1) + "\n");
            }
            System.out.println("Danh sách bảng lương đã được xuất ra file " + filename);
        } catch (IOException e) {
            System.out.println("Lỗi ghi file: " + e.getMessage());
        }
    }

    public void importPayrollsFromFile(String filename) {
        payrollList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String payrollId = parts[0];
                    double baseSalary = Double.parseDouble(parts[1]);
                    double salaryMultiplier = Double.parseDouble(parts[2]);
                    //double totalSalary = Double.parseDouble(parts[3]);

                    Payroll payroll = new Payroll(payrollId, baseSalary, salaryMultiplier);
                    payrollList.add(payroll);
                }
            }
            System.out.println("Dữ liệu bảng lương đã được import từ file " + filename);
        } catch (IOException e) {
            System.out.println("Lỗi đọc file: " + e.getMessage());
        }
    }

    public Payroll findPayrollById(String payrollId) {
        for (Payroll payroll : payrollList) {
            if (payroll.getPayrollID().equalsIgnoreCase(payrollId)) {
                return payroll;
            }
        }
        return null;
    }
}
