package com.company;

import java.io.PrintStream;
import java.util.ArrayList;

public class Department {
    private String name = "";
    private ArrayList<Employee> employees = new ArrayList<>();
    double avgSalary = 0.;
    boolean avgSalaryIsReady = false;

    Department(String name){
        this.name = name;
    }

    public void addEmployee(Employee employee){
        employees.add(employee);
        avgSalaryIsReady = false;
    }

    public boolean freeEmployee(Employee employee){
        boolean success = employees.remove(employee);
        if (success)
            avgSalaryIsReady = false;
        return success;
    }

    public String getName(){
        return name;
    }

    public double avgSalary(){
        if (avgSalaryIsReady){
            return avgSalary;
        }
        else {
            if (employees.isEmpty())
                    return 0.;
            avgSalary = employees.stream().mapToDouble(Employee::getSalary).average().getAsDouble();
            avgSalaryIsReady = true;
            return avgSalary;
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

        source.employees.stream().filter(
                                        (emp) -> emp.getSalary() < source.avgSalary()
                                                && emp.getSalary() > target.avgSalary())
                                        .forEach(
                                                (emp)-> printer.println("Сотрудник " + emp.getName()
                                                + ": отдел " + source.getName()
                                                + " -> отдел " + target.getName()));
    }
}
