package com.company;

import java.io.*;
import java.util.*;
import java.util.stream.Stream;

public class TextFileDepartmentSource implements DepartmentSource {
    private boolean isOk = true;
    private HashMap<String ,Department> departments = new HashMap<>();
    private String lastError = "";

    TextFileDepartmentSource(String filepath){
        try (Scanner scanner = new Scanner(new FileInputStream(filepath))){
            for (long lineNumber = 1; scanner.hasNextLine(); ++lineNumber){
                String str = scanner.nextLine();
                StringBuilder error = new StringBuilder();
                StringBuilder lastDepartmentNameBuider = new StringBuilder();
                Optional<Employee> newEmp = parseEmployee(str, lastDepartmentNameBuider, error);
                if (newEmp.isEmpty()) {
                    System.out.println("Не удалось прочитать строку номер №" + lineNumber + " : " + error.toString());
                    continue;
                }

                String lastDepartmentName = lastDepartmentNameBuider.toString();
                departments.putIfAbsent(lastDepartmentName, new Department(lastDepartmentName));
                departments.get(lastDepartmentName).addEmployee( newEmp.get());

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

    public static Optional<Employee> parseEmployee(String str, StringBuilder departmentName, StringBuilder error){
        String[] strList = str.split("/");
        if (strList.length != 3) {
            error.append("Строка должна содержать ровно два \"/\"");
            return Optional.empty();
        }

        String newEmployeeName = strList[0];
        if (newEmployeeName.isEmpty()){
            error.append("Имя сотрудника не указано");
            return Optional.empty();
        }

        String newEmployeeDepartmentName = strList[1];
        if (newEmployeeDepartmentName.isEmpty()){
            error.append("Название отдела не указано");
            return Optional.empty();
        }

        int newEmployeeSalary = 0;
        try {
            newEmployeeSalary = Integer.parseInt(strList[2]);
            if (newEmployeeSalary <= 0){
                throw new Exception();
            }
        } catch (Exception e){
            error.append("Поле зарплаты должно быть целым положительным числом");
            return Optional.empty();
        }

        departmentName.append(newEmployeeDepartmentName);
        return Optional.of(new Employee(newEmployeeName, newEmployeeSalary));
    }
}