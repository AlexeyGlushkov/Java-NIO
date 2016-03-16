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

    @RequestMapping("/insert")
    public @ResponseBody
    Request insert(@RequestParam(value = "login", required = false, defaultValue = "") String login,
                   @RequestParam(value = "password", required = false, defaultValue = "") String password,
                   Model model ){

        ApplicationContext context = new ClassPathXmlApplicationContext(
                "beans.xml");

        UserDAO userDAOImpl = (UserDAO) context.getBean("usersDAO");

        Boolean result = false;
        Request resultJSON = new Request();

    try {
        if (userDAOImpl.loginIsFree(login)) {
            User user = new User();

            user.setLogin(login);
            user.setPathword(password);

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