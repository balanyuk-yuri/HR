package com.company;

public class Employee {
    private String name = "";
    private int salary = 0;

    Employee(String name, int salary){
        this.name = name;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

}
