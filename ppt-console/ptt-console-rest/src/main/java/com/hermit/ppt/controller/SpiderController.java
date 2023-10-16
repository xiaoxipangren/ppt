package com.hermit.ppt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import us.codecraft.webmagic.Spider;

@RestController
@RequestMapping("/spider")
public class SpiderController {

    @Autowired
    private Spider spider;

    @RequestMapping("/start")

    public ResponseEntity<String> start(){
        spider.runAsync();
        return ResponseEntity.ok("success");
    }


    @RequestMapping("/stop")

    public ResponseEntity<String> stop(){
        spider.stop();
        return ResponseEntity.ok("success");
    }


    @RequestMapping("/hello")
    public String hello(){
        return "hello";
    }

}
