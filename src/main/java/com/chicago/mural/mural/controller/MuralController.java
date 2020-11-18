package com.chicago.mural.mural.controller;

import com.chicago.mural.mural.dto.MuralDTO;
import com.chicago.mural.mural.service.MuralService;
import com.chicago.mural.securtiy.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

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

    @GetMapping("/{muralRegistrationId}/aws-url")
    public String getMuralAwsUrl(@PathVariable("muralRegistrationId") int muralRegistrationId){
        return muralService.getMuralAwsUrl(muralRegistrationId);
    }

    @GetMapping("/ward/{wardId}")
    public ResponseEntity<Map<String,Object>> findAllMuralByWardId(@PathVariable("wardId") String wardId,
                                                                   @RequestParam(defaultValue = "0") int page,
                                                                   @RequestParam(defaultValue = "15") int size
                                                                   ){
        return muralService.findAllMuralsByWardId(wardId,page,size);
    }

}
