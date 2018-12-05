package org.ontario.goldendelicious.controllers;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.ontario.goldendelicious.commands.StaffCommand;
import org.ontario.goldendelicious.services.StaffService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class StaffController {

    private StaffService staffService;
    private final String STAFF_STAFFFORM_URL = "staff/stafform";

    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    @GetMapping("/staff/index")
    public String indexAction(Model model) {
        List<StaffCommand> models = staffService.getStaffListOrderByType();
        model.addAttribute("models", models);

        return "staff/index";
    }

    @GetMapping("/staff/new")
    public String newAction(Model model) {
        StaffCommand staffCommand = new StaffCommand();
        model.addAttribute("staff", staffCommand);

        return STAFF_STAFFFORM_URL;
    }

    @GetMapping("/staff/{id}/view")
    public String viewAction(@PathVariable String id, Model model) {
        StaffCommand command = staffService.findStaffCommandById(Long.valueOf(id));
        model.addAttribute("staff", command);

        return "staff/view";
    }

    @GetMapping("/staff/{id}/update")
    public String updateAction(@PathVariable String id, Model model) {
        StaffCommand command = staffService.findStaffCommandById(Long.valueOf(id));
        model.addAttribute("staff", command);

        return STAFF_STAFFFORM_URL;
    }

    @GetMapping("/staff/{id}/profileimage")
    public void renderImageFromDB(@PathVariable String id, HttpServletResponse response) throws IOException {
        StaffCommand staffCommand = staffService.findStaffCommandById(Long.valueOf(id));

        if (staffCommand.getImage() != null) {
            byte[] byteArray = new byte[staffCommand.getImage().length];
            int i = 0;

            for (Byte wrappedByte : staffCommand.getImage()){
                byteArray[i++] = wrappedByte;
            }

            response.setContentType("image/jpeg");
            InputStream is = new ByteArrayInputStream(byteArray);
            IOUtils.copy(is, response.getOutputStream());
        }
    }

    @RequestMapping(
            value = "/staff/free-time",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Map<String, Object> getFreeTime(@RequestParam String doctorId,
                                                         @RequestParam String date
    ) {

        log.debug("Param from ajax: doctorId = {}", doctorId);
        log.debug("Param from ajax: date = {}", date);

        String[] allTimes = {"9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00"};

        //TODO
        Map<String, Object> map = new HashMap<>();
        map.put("status", "success");

        return map;
    }
}
