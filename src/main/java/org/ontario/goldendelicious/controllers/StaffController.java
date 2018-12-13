package org.ontario.goldendelicious.controllers;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.ontario.goldendelicious.Utils.StaffUtils;
import org.ontario.goldendelicious.commands.StaffCommand;
import org.ontario.goldendelicious.domain.Request;
import org.ontario.goldendelicious.exceptions.ConvertionException;
import org.ontario.goldendelicious.services.RequestService;
import org.ontario.goldendelicious.services.StaffService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Controller
public class StaffController {

    private StaffService staffService;
    private RequestService requestService;
    private final String STAFF_STAFFORM_URL = "staff/stafform";

    public StaffController(
            StaffService staffService,
            RequestService requestService
    ) {
        this.staffService = staffService;
        this.requestService = requestService;
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

        return STAFF_STAFFORM_URL;
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

        return STAFF_STAFFORM_URL;
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

        log.debug("Param from request form ajax: doctorId = {}", doctorId);
        log.debug("Param from request form ajax: date = {}", date);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date dateObject;
        try {
            dateObject = dateFormat.parse(date);
        } catch (ParseException e) {
            throw new ConvertionException("Convertion RequestCommand to Request was failed: " + e.getMessage());
        }
        Set<Request> requestSet =  requestService.fetchByDateAndDoctor(dateObject.getTime(), Long.valueOf(doctorId));
        String[] availableTime = StaffUtils.getAvailableTime(requestSet);

        Map<String, Object> map = new HashMap<>();
        map.put("status", "success");
        map.put("times", availableTime);

        return map;
    }
}
