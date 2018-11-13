package org.ontario.goldendelicious.controllers;

import lombok.extern.slf4j.Slf4j;
import org.ontario.goldendelicious.commands.StaffCommand;
import org.ontario.goldendelicious.commands.UpdatableStaffCommand;
import org.ontario.goldendelicious.domain.Staff;
import org.ontario.goldendelicious.services.StaffService;
import org.ontario.goldendelicious.services.StaffServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Slf4j
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

    @RequestMapping("/user/index")
    public String getUsersListAction(Model model) {
        List<Staff> list = staffService.getStaffList();
        model.addAttribute("usersList", list);

        return "admin/user/index";
    }

    @RequestMapping("/user/create")
    public String createAction(Model model) {
        StaffCommand staffCommand = new StaffCommand();
        model.addAttribute("user", staffCommand);

        return "admin/user/create";
    }

    @RequestMapping("/user/save")
    public String saveUser(@RequestParam("imageFile") MultipartFile file,
                           @Valid @ModelAttribute("user") StaffCommand user,
                           BindingResult bindingResult) throws IOException {

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });

            return "admin/user/create";
        }

        staffService.saveStaffCommand(user, file);

        return "redirect:/admin/user/index";
    }

    @RequestMapping("/user/{id}/view")
    public String viewAction(@PathVariable("id") String id, Model model) {
        StaffCommand user = staffService.findStaffCommandById(Long.valueOf(id));
        model.addAttribute("user", user);

        return "admin/user/view";
    }

    @RequestMapping("/user/{id}/delete")
    public String deleteAction(@PathVariable("id") String id) {
        staffService.deleteById(new Long(id));
        return "redirect:/admin/user/index";
    }

    @RequestMapping("/user/{id}/update")
    public String updateAction(@PathVariable("id") String id, Model model) {
        StaffCommand user = staffService.findStaffCommandById(Long.valueOf(id));
        UpdatableStaffCommand updatable = staffService.bindParamsToUpdatable(user);

        model.addAttribute("user", updatable);

        return "admin/user/update";
    }

    @RequestMapping("/user/update")
    public String updateUser(@RequestParam("imageFile") MultipartFile file,
                           @Valid @ModelAttribute("user") UpdatableStaffCommand user,
                           BindingResult bindingResult) throws IOException {

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });

            return "admin/user/update";
        }

        staffService.updateStaffCommand(user, file);

        return "redirect:/admin/user/index";
    }
}
