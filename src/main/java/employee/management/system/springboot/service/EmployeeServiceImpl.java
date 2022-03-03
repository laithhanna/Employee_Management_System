package employee.management.system.springboot.service;

import employee.management.system.springboot.model.Employee;
import employee.management.system.springboot.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Override
    public Page<Employee> findPaginated(int pageNo, int pageSize) {
        //Pageable is an interface and PageRequest is a class that internally implements the Pageable interface
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        /*Notice how we subtract 1 from the page number. Because SpringDataJpa consider pages to be 0 indexed. However,
        from the user point of view the pages start from 1. So, we need to subtract 1 before we pass the page number
        as an argument.*/
        return employeeRepository.findAll(pageable);
    }
}
