package com.chicago.mural.mural.dao;


import com.chicago.mural.mural.Mural;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface JpaMuralRepo extends JpaRepository<Mural, Integer> {

    @Query(value = "SELECT * FROM mural WHERE mural_registration_id = ?1",
    nativeQuery = true)
    Mural findByMuralRegistrationId(int muralRegistrationId);

    Page<Mural> findByWard(String ward, Pageable pageable);

    @Query(value = "SELECT ward FROM mural GROUP BY ward ORDER BY cast(ward AS unsigned)",
            nativeQuery = true)
    List<String> findAllWardIds();
}
