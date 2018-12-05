package org.ontario.goldendelicious.services;

import lombok.extern.slf4j.Slf4j;
import org.ontario.goldendelicious.commands.RequestCommand;
import org.ontario.goldendelicious.converters.RequestCommandToRequest;
import org.ontario.goldendelicious.converters.RequestToRequestCommand;
import org.ontario.goldendelicious.domain.Request;
import org.ontario.goldendelicious.repositories.RequestRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RequestServiceImpl implements RequestService {

    private RequestRepository requestRepository;
    private RequestCommandToRequest requestCommandToRequest;
    private RequestToRequestCommand requestToRequestCommand;

    public RequestServiceImpl(
            RequestRepository requestRepository,
            RequestCommandToRequest requestCommandToRequest,
            RequestToRequestCommand requestToRequestCommand
    ) {
        this.requestRepository = requestRepository;
        this.requestCommandToRequest = requestCommandToRequest;
        this.requestToRequestCommand = requestToRequestCommand;
    }

    @Override
    public Request save(RequestCommand requestCommand) {
        Request detached = requestCommandToRequest.convert(requestCommand);
        assert detached != null;
        return requestRepository.save(detached);
    }
}
