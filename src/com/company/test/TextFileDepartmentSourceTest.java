package com.company.test;

import com.company.Employee;
import com.company.TextFileDepartmentSource;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TextFileDepartmentSourceTest {
    StringBuilder error;
    StringBuilder lastDepartmentNameBuider;

    @Before
    public void testInit(){
        error = new StringBuilder();
        lastDepartmentNameBuider = new StringBuilder();
    }

    @Test
    public void parseEmployeeOk() {
        String testStr = "Иванов/A/100";
        Optional<Employee> result = TextFileDepartmentSource.parseEmployee(testStr, lastDepartmentNameBuider, error);
        assertTrue(result.isPresent());
    }

    @Test
    public void parseEmployeeEmpty() {
        String testStr = "";
        Optional<Employee> result = TextFileDepartmentSource.parseEmployee(testStr, lastDepartmentNameBuider, error);
        assertTrue(result.isEmpty());
    }

    @Test
    public void parseEmployeeEmptyName() {
        String testStr = "/A/100";
        Optional<Employee> result = TextFileDepartmentSource.parseEmployee(testStr, lastDepartmentNameBuider, error);
        assertTrue(result.isEmpty());
    }

    @Test
    public void parseEmployeeEmptyDepartment() {
        String testStr = "Иванов//100";
        Optional<Employee> result = TextFileDepartmentSource.parseEmployee(testStr, lastDepartmentNameBuider, error);
        assertTrue(result.isEmpty());
    }

    @Test
    public void parseEmployeeWrongSalary() {
        String testStr = "Иванов/A/zxcv";
        Optional<Employee> result = TextFileDepartmentSource.parseEmployee(testStr, lastDepartmentNameBuider, error);
        assertTrue(result.isEmpty());
    }
}
