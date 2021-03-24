package com.online.purchase.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {


    @RequestMapping("/user/list")
    public String getUserList(){
        return "user-list";
    }


}
