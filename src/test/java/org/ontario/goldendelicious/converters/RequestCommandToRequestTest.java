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
public class RequestCommandToRequestTest {

    private RequestCommandToRequest converter;
    private static final Long ID = 1L;
    private static final String FIRST_NAME = "test";
    private static final String LAST_NAME = "test";
    private static final String PHONE = "093-458-89-69";
    private static final String EMAIL = "exqmple@test.com";
    private static final ServiceType SERVICE_TYPE = ServiceType.SERVICE_1;
    private static final Long DOCTOR_ID = 2L;
    private static final String DATE = "4 DEC 2018";
    private static final String TIME = "9:00";
    private static final RequestStatus REQUEST_STATUS = RequestStatus.NEW;

    @Before
    public void setUp() {
        converter = new RequestCommandToRequest();
    }

    @Test
    public void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    public void shouldConvertToRequest() {
        // given
        RequestCommand command = new RequestCommand();
        command.setId(ID);
        command.setFirstName(FIRST_NAME);
        command.setLastName(LAST_NAME);
        command.setPhone(PHONE);
        command.setEmail(EMAIL);
        command.setServiceType(SERVICE_TYPE);
        command.setDoctorId(DOCTOR_ID);
        command.setDate(DATE);
        command.setTime(TIME);
        command.setStatus(REQUEST_STATUS);

        // when
        Request request = converter.convert(command);

        log.debug(request.toString());

        // then
        assertEquals(ID, request.getId());
        assertEquals(EMAIL, request.getEmail());
    }

    @Test
    public void shouldNotReturnNull() {
        // given
        RequestCommand command = new RequestCommand();
        command.setId(ID);
        command.setFirstName(FIRST_NAME);
        command.setLastName(LAST_NAME);
        command.setPhone(PHONE);
        command.setEmail(EMAIL);
        command.setServiceType(SERVICE_TYPE);
        command.setDoctorId(DOCTOR_ID);
        command.setDate(DATE);
        command.setTime(TIME);
        command.setStatus(REQUEST_STATUS);

        // then
        assertNotNull(converter.convert(command));
    }
}