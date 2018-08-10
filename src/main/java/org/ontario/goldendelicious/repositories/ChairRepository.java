package org.ontario.goldendelicious.repositories;

import org.ontario.goldendelicious.domain.Chair;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChairRepository extends CrudRepository<Chair, Long> {
}
