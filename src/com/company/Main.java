package com.company;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    static List<Department> deps;

    public static void main(String[] args) {
        if (args.length != 2){
            System.out.println("В аргументах укажите пути до входного и выходного файла");
            return;
        }

        DepartmentSource src = new TextFileDepartmentSource(args[0]);

        if (!src.isOk()){
            System.out.println("Не удалось получить источник работников");
            return;
        }

        deps = src.departments().collect(Collectors.toList());
        if (deps.size() < 2)
        {
            System.out.println("Нужно минимум 2 отдела");
            return;
        }

        PrintStream printer = null;
        try {
            printer = new PrintStream(args[1]);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        printHeader(printer);
        printTable(printer);
    }

    public static void printHeader(PrintStream printer){
        printer.println("Исходные зарплаты отделов:");
        deps.stream().forEach(dep->printer.printf("Отдел %s : %3.0f%n", dep.getName(), dep.avgSalary()));
        printer.println();
    }

    public static void printTable(PrintStream printer){
        printer.printf("%12s; %s; %s; %s%n", "Перевод", "Новая ЗП 1",
                "Новая ЗП 2", "Список сотрудников");
        for (int i = 0; i < deps.size() - 1; ++i){
            for (int j = i + 1; j < deps.size(); ++j){
                deps.get(i).printOptimizationWith(deps.get(j), printer);
            }
        }
    }
}