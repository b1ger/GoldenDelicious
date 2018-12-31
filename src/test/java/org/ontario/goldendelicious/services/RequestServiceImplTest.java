package org.ontario.goldendelicious.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.ontario.goldendelicious.commands.RequestCommand;
import org.ontario.goldendelicious.converters.RequestCommandToRequest;
import org.ontario.goldendelicious.converters.RequestToRequestCommand;
import org.ontario.goldendelicious.domain.Request;
import org.ontario.goldendelicious.domain.enums.RequestStatus;
import org.ontario.goldendelicious.repositories.RequestRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

public class RequestServiceImplTest {

    private RequestServiceImpl service;

    @Mock
    private RequestRepository requestRepository;

    @Mock
    private RequestCommandToRequest requestCommandToRequest;

    @Mock
    private RequestToRequestCommand requestToRequestCommand;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        service = new RequestServiceImpl(requestRepository);
    }

    @Test
    public void shouldSaveNewRequest() {

        // given
        RequestCommand command = new RequestCommand();
        command.setId(1L);
        command.setFirstName("Test");
        command.setStatus(RequestStatus.NEW);
        command.setDate("2018-12-22");

        Request saved = service.save(command);

        // then
        verify(requestRepository, times(1)).save(any());
    }

    @Test
    public void shouldReturnListWithNewRequests() {
        // given
        Request request1 = new Request();
        request1.setId(1L);
        request1.setDate(1546984800000L);
        request1.setStatus(RequestStatus.NEW);
        Request request2 = new Request();
        request2.setId(2L);
        request2.setDate(1546984800000L);
        request2.setStatus(RequestStatus.NEW);
        Set<Request> requestSet = new HashSet<>();
        requestSet.add(request1);
        requestSet.add(request2);

        // when
        when(requestRepository.getAllByStatusOrderByDate(RequestStatus.NEW)).thenReturn(requestSet);
        List<RequestCommand> commands = service.fetchByStatus(RequestStatus.NEW);

        // then
        assertEquals(requestSet.size(), commands.size());
    }

    @Test
    public void shouldReturnAvailableTime() {
        // given
        Request request = new Request();
        request.setId(1L);
        request.setDate(1544624325L);
        request.setTime("9:00");
        Request request2 = new Request();
        request2.setId(2L);
        request2.setTime("11:00");
        request2.setDate(1544624325L);;
        Set<Request> requestSet = new HashSet<>();
        requestSet.add(request);
        requestSet.add(request2);

        // when
        when(requestRepository.findByDateAndDoctorId(anyLong(), anyLong())).thenReturn(requestSet);
        Set<Request> requestSet1 = service.fetchByDateAndDoctor(anyLong(), anyLong());

        // then
        assertThat(requestSet1, hasItems(request, request2));
    }

    @Test
    public void shouldReturnRequestCommandById() {

        // given
        Request request = new Request();
        request.setId(1L);
        request.setDate(1546984800000L);
        RequestCommand requestCommand = new RequestCommand();
        requestCommand.setId(1L);
        request.setStatus(RequestStatus.NEW);
        Optional<Request> optionalRequest = Optional.of(request);

        // when
        when(requestRepository.findById(anyLong())).thenReturn(optionalRequest);
        when(requestToRequestCommand.convert(any())).thenReturn(requestCommand);

        RequestCommand command = service.getById(1L);

        // then
        assertNotNull(command);
        assertEquals(1, Long.parseLong(String.valueOf(command.getId())));
    }
}