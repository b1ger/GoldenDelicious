package org.ontario.goldendelicious.converters;

import lombok.Synchronized;
import org.ontario.goldendelicious.commands.StaffCommand;
import org.ontario.goldendelicious.domain.Staff;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = dateFormat.parse(source.getBirthDate());
            System.out.println(date);
            staff.setBirthDate(date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        staff.setUserName(source.getUserName());
        staff.setType(source.getType());
        staff.setPassword(source.getPassword());
        staff.setCreatedAt(source.getCreatedAt());
        if (source.getUpdatedAt() != null) {
            staff.setUpdatedAt(source.getUpdatedAt());
        }
        if (source.getImage() != null &&
                source.getImage().length > 0) {
            staff.setImage(source.getImage());
        }

        staff.setAbout(source.getAbout());
        staff.setAuthorities(source.getAuthorities());

        return staff;
    }
}
