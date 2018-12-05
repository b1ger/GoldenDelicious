package org.ontario.goldendelicious.repositories;

import org.ontario.goldendelicious.domain.Request;
import org.springframework.data.repository.CrudRepository;

public interface RequestRepository extends CrudRepository<Request, Long> {
}
