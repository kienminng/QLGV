package model;

public class Teacher {
    private String teacherId;
    private String name;
    private int age;
    private String department;
    private String position;
    private int workHours;
    private Payroll payroll;


    public Teacher() {
    }

    public Teacher(String teacherId, String name, int age, String department, int workHours) {
        this.teacherId = teacherId;
        this.name = name;
        this.age = age;
        this.department = department;
        this.workHours = workHours;
    }

    public Teacher(String teacherId, String name, int age, String department, String position, int workHours) {
        this.teacherId = teacherId;
        this.name = name;
        this.age = age;
        this.department = department;
        this.position = position;
        this.workHours = workHours;
    }

    public Teacher(String teacherId, String name, int age, String department, String position, int workHours, Payroll payroll) {
        this.teacherId = teacherId;
        this.name = name;
        this.age = age;
        this.department = department;
        this.position = position;
        this.workHours = workHours;
        this.payroll = payroll;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getWorkHours() {
        return workHours;
    }

    public void setWorkHours(int workHours) {
        this.workHours = workHours;
    }

    public String getPosition() {
        return position;
    }

    public double getTotalSalary() {
        double totalSalary = payroll.GetTotalSalary(workHours);
        return totalSalary;
    }

    public Payroll getPayroll() {
        return payroll;
    }

    public void setPayroll(Payroll payroll) {
        this.payroll = payroll;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String toString(int index) {
        String departmentName = new String();
        if (department.equals(Department.CN.name())) {
            departmentName = "Công nghệ thông tin";
        } else if (department.equals(Department.TC.name())) {
            departmentName = "Tài chính";
        } else if (department.equals(Department.QT.name())){
            departmentName = "Quản trị";
        } else if (department.equals(Department.NN.name())) {
            departmentName = "Ngoại ngữ";
        }
        return "----------------------- Teacher -------------------------"+'\n'  +
                "STT = "+ index  +'\'' +
                "teacherId='" + teacherId + '\'' +
                ",name='" + name + '\'' +
                ",age=" + age +
                ",department='" + departmentName + '\'' +
                ",position='" + position + '\'' +
                ",workHours= "  + workHours + '\'' +
                ",Total salary=" + getTotalSalary() + '\n'+
                "--------------------------------------------------------";
    }
}
