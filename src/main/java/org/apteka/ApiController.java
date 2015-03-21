package org.apteka;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Admin on 21.03.2015.
 */
@Controller
@RequestMapping("/Api")
public class ApiController {
    @RequestMapping("/")
    public @ResponseBody
    String sayHello(){
        return "Hi!!!, this is Spri2ng Boot Project for IS 'Apteka'";
    }
}