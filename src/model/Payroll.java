package model;

public class Payroll {
    private String PayrollID;
    private double baseSalary;
    private double salaryMultiplier;
    private double totalSalary;

    public Payroll() {
    }

    public Payroll(String PayrollId, double baseSalary, double salaryMultiplier, double totalSalary) {
        this.PayrollID = PayrollId;
        this.baseSalary = baseSalary;
        this.salaryMultiplier = salaryMultiplier;
    }

    public double GetTotalSalary(int workHours){
        totalSalary = baseSalary * salaryMultiplier * workHours;
        return totalSalary;
    }

    public String getPayrollID() {
        return PayrollID;
    }

    public void setPayrollID(String payrollID) {
        PayrollID = payrollID;
    }

    public double getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(double baseSalary) {
        this.baseSalary = baseSalary;
    }

    public double getSalaryMultiplier() {
        return salaryMultiplier;
    }

    public void setSalaryMultiplier(double salaryMultiplier) {
        this.salaryMultiplier = salaryMultiplier;
    }

    @Override
    public String toString() {
        return "Payroll{" +
                "PayrollID='" + PayrollID +
                ", baseSalary=" + baseSalary +
                ", salaryMultiplier=" + salaryMultiplier +
                ", totalSalary=" + totalSalary +
                '\n';
    }
}
