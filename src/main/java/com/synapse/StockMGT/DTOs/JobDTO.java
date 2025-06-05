package com.synapse.StockMGT.DTOs;

import com.synapse.StockMGT.CustomFields.CustomFields_jobs;
import com.synapse.StockMGT.Enums.JobTypes;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class JobDTO {
    private JobTypes jobType;
    private String invoiceNumber;
    private LocalDate invoiceDate;
    private String customerName;
    private String customerPhone;

    private List<JobItemsDTO> jobItems;
    private List<CustomFields_jobs> customFields;
}
