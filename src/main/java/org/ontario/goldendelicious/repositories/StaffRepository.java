package org.ontario.goldendelicious.repositories;

import org.ontario.goldendelicious.domain.Staff;
import org.ontario.goldendelicious.domain.enums.StaffType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StaffRepository extends CrudRepository<Staff, Long> {

    List<Staff> findAllByOrderById();
    List<Staff> findAllByOrderByType();
    List<Staff> findByTypeOrderById(StaffType type);
    Optional<Staff> findByUserName(String userName);
}
