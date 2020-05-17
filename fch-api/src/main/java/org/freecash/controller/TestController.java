package org.freecash.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author wanglint
 * @date 2020/5/17 16:22
 **/
@Controller
public class TestController {

    @RequestMapping("index")
    @ResponseBody
    public String index(){
        return "hello freecash!";
    }
}
