package org.ontario.goldendelicious.controllers;

import org.ontario.goldendelicious.domain.Staff;
import org.ontario.goldendelicious.services.StaffService;
import org.ontario.goldendelicious.services.StaffServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/admin")
@Controller
public class AdminController {

    private StaffService staffService;

    public AdminController(StaffServiceImpl staffService) {
        this.staffService = staffService;
    }

    @RequestMapping("/index")
    public String indexAction() {
        return "admin/index";
    }

    @RequestMapping("/users")
    public String getUsersListAction(Model model) {
        List<Staff> list = staffService.getStaffList();
        model.addAttribute("usersList", list);

        return "admin/users";
    }
}
