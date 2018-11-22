package org.ontario.goldendelicious.controllers.admin;

import lombok.extern.slf4j.Slf4j;
import org.ontario.goldendelicious.commands.StaffCommand;
import org.ontario.goldendelicious.commands.UpdatableStaffCommand;
import org.ontario.goldendelicious.domain.Authority;
import org.ontario.goldendelicious.domain.Staff;
import org.ontario.goldendelicious.services.AuthorityService;
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
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequestMapping("/admin")
@Controller
public class UserController {

    private StaffService staffService;
    private AuthorityService authorityService;

    public UserController(StaffServiceImpl staffService, AuthorityService authorityService) {
        this.staffService = staffService;
        this.authorityService = authorityService;
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
        List<Authority> authorities = authorityService.getAuthorityList();
        Set<String> ids = user.getAuthorities().stream()
                .map(Authority::getName)
                .collect(Collectors.toSet());
        List<Authority> availableAuthorities = authorities.stream()
                .filter(authority -> !ids.contains(authority.getName()))
                .collect(Collectors.toList());

        model.addAttribute("user", user);
        model.addAttribute("availableAuthorities", availableAuthorities);

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

        StaffCommand saved = staffService.updateStaffCommand(user, file);
        log.debug("Update user with id: " + saved.getId());

        return "redirect:/admin/user/" + saved.getId() + "/view";
    }
}
