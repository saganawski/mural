package com.chicago.mural.mural.service;

import com.chicago.mural.mural.Mural;
import com.chicago.mural.mural.dao.JpaMuralRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class MuralServiceImpl implements MuralService {
    @Autowired
    private JpaMuralRepo jpaMuralRepo;

    @Override
    public Mural getMural(int muralRegistrationId) {
        final Mural mural = jpaMuralRepo.findByMuralRegistrationId(muralRegistrationId);
        return mural;
    }
}
