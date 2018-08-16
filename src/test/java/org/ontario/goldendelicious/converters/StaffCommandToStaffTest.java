package org.ontario.goldendelicious.converters;

import org.junit.Before;
import org.junit.Test;
import org.ontario.goldendelicious.commands.StaffCommand;
import org.ontario.goldendelicious.domain.Staff;
import org.ontario.goldendelicious.domain.enums.StaffType;

import static org.junit.Assert.*;

public class StaffCommandToStaffTest {

    private StaffCommandToStaff converter;
    private static final Long ID_VALUE = 1L;
    private static final String FIRS_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final StaffType TYPE = StaffType.DOCTOR;
    private static final Long CREATED_AT = 55L;
    private static final Long UPDATED_AT = 56L;
    private static final String USERNAME = "username";
    private static final String PASS_HASH = "some_hash";
    private static final Byte[] IMAGE = {2, 5, 14};

    @Before
    public void setUp() throws Exception {
        converter = new StaffCommandToStaff();
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(converter.convert(new StaffCommand()));
    }

    @Test
    public void convert() {
        //given
        StaffCommand command = new StaffCommand();
        command.setId(ID_VALUE);
        command.setFirstName(FIRS_NAME);
        command.setLastName(LAST_NAME);
        command.setType(TYPE);
        command.setCreatedAt(CREATED_AT);
        command.setUpdatedAt(UPDATED_AT);
        command.setUsername(USERNAME);
        command.setPasswordHash(PASS_HASH);
        command.setImage(IMAGE);

        //when
        Staff staff = converter.convert(command);

        //then
        assertEquals(ID_VALUE, staff.getId());
        assertEquals(USERNAME, staff.getUsername());
        assertEquals(IMAGE, staff.getImage());
    }
}