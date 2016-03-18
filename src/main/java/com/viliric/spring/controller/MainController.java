package com.viliric.spring.controller;

import com.viliric.spring.DAO.model.Entities.Group;
import com.viliric.spring.DAO.model.Entities.User;
import com.viliric.spring.DAO.model.Interfaces.GroupDAO;
import com.viliric.spring.DAO.model.Interfaces.UserDAO;
import com.viliric.spring.JSONResults.Request;
import com.viliric.spring.JSONResults.Responses;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MainController {
    private static ApplicationContext context;
    private static GroupDAO groupDAOimpl;
    private static UserDAO userDAOImpl ;

    static {
        context = new ClassPathXmlApplicationContext("beans.xml");
        groupDAOimpl = (GroupDAO) context.getBean("groupDAO");
        userDAOImpl = (UserDAO) context.getBean("usersDAO");
    }

    @RequestMapping("/authorication")
    @Transactional(isolation= Isolation.READ_UNCOMMITTED)
    public @ResponseBody
    Request authorication(@RequestParam("login") String login,
                          @RequestParam("password") String password,
                          Model model){

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

    @RequestMapping("/createGroup")
    @Transactional(isolation= Isolation.READ_UNCOMMITTED)
    public @ResponseBody
    Request createGroup(
            @RequestParam("name") String name){

        Boolean result = false;
        Request resultJSON = new Request();

        try {
            Group group = new Group();
            group.setName(name);
            groupDAOimpl.insert(group);
            resultJSON.setResponceStatus(Responses.RES_OK);
        }
        catch (Exception e)
        {
            result = false;
            resultJSON.setResponceStatus(Responses.RES_FATAL);
        }
        resultJSON.setResult(result);

        return resultJSON;
    }

    @RequestMapping("/updateGroup")
    @Transactional(isolation= Isolation.READ_UNCOMMITTED)
    public @ResponseBody
    Request updateGroup(
            @RequestParam("id") int group_id,
            @RequestParam("name") String name){

        Boolean result = false;
        Request resultJSON = new Request();

        try {
            Group group = new Group();
            group.setName(name);
            group.setId(group_id);
            groupDAOimpl.update(group);
            resultJSON.setResponceStatus(Responses.RES_OK);
        }
        catch (Exception e)
        {
            result = false;
            resultJSON.setResponceStatus(Responses.RES_FATAL);
        }
        resultJSON.setResult(result);

        return resultJSON;
    }

    @RequestMapping("/deleteGroup")
    @Transactional(isolation= Isolation.READ_UNCOMMITTED)
    public @ResponseBody
    Request deleteGroup(
            @RequestParam("id") int group_id){

        Boolean result = false;
        Request resultJSON = new Request();

        try {
            Group group = new Group();
            group.setId(group_id);
            groupDAOimpl.delete(group);
            resultJSON.setResponceStatus(Responses.RES_OK);
        }
        catch (Exception e)
        {
            result = false;
            resultJSON.setResponceStatus(Responses.RES_FATAL);
        }
        resultJSON.setResult(result);

        return resultJSON;
    }

    @RequestMapping("/selectAllGroups")
    @Transactional(isolation= Isolation.READ_UNCOMMITTED)
    public @ResponseBody
    List<Group> deleteGroup(){

        Boolean result = false;
        Request resultJSON = new Request();

        return groupDAOimpl.selectAll();
    }
}