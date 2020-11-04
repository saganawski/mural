package com.chicago.mural.jobs;

import com.chicago.mural.jobs.service.JobService;
import com.chicago.mural.mural.Mural;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {
    @Autowired
    private JobService jobService;


    @GetMapping("/chicago-mural")
    public List<Mural> runChicagoMural(){
//        TODO: add auth user
        System.out.println("hits");
        return jobService.returnMurals();
    }
}
