package com.kanbujian.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class MessageController {

    @GetMapping("/hi")
    public @ResponseBody Map sayHi(){
        Map map = new HashMap(){{
            put("name", "nikan");
            put("age", 20);
        }};
       return map;
    }
}
