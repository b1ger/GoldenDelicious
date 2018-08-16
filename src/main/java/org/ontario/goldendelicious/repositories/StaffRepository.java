package org.ontario.goldendelicious.repositories;

import org.ontario.goldendelicious.domain.Staff;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffRepository extends CrudRepository<Staff, Long> {

    List<Staff> findAllByOrderById();
}
