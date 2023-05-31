package com.udacity.jdnd.course3.critter.user;

import java.time.DayOfWeek;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> getAllByAvailDay(DayOfWeek dayOfWeek);

}