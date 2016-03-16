package com.inculerate.diode.controller;

import com.inculerate.diode.DAO.model.UserDAO;
import com.inculerate.diode.JSONResults.Request;
import com.inculerate.diode.DAO.model.User;
import com.inculerate.diode.JSONResults.Responses;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {

    @RequestMapping("/authorication")
    @Transactional(isolation= Isolation.READ_UNCOMMITTED)
    public @ResponseBody
    Request authorication(@RequestParam("login") String login,
                          @RequestParam("password") String password,
                          Model model){
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "beans.xml");
        UserDAO userDAOImpl = (UserDAO) context.getBean("usersDAO");
        userDAOImpl.authorization(login, password);
        Request result = new Request();

        try {
            boolean res = userDAOImpl.authorization(login, password);
            result.setResult(res);
            if (res)
                result.setResponceStatus(Responses.RES_OK);
            else
                result.setResponceStatus(Responses.INCORRECT_DATA);
        }catch (Exception e){
            result.setResponceStatus(Responses.RES_FATAL);
        }
        return result;
    }

    @RequestMapping("/updatePassword")
    public @ResponseBody
    Request updatePassword(@RequestParam("user_id") int user_id,
                           @RequestParam("new_pass") String new_pass,
                          Model model){
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "beans.xml");
        UserDAO userDAOImpl = (UserDAO) context.getBean("usersDAO");
        Request result = new Request();
        try {
            User user = new User();
            user.setId(user_id);
            user.setPassword(new_pass);
            userDAOImpl.updatePassword(user);
            result.setResult(true);
            result.setResponceStatus(Responses.RES_OK);
        }catch (Exception e){
            result.setResult(false);
            result.setResponceStatus(Responses.RES_FATAL);
        }
        return result;
    }

    @RequestMapping("/updateInfo")
    public @ResponseBody
    Request updateInfo(@RequestParam("user_id") int user_id,
                       @RequestParam("surname") String surname,
                       @RequestParam("name") String name,
                       @RequestParam("patronymic") String patronymic,
                       @RequestParam("email") String email,
                           Model model){
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "beans.xml");
        UserDAO userDAOImpl = (UserDAO) context.getBean("usersDAO");
        Request result = new Request();
        try {
            User user = new User();
            user.setId(user_id);
            user.setSurname(surname);
            user.setName(name);
            user.setPatronymic(patronymic);
            user.setEmail(email);
            userDAOImpl.updateUserInfo(user);
            result.setResult(true);
            result.setResponceStatus(Responses.RES_OK);
        }catch (Exception e){
            result.setResult(false);
            result.setResponceStatus(Responses.RES_FATAL);
        }
        return result;
    }

    @RequestMapping("/createUser")
    @Transactional(isolation= Isolation.READ_UNCOMMITTED)
    public @ResponseBody
    Request createUser(
                    @RequestParam("surname") String surname,
                    @RequestParam("name") String name,
                    @RequestParam("patronymic") String patronymic,
                    @RequestParam("email") String email,
                    @RequestParam(value = "login") String login,
                    @RequestParam(value = "password") String password,
                   Model model ){

        ApplicationContext context = new ClassPathXmlApplicationContext(
                "beans.xml");

        UserDAO userDAOImpl = (UserDAO) context.getBean("usersDAO");

        Boolean result = false;
        Request resultJSON = new Request();

    try {
        if (userDAOImpl.loginIsFree(login)) {
            User user = new User();

            user.setSurname(surname);
            user.setName(name);
            user.setPatronymic(patronymic);
            user.setEmail(email);
            user.setLogin(login);
            user.setPassword(password);

            userDAOImpl.insert(user);

            result = true;
            resultJSON.setResponceStatus(Responses.RES_OK);
        } else
        {
            resultJSON.setResult(false);
            resultJSON.setResponceStatus(Responses.LOGIN_BUSY);
        }
    }
    catch (Exception e)
    {
        result = false;
        resultJSON.setResponceStatus(Responses.RES_FATAL);
    }
        resultJSON.setResult(result);

        return resultJSON;
    }

}