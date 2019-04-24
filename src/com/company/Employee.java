package com.company;

public class Employee {
    Employee(String name, double salary){
        _name = name;
        _salary = salary;
    }

    public String get_name() {
        return _name;
    }

    public double get_salary() {
        return _salary;
    }

    private String _name = "";
    private double _salary = 0.;
}
