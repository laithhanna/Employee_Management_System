package employee.management.system.springboot.controller;

import employee.management.system.springboot.model.Employee;
import employee.management.system.springboot.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    //create a method handler for the home page (index.html) to display a list of employees
    //Since we want to supply a list of employees to the view layer, we need to use Model parameter in the method
    @GetMapping("/")
    public String viewHomePage(Model model) {
        //add data to the model
        model.addAttribute("listEmployees", employeeService.getAllEmployees());
        return "index"; //return the name of the html page that we will show (in this case index.html). No need to put .html, it is implied.
    }

    /*
        Create a method handler for the "showNewEmployeeForm" found in index.html
     */
    @GetMapping("/showNewEmployeeForm") //this request comes from the index.html page
    public String showNewEmployeeForm(Model model) {
        Employee employee = new Employee();
        //create a model attribute to bind the form data. So, Thymeleaf template will access this empty employee object, by using "employee" to bind the form data
        model.addAttribute("employee", employee);
        return "new_employee";
    }

    //Create a method handler for the "saveEmployee" found in new_employee.html
    //To bind the employee data from the form to the employee object, we need to use @ModelAttribute annotation
    @PostMapping("/saveEmployee")
    public String saveEmployee(@ModelAttribute("employee") Employee employee) {
        //save employee to database
        employeeService.saveEmployee(employee);
        return "redirect:/"; //redirect to the home page
    }

    //Create a method handler for the "showFormForUpdate/{id}" found in index.html
    //@PathVariable is used to bind the method parameter with the path variable in the request
    //create a model attribute to bind the form data. We add data to the model and we pass the model to the thymeleaf template
    @GetMapping("showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {
        //get employee from the service layer which interacts with the repository layer that interacts with the database
        Employee employee = employeeService.getEmployeeById(id);

        //set employee as a model attribute to pre-populate the form
        //here we are getting the employee object from the database, if any. Then, we add this already filled object to the model to populate the form with
        //the data that we already have in order to update what we need
        model.addAttribute("employee", employee);
        return "update_employee";
    }

    //Create a method handler for the "deleteEmployee" found in index.html
    @GetMapping("/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable(value = "id") long id) {
        //call delete method from the service layer
        employeeService.deleteEmployeeById(id);
        return "redirect:/";
    }
}
