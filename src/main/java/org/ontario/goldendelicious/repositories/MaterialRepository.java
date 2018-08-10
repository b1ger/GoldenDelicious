package org.ontario.goldendelicious.repositories;

import org.ontario.goldendelicious.domain.Material;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialRepository extends CrudRepository<Material, Long> {
}
