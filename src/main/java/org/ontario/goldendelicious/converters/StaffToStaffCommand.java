package org.ontario.goldendelicious.converters;

import lombok.Synchronized;
import org.ontario.goldendelicious.commands.StaffCommand;
import org.ontario.goldendelicious.domain.Staff;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class StaffToStaffCommand implements Converter<Staff, StaffCommand> {

    @Synchronized
    @Nullable
    @Override
    public StaffCommand convert(Staff source) {
        if (source == null) {
            return null;
        }

        final StaffCommand command = new StaffCommand();
        command.setId(source.getId());
        command.setImage(source.getImage());
        command.setFirstName(source.getFirstName());
        command.setLastName(source.getLastName());
        command.setUsername(source.getUsername());
        command.setType(source.getType());
        command.setPasswordHash(source.getPasswordHash());
        command.setCreatedAt(source.getCreatedAt());
        if (source.getUpdatedAt() != null) {
            command.setUpdatedAt(source.getUpdatedAt());
        }
        if (source.getImage() != null) {
            command.setImage(source.getImage());
        }

        return command;
    }
}
