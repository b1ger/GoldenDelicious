package org.ontario.goldendelicious.services;

import lombok.extern.slf4j.Slf4j;
import org.ontario.goldendelicious.commands.RequestCommand;
import org.ontario.goldendelicious.converters.RequestCommandToRequest;
import org.ontario.goldendelicious.converters.RequestToRequestCommand;
import org.ontario.goldendelicious.domain.Request;
import org.ontario.goldendelicious.domain.enums.RequestStatus;
import org.ontario.goldendelicious.exceptions.NotFoundException;
import org.ontario.goldendelicious.repositories.RequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Override
    public List<RequestCommand> fetchByStatus(RequestStatus status) {
        return requestRepository.getAllByStatusOrderByDate(status)
                .stream()
                .map(request -> requestToRequestCommand.convert(request))
                .collect(Collectors.toList());
    }

    @Override
    public Set<Request> fetchByDateAndDoctor(Long date, Long doctorId) {
        return requestRepository.findByDateAndDoctorId(date, doctorId);
    }

    @Override
    public RequestCommand getById(Long id) {
        Optional<Request> optional = requestRepository.findById(id);

        if (! optional.isPresent()) {
            throw new NotFoundException("Request not found by 'id': " + id);
        }

        return requestToRequestCommand.convert(optional.get());
    }
}
