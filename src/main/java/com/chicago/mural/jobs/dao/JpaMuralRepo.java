package com.chicago.mural.jobs.dao;

import com.chicago.mural.mural.Mural;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMuralRepo extends JpaRepository<Mural, Integer> {
}
