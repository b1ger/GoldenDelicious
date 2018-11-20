package org.ontario.goldendelicious.controllers.admin;

import lombok.extern.slf4j.Slf4j;
import org.ontario.goldendelicious.domain.Authority;
import org.ontario.goldendelicious.services.AuthorityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@RequestMapping("/admin/role")
@Controller
public class RoleController {

    private AuthorityService authorityService;

    public RoleController(AuthorityService authorityService) {
        this.authorityService = authorityService;
    }

    @RequestMapping("/index")
    public String indexAction(Model model) {
        List<Authority> authorityList = authorityService.getAuthorityList();
        model.addAttribute("roleList", authorityList);

        return "admin/role/index";
    }
}
