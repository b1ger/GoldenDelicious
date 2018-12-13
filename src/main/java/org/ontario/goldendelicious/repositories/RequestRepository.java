package org.ontario.goldendelicious.repositories;

import org.ontario.goldendelicious.domain.Request;
import org.ontario.goldendelicious.domain.enums.RequestStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RequestRepository extends CrudRepository<Request, Long> {

    Set<Request> getAllByStatusOrderByDate(RequestStatus status);
    @Query("select r from Request r where r.date = (:date) and r.doctorId = (:doctorId) and r.status <> 'DECLINED' order by id")
    Set<Request> findByDateAndDoctorId(@Param("date") Long date, @Param("doctorId") Long doctorId);
}
