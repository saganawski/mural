package com.chicago.mural.muralImageLikes.service;

import com.chicago.mural.securtiy.UserPrincipal;
import org.springframework.http.ResponseEntity;

public interface MuralImageLikesService {
    ResponseEntity<String> addLike(UserPrincipal userPrincipal, int imageId);
}
