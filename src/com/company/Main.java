package com.company;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        DepartmentSource src = new TextFileDepartmentSource("in.txt");

        if (!src.isOk()){
            System.out.println("Не удалось получить источник работников");
            return;
        }

        ArrayList<Department> deps= src.departments();
        if (deps.size() < 2)
        {
            System.out.println("Нужно минимум 2 отдела");
            return;
        }

        PrintStream printer = null;
        try {
            printer = new PrintStream("out.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        for (int i = 0; i < deps.size() - 1; ++i){
            for (int j = i + 1; j < deps.size(); ++j){
                deps.get(i).printOptimizationWith(deps.get(j), printer);
            }
        }
    }
}