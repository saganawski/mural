package com.chicago.mural.muralImageLikes;

import com.chicago.mural.muralImageLikes.service.MuralImageLikesService;
import com.chicago.mural.securtiy.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mural-image/{imageId}")
public class MuralImageLikesController {

    @Autowired
    private MuralImageLikesService muralImageLikesService;

    @PostMapping("/add-like")
    public ResponseEntity<String> addLike(@PathVariable("imageId") int imageId, @AuthenticationPrincipal UserPrincipal userPrincipal){
        if(userPrincipal == null){
            throw new RuntimeException("Must be logged in to upvote an image!");
        }

        return  muralImageLikesService.addLike(userPrincipal,imageId);
    }
}
