package org.ontario.goldendelicious.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.ontario.goldendelicious.domain.enums.StaffType;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class StaffCommand {

    private Long id;
    private String firstName;
    private String lastName;
    private String birthDate;
    private StaffType type;
    private Long createdAt;
    private Long updatedAt;
    private String userName;
    private String password;
    private Byte[] image;

    public int getAge() throws ParseException {
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date b = format.parse(getBirthDate());

        return Calendar.YEAR - b.getYear();
    }
}
