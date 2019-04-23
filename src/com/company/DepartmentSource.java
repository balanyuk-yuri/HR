package com.company;

import java.util.ArrayList;

public interface DepartmentSource {
    ArrayList<Department> departments();
    boolean isOk();
}
