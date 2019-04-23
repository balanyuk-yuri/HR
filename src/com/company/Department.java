package com.company;

import javax.xml.transform.Source;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Department {
    Department(String name){
        _name = name;
    }

    public void addEmployee(Employee employee){
        _employees.add(employee);
        _avgSalaryIsReady = false;
    }

    public boolean freeEmployee(Employee employee){
        boolean success = _employees.remove(employee);
        if (success)
            _avgSalaryIsReady = false;
        return success;
    }

    public String get_name(){
        return _name;
    }

    public double avgSalary(){
        if (_avgSalaryIsReady){
            return _avgSalary;
        }
        else {
            if (_employees.isEmpty())
                    return 0.;
            _avgSalary = _employees.stream().mapToDouble(Employee::get_salary).average().getAsDouble();
            _avgSalaryIsReady = true;
            return _avgSalary;
        }
    }

    void printOptimizationWith(Department other, PrintStream printer){
        Department source;
        Department target;
        if (avgSalary() > other.avgSalary()){
            source = this;
            target = other;
        }
        else{
            source = other;
            target = this;
        }

        List<Employee> movedEmployees = source._employees.stream().filter(
                (emp) -> emp.get_salary() < source.avgSalary()
                && emp.get_salary() > target.avgSalary()).
                collect(Collectors.toList());

//        System.out.println("До:");
//        System.out.println("Средняя зп отдела " + source.get_name() + " : " + source.avgSalary());
//        System.out.println("Средняя зп отдела " + target.get_name() + " : " + target.avgSalary());

        for (Employee emp : movedEmployees){
//            source.freeEmployee(emp);
//            target.addEmployee(emp);
            printer.println("Сотрудник " + emp.get_name() + ": отдел " + source.get_name() + " -> отдел " + target.get_name());
        }

//        System.out.println("После:");
//        System.out.println("Средняя зп отдела " + source.get_name() + " : " + source.avgSalary());
//        System.out.println("Средняя зп отдела " + target.get_name() + " : " + target.avgSalary());
    }

    private String _name = "";
    private ArrayList<Employee> _employees = new ArrayList<>();
    double _avgSalary = 0.;
    boolean _avgSalaryIsReady = false;
}
