package com.udacity.jdnd.course3.critter.schedule;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.user.Employee;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> getAllByPetsContains(Pet pet);
    List<Schedule> getAllByEmployeesContains(Employee employee);
    List<Schedule> getAllByPetsIn(List<Pet> pets);
}
