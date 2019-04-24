package com.company;

import java.util.stream.Stream;

public interface DepartmentSource {
    Stream<Department> departments();
    boolean isOk();
}
