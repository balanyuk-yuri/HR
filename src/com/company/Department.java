package com.company;

import java.io.PrintStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    public void printOptimizationWith(Department other, PrintStream printer) {
        Department source;
        Department target;
        if (avgSalary() > other.avgSalary()) {
            source = this;
            target = other;
        } else {
            source = other;
            target = this;
        }

        for (int m = 1; m < source.employees.size() - 1; ++m) {
            int[] testingIndices = null;
            while ((testingIndices = generateIndecesCombinations(testingIndices, m, source.employees.size())) != null) {
                Set<Integer> testingIndicesSet = Arrays.stream(testingIndices).boxed().collect(Collectors.toSet());

                List<Employee> curEmps = IntStream.range(0, source.employees.size())
                        .filter(i -> testingIndicesSet.contains(i))
                        .mapToObj(i -> source.employees.get(i)).collect(Collectors.toList());

                double curAvgSalary = curEmps.stream().mapToDouble(Employee::getSalary).average().getAsDouble();
                if (curAvgSalary < source.avgSalary() && curAvgSalary > target.avgSalary()){
                    printer.printf("%12s;", source.name + " -> " + target.name);
                    printer.printf("%11.0f;", source.avgSalaryIfFree(curEmps));
                    printer.printf("%11.0f; ", target.avgSalaryIfAdd(curEmps));
                    curEmps.stream().forEach((emp)->printer.print(emp.getName() + " "));
                    printer.println();
                }
            }
        }
    }

    private double avgSalaryIfAdd(List<Employee> newEmps){
        List<Employee> resultEmplList = new ArrayList<>(employees);
        resultEmplList.addAll(newEmps);
        return resultEmplList.stream().mapToDouble(Employee::getSalary).average().getAsDouble();
    }

    private double avgSalaryIfFree(List<Employee> emps){
        List<Employee> resultEmplList = new ArrayList<>(employees);
        resultEmplList.removeAll(emps);
        return resultEmplList.stream().mapToDouble(Employee::getSalary).average().getAsDouble();
    }

    private static int[] generateIndecesCombinations(int[] arr, int M, int N)
    {
        if (arr == null)
        {
            arr = new int[M];
            for (int i = 0; i < M; i++)
                arr[i] = i;
            return arr;
        }
        for (int i = M - 1; i >= 0; i--)
            if (arr[i] < N - M + i)
            {
                arr[i]++;
                for (int j = i; j < M - 1; j++)
                    arr[j + 1] = arr[j] + 1;
                return arr;
            }
        return null;
    }
}
