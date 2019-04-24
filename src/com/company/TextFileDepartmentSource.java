package com.company;

import java.io.*;
import java.util.*;
import java.util.stream.Stream;

public class TextFileDepartmentSource implements DepartmentSource {
    private boolean isOk = true;
    private HashMap<String ,Department> departments = new HashMap<>();

    TextFileDepartmentSource(String filepath){
        try (Scanner scanner = new Scanner(new FileInputStream(filepath))){
            while (scanner.hasNextLine()){
                String str = scanner.nextLine();
                String[] strList = str.split("/");
                if (strList.length < 3)
                    continue;

                String newEmployeeName = strList[0];
                String newEmployeeDepartment = strList[1];
                double newEmployeeSalary = Double.parseDouble(strList[2]);

                departments.putIfAbsent(newEmployeeDepartment, new Department(newEmployeeDepartment));
                departments.get(newEmployeeDepartment).addEmployee(
                        new Employee(newEmployeeName, newEmployeeSalary));

            }
        } catch (IOException e) {
            e.printStackTrace();
            isOk = false;
        }
    }

    @Override
    public Stream<Department> departments() {
        return departments.values().stream();
    }

    @Override
    public boolean isOk() {
        return isOk;
    }
}