package com.company;

import java.io.*;
import java.util.*;

public class TextFileDepartmentSource implements DepartmentSource{
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

                _departments.putIfAbsent(newEmployeeDepartment, new Department(newEmployeeDepartment));
                _departments.get(newEmployeeDepartment).addEmployee(
                        new Employee(newEmployeeName, newEmployeeSalary));

            }
        } catch (IOException e) {
            e.printStackTrace();
            _isOk = false;
        }
    }

    @Override
    public ArrayList<Department> departments() {
        return new ArrayList(_departments.values());
    }

    @Override
    public boolean isOk() {
        return _isOk;
    }

    private boolean _isOk = true;
    private HashMap<String ,Department> _departments = new HashMap<>();
}