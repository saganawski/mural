package com.chicago.mural.jobs.service;

import com.chicago.mural.mural.dao.JpaMuralRepo;
import com.chicago.mural.mural.Mural;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class JobServiceImpl implements JobService {
    @Autowired
    private JpaMuralRepo jpaMuralRepo;

    @Override
    public List<Mural> returnMurals() {
        final String url = "https://data.cityofchicago.org/resource/we8h-apcf.json";

        final OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            final ResponseBody responseBody = client.newCall(request).execute().body();

            final ObjectMapper objectMapper = new ObjectMapper();
            final List<Mural> murals = objectMapper.readValue(responseBody.string(), new TypeReference<List<Mural>>(){});

            final List<Mural> existingMurals = jpaMuralRepo.findAll();

            existingMurals.forEach(em ->{
                final boolean removed = murals.removeIf(m -> {
                   return m.getMural_registration_id().equals(em.getMural_registration_id());
                });
            });

            jpaMuralRepo.saveAll(murals);

            return murals;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to parse client request");
        }
    }
}
