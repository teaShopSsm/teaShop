package com.teaShop.controller;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class Hello {

    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public String add() {
        return "hello";
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public String getOne(@PathVariable("id") String userId) {
        return "hello";
    }

    @RequestMapping(value = "/user/{id}/{name}", method = RequestMethod.GET)
    public String getOne(@PathVariable("id") String userId, @PathVariable("name") String name) {
        return "hello";
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String getList() {
        return "hello";
    }
}
