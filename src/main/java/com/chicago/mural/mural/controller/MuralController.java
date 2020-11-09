package com.chicago.mural.mural.controller;

import com.chicago.mural.mural.Mural;
import com.chicago.mural.mural.service.MuralService;
import com.chicago.mural.securtiy.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mural")
public class MuralController {

    @Autowired
    private MuralService muralService;

    @GetMapping("/details/{muralRegistrationId}")
    public Mural getMural(@PathVariable("muralRegistrationId") int muralRegistrationId){
        return muralService.getMural(muralRegistrationId);
    }

}
