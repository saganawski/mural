package com.chicago.mural.mural.controller;

import com.chicago.mural.mural.Mural;
import com.chicago.mural.mural.dto.MuralDTO;
import com.chicago.mural.mural.service.MuralService;
import com.chicago.mural.securtiy.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/mural")
public class MuralController {

    @Autowired
    private MuralService muralService;

    @GetMapping("/details/{muralRegistrationId}")
    public MuralDTO getMural(@PathVariable("muralRegistrationId") int muralRegistrationId){
        return muralService.getMural(muralRegistrationId);
    }

    @PostMapping("/{muralId}/aws-image-upload")
    public ResponseEntity<String> awsFileUpload(MultipartFile file, @PathVariable("muralId") int muralId, @AuthenticationPrincipal UserPrincipal userPrincipal){
        //TODO: sometype of file check
        if(userPrincipal == null){
            throw new RuntimeException("Must be logged in to upload an image!");
        }
        return muralService.awsImageUpload(muralId,userPrincipal,file);
    }

    @GetMapping("/{muralRegistrationId}/aws-image-download")
    public List<String> getMuralImages(@PathVariable("muralRegistrationId") int muralRegistrationId){
        return muralService.getMuralImages(muralRegistrationId);
    }

}
