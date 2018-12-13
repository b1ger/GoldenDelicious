package org.ontario.goldendelicious.services;

import org.ontario.goldendelicious.commands.RequestCommand;
import org.ontario.goldendelicious.domain.Request;
import org.ontario.goldendelicious.domain.enums.RequestStatus;

import java.util.Set;

public interface RequestService {

    Request save(RequestCommand request);
    Set<RequestCommand> fetchByStatus(RequestStatus status);
    Set<Request> fetchByDateAndDoctor(Long date, Long doctorId);
}
