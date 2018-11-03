package org.ontario.goldendelicious.services;

import org.ontario.goldendelicious.commands.StaffCommand;
import org.ontario.goldendelicious.domain.Staff;
import org.ontario.goldendelicious.exceptions.NotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface StaffService {

    List<Staff> getStaffList();
    List<StaffCommand> getStaffListOrderByType();
    Staff findById(Long id) throws NotFoundException;
    StaffCommand findStaffCommandById(Long id);
    StaffCommand saveStaffCommand(StaffCommand command, MultipartFile file) throws IOException;
    void deleteById(Long id);
}
