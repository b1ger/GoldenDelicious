package org.ontario.goldendelicious.converters;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.ontario.goldendelicious.commands.RequestCommand;
import org.ontario.goldendelicious.domain.Request;
import org.ontario.goldendelicious.domain.enums.RequestStatus;
import org.ontario.goldendelicious.domain.enums.ServiceType;

import static org.junit.Assert.*;

@Slf4j
public class RequestToRequestCommandTest {

    private RequestToRequestCommand converter;
    private static final Long ID = 1L;
    private static final String FIRST_NAME = "test";
    private static final String LAST_NAME = "test";
    private static final String PHONE = "093-458-89-69";
    private static final String EMAIL = "exqmple@test.com";
    private static final ServiceType SERVICE_TYPE = ServiceType.SERVICE_1;
    private static final Long DOCTOR_ID = 2L;
    private static final Long DATE = 1543874400L;
    private static final String TIME = "9:00";
    private static final RequestStatus REQUEST_STATUS = RequestStatus.NEW;


    @Before
    public void setUp() {
        converter = new RequestToRequestCommand();
    }

    @Test
    public void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    public void shouldConvertToRequestCommand() {
        // given
        Request request = new Request();
        request.setId(ID);
        request.setFirstName(FIRST_NAME);
        request.setLastName(LAST_NAME);
        request.setPhone(PHONE);
        request.setEmail(EMAIL);
        request.setServiceType(SERVICE_TYPE);
        request.setDoctorId(DOCTOR_ID);
        request.setDate(DATE);
        request.setTime(TIME);
        request.setStatus(REQUEST_STATUS);

        // when
        RequestCommand command = converter.convert(request);

        log.debug(command.toString());

        // then
        assertEquals(ID, command.getId());
        assertEquals(EMAIL, command.getEmail());
        assertEquals("4 Dec 2018", command.getDate());
    }
}