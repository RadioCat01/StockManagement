package com.synapse.StockMGT.Controllers;

import com.synapse.StockMGT.DTOs.JobDTO;
import com.synapse.StockMGT.Services.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/job")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('COMPANY_ADMIN', 'STOCK_CLERK')")
public class JobController {

    private final JobService jobService;
    Logger jobLogger = Logger.getLogger("AUDIT");

    @GetMapping("/get")
    public ResponseEntity<?> getJob() {
        jobLogger.info("getJob called");
        return ResponseEntity.ok(jobService.getJobs());
    }

    @GetMapping("/replacementNotes")
    public ResponseEntity<?> replacementNotes() {
        jobLogger.info("replacementNotes called");
        return ResponseEntity.ok(jobService.getRepNotes());
    }

    @GetMapping("/defectItems")
    public ResponseEntity<?> getDefectItems() {
        jobLogger.info("getDefectItems called");
        return ResponseEntity.ok(jobService.getDefects());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addJob(@RequestBody JobDTO job) throws Exception {
        jobLogger.info("Adding job: " + job);
        return ResponseEntity.ok(jobService.addJob(job));
    }

    @PostMapping("/warranty")
    public ResponseEntity<?> addWarranty(@RequestBody JobDTO job) {
        jobLogger.info("Adding warranty: " + job);
        return ResponseEntity.ok(jobService.claimWarranty(job));
    }
}
