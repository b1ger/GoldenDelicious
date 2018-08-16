package org.ontario.goldendelicious.converters;

import lombok.Synchronized;
import org.ontario.goldendelicious.commands.StaffCommand;
import org.ontario.goldendelicious.domain.Staff;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class StaffCommandToStaff implements Converter<StaffCommand, Staff> {

    @Override
    @Synchronized
    @Nullable
    public Staff convert(StaffCommand source) {
        if (source == null) {
            return null;
        }

        final Staff staff = new Staff();
        staff.setId(source.getId());
        staff.setImage(source.getImage());
        staff.setFirstName(source.getFirstName());
        staff.setLastName(source.getLastName());
        staff.setUsername(source.getUsername());
        staff.setType(source.getType());
        staff.setPasswordHash(source.getPasswordHash());
        staff.setCreatedAt(source.getCreatedAt());
        if (source.getUpdatedAt() != null) {
            staff.setUpdatedAt(source.getUpdatedAt());
        }
        if (source.getImage() != null) {
            staff.setImage(source.getImage());
        }

        return staff;
    }
}
