package com.viliric.spring.controller;

import com.viliric.spring.DAO.model.Student;
import com.viliric.spring.DAO.model.StudentDAO;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloWorldController {

    @RequestMapping("/hello")
    public String hello(@RequestParam(value="name", required=false, defaultValue="Alexey") String name, Model model) {
        model.addAttribute("name", name);
        return "helloworld";
    }


    @RequestMapping("/insert")
    public String insert(@RequestParam(value = "surname", required = false, defaultValue = "") String surname,
                       @RequestParam(value = "name", required = false, defaultValue = "") String name,
                       @RequestParam(value = "patronymic", required = false, defaultValue = "") String patronymic,
                         Model model ){

        ApplicationContext context = new ClassPathXmlApplicationContext(
                "beans.xml");

        StudentDAO studentDAOImpl = (StudentDAO) context.getBean("studentDAO");

        String result = "";
try {

    Student student = new Student();

    student.setSurname(surname);
    student.setName(name);
    student.setPatronymic(patronymic);

    studentDAOImpl.insert(student);

    result = "Success";
}
catch (Exception e)
{
    result = "FATAL";
}
        model.addAttribute("insert", result);

        return "helloworld";
    }

}