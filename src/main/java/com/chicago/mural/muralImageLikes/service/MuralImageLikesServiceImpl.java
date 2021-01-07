package com.chicago.mural.muralImageLikes.service;

import com.chicago.mural.mural.MuralImageUpload;
import com.chicago.mural.mural.dao.JpaMuralImageUploadRepo;
import com.chicago.mural.securtiy.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class MuralImageLikesServiceImpl implements MuralImageLikesService{
    @Autowired
    private JpaMuralImageUploadRepo jpaMuralImageUploadRepo;

    @Override
    public ResponseEntity<String> addLike(UserPrincipal userPrincipal, int imageId) {
        final MuralImageUpload image = jpaMuralImageUploadRepo.getOne(imageId);
        image.addLike(userPrincipal);

        jpaMuralImageUploadRepo.save(image);
        return new ResponseEntity<String>("Successfully Liked!", HttpStatus.OK);
    }
}
