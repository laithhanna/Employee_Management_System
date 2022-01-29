package employee.management.system.springboot.service;

import employee.management.system.springboot.model.Employee;
import employee.management.system.springboot.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll(); //findAll method returns a list
    }

    @Override
    public void saveEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    @Override
    public Employee getEmployeeById(long id) {
        //The findById method provided by JPA returns object of type optional.
        /*Optional is nice for null checking. It provides methods such as .ispresent(). Returns true if the object is not null. Thus, present.
        It also provides a get method to get the object from the optional.
         */
        Optional<Employee> optional = employeeRepository.findById(id);
        Employee employee = null;

        if (optional.isPresent()) {
            employee = optional.get(); //Now the employee reference variable points to the object we got from the optional
        } else {
            throw new RuntimeException("Employee not found for the following id: " + id);
        }
        return employee;
    }

    @Override
    public void deleteEmployeeById(long id) {
        employeeRepository.deleteById(id);
    }
}
