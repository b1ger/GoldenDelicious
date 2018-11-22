package org.ontario.goldendelicious.repositories;

import org.ontario.goldendelicious.domain.Authority;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorityRepository extends CrudRepository<Authority, Long> {

    List<Authority> findAllByOrderByName();
    Optional<Authority> findByName(String name);
}
