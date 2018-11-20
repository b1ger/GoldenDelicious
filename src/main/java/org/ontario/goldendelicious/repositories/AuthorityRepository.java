package org.ontario.goldendelicious.repositories;

import org.ontario.goldendelicious.domain.Authority;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorityRepository extends CrudRepository<Authority, String> {

    List<Authority> findAllByOrderByName();
}
