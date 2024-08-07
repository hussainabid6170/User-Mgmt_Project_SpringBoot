package com.springbootmicroserviceudm.sbdemo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SimpleController {

    @RequestMapping(path = "/getUser" , method = RequestMethod.GET)
    public String getUserDetails(){

        return "hello";
    }

    @RequestMapping(path = "/getUserDetailWithResponse" , method = RequestMethod.GET)
    @ResponseBody
    public String getUserDetailsWithResponse(){

        return "hello response";
    }

}
