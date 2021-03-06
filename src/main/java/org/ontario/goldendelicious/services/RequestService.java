package org.ontario.goldendelicious.services;

import org.ontario.goldendelicious.commands.RequestCommand;
import org.ontario.goldendelicious.domain.Request;
import org.ontario.goldendelicious.domain.enums.RequestStatus;

import java.util.List;
import java.util.Set;

public interface RequestService {

    Request save(RequestCommand request);
    List<RequestCommand> fetchByStatus(RequestStatus status);
    Set<Request> fetchByDateAndDoctor(Long date, Long doctorId);
    RequestCommand getById(Long id);
    RequestCommand updateRequest(RequestCommand updatable);
}
