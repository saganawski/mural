package com.chicago.mural.mural.dao;

import com.chicago.mural.mural.Mural;
import com.chicago.mural.mural.MuralImageUpload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JpaMuralImageUploadRepo extends JpaRepository<MuralImageUpload, Integer> {

}
