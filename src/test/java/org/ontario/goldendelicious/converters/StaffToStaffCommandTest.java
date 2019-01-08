package org.ontario.goldendelicious.converters;

import org.junit.Before;
import org.junit.Test;
import org.ontario.goldendelicious.commands.StaffCommand;
import org.ontario.goldendelicious.domain.Staff;
import org.ontario.goldendelicious.domain.enums.StaffType;

import static org.junit.Assert.*;

public class StaffToStaffCommandTest {

    private StaffToStaffCommand converter;
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
        converter = new StaffToStaffCommand();
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        Staff staff = new Staff();
        staff.setId(ID_VALUE);
        staff.setFirstName(FIRS_NAME);
        staff.setLastName(LAST_NAME);
        staff.setType(TYPE);
        staff.setCreatedAt(CREATED_AT);
        staff.setUpdatedAt(UPDATED_AT);
        staff.setUserName(USERNAME);
        staff.setPassword(PASS_HASH);
        staff.setImage(IMAGE);
        staff.setBirthDate(646516800000L);

        assertNotNull(converter.convert(staff));
    }

    @Test
    public void convert() {
        //given
        Staff staff = new Staff();
        staff.setId(ID_VALUE);
        staff.setFirstName(FIRS_NAME);
        staff.setLastName(LAST_NAME);
        staff.setType(TYPE);
        staff.setCreatedAt(CREATED_AT);
        staff.setUpdatedAt(UPDATED_AT);
        staff.setUserName(USERNAME);
        staff.setPassword(PASS_HASH);
        staff.setImage(IMAGE);
        staff.setBirthDate(646516800000L);

        //when
        StaffCommand command = converter.convert(staff);

        //then
        assertEquals(ID_VALUE, command.getId());
        assertEquals(USERNAME, command.getUserName());
        assertArrayEquals(IMAGE, command.getImage());
        assertEquals("28 Jun 1990", command.getBirthDate());
    }
}