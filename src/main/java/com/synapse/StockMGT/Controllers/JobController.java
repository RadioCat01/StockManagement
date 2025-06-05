package com.synapse.StockMGT.Controllers;

import com.synapse.StockMGT.DTOs.JobDTO;
import com.synapse.StockMGT.Services.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/job")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;
    Logger jobLogger = Logger.getLogger("AUDIT");

    @GetMapping("/get")
    public ResponseEntity<?> getJob() {
        jobLogger.info("getJob called");
        return ResponseEntity.ok(jobService.getJobs());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addJob(@RequestBody JobDTO job) throws Exception {
        jobLogger.info("Adding job: " + job);
        Map<String, String> response = new HashMap<>();
        response.put("job", jobService.addJob(job));
        return ResponseEntity.ok(response);
    }

}
