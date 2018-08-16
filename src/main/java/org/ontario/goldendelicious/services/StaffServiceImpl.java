package org.ontario.goldendelicious.services;

import lombok.extern.slf4j.Slf4j;
import org.ontario.goldendelicious.commands.StaffCommand;
import org.ontario.goldendelicious.converters.StaffCommandToStaff;
import org.ontario.goldendelicious.converters.StaffToStaffCommand;
import org.ontario.goldendelicious.domain.Staff;
import org.ontario.goldendelicious.exceptions.NotFoundException;
import org.ontario.goldendelicious.repositories.StaffRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class StaffServiceImpl implements StaffService {

    private StaffRepository repository;
    private StaffToStaffCommand staffToStaffCommand;
    private StaffCommandToStaff staffCommandToStaff;

    public StaffServiceImpl(
            StaffRepository repository,
            StaffToStaffCommand staffToStaffCommand,
            StaffCommandToStaff staffCommandToStaff
    ) {
        this.repository = repository;
        this.staffToStaffCommand = staffToStaffCommand;
        this.staffCommandToStaff = staffCommandToStaff;
    }

    @Override
    public List<Staff> getStaffList() {
        return repository.findAllByOrderById();
    }

    @Override
    public Staff findById(Long id) throws NotFoundException {
        Optional<Staff> optional = repository.findById(id);

        if (! optional.isPresent()) {
            throw new NotFoundException("Staff not found for 'id' value: " + id);
        }

        return optional.get();
    }

    @Override
    @Transactional
    public StaffCommand findStaffCommandById(Long id) {
        return staffToStaffCommand.convert(findById(id));
    }

    @Override
    @Transactional
    public StaffCommand saveStaffCommand(StaffCommand command, MultipartFile file) {
        Date date = new Date();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        command.setImage(getBytesFromFile(file));
        command.setCreatedAt(date.getTime());
        command.setUpdatedAt(command.getCreatedAt());
        command.setPasswordHash(encoder.encode(command.getPassword()));
        Staff detached = staffCommandToStaff.convert(command);
        System.out.println(detached);
        Staff saved = repository.save(detached);
        log.debug("Saved Room with id: " + saved.getId());

        return staffToStaffCommand.convert(saved);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    private Byte[] getBytesFromFile(MultipartFile file) {
        Byte[] byteObject = null;
        try {
            byteObject = new Byte[file.getBytes().length];

            int i = 0;
            for (byte b : file.getBytes()) {
                byteObject[i++] = b;
            }
        } catch (IOException ex) {
            log.error("Error occurred ", ex);
        }

        return byteObject;
    }
}
