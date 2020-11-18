package com.chicago.mural.mural.service;

import com.chicago.mural.mural.dto.MuralDTO;
import com.chicago.mural.securtiy.UserPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface MuralService {
    MuralDTO getMural(int muralRegistrationId);

    ResponseEntity<String> awsImageUpload(int muralId, UserPrincipal userPrincipal, MultipartFile file);

    List<String> getMuralImages(int muralRegistrationId);

    String getMuralAwsUrl(int muralRegistrationId);

    ResponseEntity<Map<String, Object>> findAllMuralsByWardId(String wardId, int page, int size);
}
