package com.chicago.mural.mural.controller;

import com.chicago.mural.mural.Mural;
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
    public Mural getMural(@PathVariable("muralRegistrationId") int muralRegistrationId){
        return muralService.getMural(muralRegistrationId);
    }

    @PostMapping("/{muralId}/aws-image-upload")
    public ResponseEntity<String> awsFileUpload(MultipartFile file, @PathVariable("muralId") int muralId, @AuthenticationPrincipal UserPrincipal userPrincipal){
        //TODO: sometype of file check
        return muralService.awsImageUpload(muralId,userPrincipal,file);
    }

    @GetMapping("/{muralId}/aws-image-download")
    public List<String> getMuralImages(@PathVariable("muralId") int muralId){
        return muralService.getMuralImages(muralId);
    }

}
