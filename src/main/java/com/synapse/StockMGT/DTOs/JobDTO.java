package com.synapse.StockMGT.DTOs;

import com.synapse.StockMGT.CustomFields.CustomFields_jobs;
import com.synapse.StockMGT.Enums.JobTypes;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class JobDTO {
    private String jobNumber;
    private JobTypes jobType;
    private String invoiceNumber;
    private LocalDate invoiceDate;
    private String customerName;
    private String customerPhone;

    private List<JobItemsDTO> jobItems;
    private List<CustomFields_jobs> customFields;

    private List<String> claimSerials;
}
