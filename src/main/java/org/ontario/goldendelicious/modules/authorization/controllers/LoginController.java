package org.ontario.goldendelicious.modules.authorization.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginAction() {
        return "loginform";
    }
}
