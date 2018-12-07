package org.ontario.goldendelicious.repositories;

import org.ontario.goldendelicious.domain.Request;
import org.ontario.goldendelicious.domain.enums.RequestStatus;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface RequestRepository extends CrudRepository<Request, Long> {

    Set<Request> getAllByStatusOrderByDate(RequestStatus status);
}
