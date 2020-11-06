package com.chicago.mural.mural.dao;

import com.chicago.mural.mural.Mural;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JpaMuralRepo extends JpaRepository<Mural, Integer> {

    @Query(value = "SELECT * FROM mural WHERE mural_registration_id = ?1",
    nativeQuery = true)
    Mural findByMuralRegistrationId(int muralRegistrationId);
}
