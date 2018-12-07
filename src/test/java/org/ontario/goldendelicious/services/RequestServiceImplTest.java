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
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
        service = new RequestServiceImpl(requestRepository, requestCommandToRequest, requestToRequestCommand);
    }

    @Test
    public void shouldSaveNewRequest() {

        // given
        RequestCommand command = new RequestCommand();
        command.setId(1L);
        command.setFirstName("Test");
        command.setStatus(RequestStatus.NEW);
        Request request = new Request();
        request.setId(1L);
        request.setFirstName("Test");
        request.setStatus(RequestStatus.NEW);

        // when
        when(requestRepository.save(any())).thenReturn(request);
        when(requestCommandToRequest.convert(any())).thenReturn(request);

        service.save(command);

        // then
        verify(requestRepository, times(1)).save(any());
    }

    @Test
    public void shouldReturnListWithNewRequests() {
        // given
        Request request1 = new Request();
        request1.setId(1L);
        request1.setStatus(RequestStatus.NEW);
        Request request2 = new Request();
        request2.setId(2L);
        request2.setStatus(RequestStatus.NEW);
        Set<Request> requestSet = new HashSet<>();
        requestSet.add(request1);
        requestSet.add(request2);

        // when
        when(requestRepository.getAllByStatusOrderByDate(RequestStatus.NEW)).thenReturn(requestSet);
        Set<RequestCommand> commands = service.fetchByStatus(RequestStatus.NEW);

        // then
        verify(requestToRequestCommand, times(2)).convert(any());
    }
}