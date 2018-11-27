package org.ontario.goldendelicious.services;

import org.ontario.goldendelicious.commands.StaffCommand;
import org.ontario.goldendelicious.commands.UpdatableStaffCommand;
import org.ontario.goldendelicious.domain.Staff;
import org.ontario.goldendelicious.domain.enums.StaffType;
import org.ontario.goldendelicious.exceptions.NotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface StaffService {

    List<Staff> getStaffList();
    List<Staff> findByType(StaffType type);
    List<StaffCommand> getStaffListOrderByType();
    Staff findById(Long id) throws NotFoundException;
    StaffCommand findStaffCommandById(Long id);
    StaffCommand saveStaffCommand(StaffCommand command, MultipartFile file) throws IOException;
    StaffCommand updateStaffCommand(UpdatableStaffCommand updatable, MultipartFile imageFile) throws IOException;
    void deleteById(Long id);
    UpdatableStaffCommand bindParamsToUpdatable(StaffCommand command);
    StaffCommand bindParamsToStaffCommand(UpdatableStaffCommand updatable, StaffCommand staff);
}
