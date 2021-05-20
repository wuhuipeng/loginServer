package cn.hereyou.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuhuipeng
 * @version 1.0
 * @date 2021/5/20 17:21
 */
@RestController
public class LoginController {
    @GetMapping("/test")
    public  String test(){

     return "hello world";
    }
}
