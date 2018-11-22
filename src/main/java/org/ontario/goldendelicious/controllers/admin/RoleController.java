package org.ontario.goldendelicious.controllers.admin;

import lombok.extern.slf4j.Slf4j;
import org.ontario.goldendelicious.commands.StaffCommand;
import org.ontario.goldendelicious.commands.UpdatableStaffCommand;
import org.ontario.goldendelicious.domain.Authority;
import org.ontario.goldendelicious.services.AuthorityService;
import org.ontario.goldendelicious.services.StaffService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Slf4j
@RequestMapping("/admin/role")
@Controller
public class RoleController {

    private AuthorityService authorityService;
    private StaffService userService;

    public RoleController(AuthorityService authorityService, StaffService userService) {
        this.authorityService = authorityService;
        this.userService = userService;
    }

    @RequestMapping("/index")
    public String indexAction(Model model) {
        List<Authority> authorityList = authorityService.getAuthorityList();
        model.addAttribute("roleList", authorityList);

        return "admin/role/index";
    }

    @RequestMapping("/create")
    public String createAction(Model model) {
        Authority authority = new Authority();
        model.addAttribute("authority", authority);

        return "admin/role/create";
    }

    @RequestMapping("/save")
    public String saveAction(@Valid @ModelAttribute("authority") Authority authority,
                             BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });

            return "admin/role/create";
        }

        Authority saved = authorityService.save(authority);
        log.debug("Saved authority: " + saved.toString());

        return "redirect:/admin/role/index";
    }

    @RequestMapping("/attach/{userId}/{authorityId}")
    public String attachRoleAction(@PathVariable("userId") String userId,
                                   @PathVariable("authorityId") String authorityId,
                                   Model model) throws IOException {

        StaffCommand user = userService.findStaffCommandById(Long.valueOf(userId));
        Authority authority = authorityService.getAuthorityById(Long.valueOf(authorityId));

        user.getAuthorities().add(authority);
        UpdatableStaffCommand updatable = userService.bindParamsToUpdatable(user);
        StaffCommand saved = userService.updateStaffCommand(updatable, null);
        model.addAttribute("user", saved);

        log.debug("Attach role to user{" + user.getUserName() + "} with id: " + authorityId);

        return "redirect:/admin/user/" + saved.getId() + "/view";
    }

    @RequestMapping("/detach/{userId}/{authorityId}")
    public String detachRoleAction(@PathVariable("userId") String userId,
                                   @PathVariable("authorityId") String authorityId,
                                   Model model) throws IOException {

        StaffCommand user = userService.findStaffCommandById(Long.valueOf(userId));
        user.getAuthorities().removeIf(item -> item.getId().equals(Long.valueOf(authorityId)));

        UpdatableStaffCommand updatable = userService.bindParamsToUpdatable(user);
        StaffCommand saved = userService.updateStaffCommand(updatable, null);
        model.addAttribute("user", saved);

        log.debug("Detach role from user{" + saved.getUserName() + "} with id: " + authorityId);

        return "redirect:/admin/user/" + saved.getId() + "/view";
    }
}
