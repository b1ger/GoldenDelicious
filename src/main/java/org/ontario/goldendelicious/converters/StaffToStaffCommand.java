package org.ontario.goldendelicious.converters;

import lombok.Synchronized;
import org.ontario.goldendelicious.commands.StaffCommand;
import org.ontario.goldendelicious.domain.Staff;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

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
        DateFormat format = new SimpleDateFormat("d MMM y", Locale.ENGLISH);
        String date = format.format(source.getBirthDate());
        command.setBirthDate(date);
        command.setUserName(source.getUserName());
        command.setType(source.getType());
        command.setPassword(source.getPassword());
        command.setCreatedAt(source.getCreatedAt());
        if (source.getUpdatedAt() != null) {
            command.setUpdatedAt(source.getUpdatedAt());
        }
        if (source.getImage() != null && source.getImage().length > 0) {
            command.setImage(source.getImage());
        }
        command.setAbout(source.getAbout());
        command.setAuthorities(source.getAuthorities());

        return command;
    }
}
