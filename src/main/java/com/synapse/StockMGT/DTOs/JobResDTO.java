package com.synapse.StockMGT.DTOs;

import com.synapse.StockMGT.Enums.JobStatus;
import com.synapse.StockMGT.Enums.JobTypes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class JobResDTO {
    private String jobNumber;
    private LocalDate jobDate;
    private JobTypes jobType;
    private LocalDate invoiceDate;
    private String invoiceNumber;
    private JobStatus status;
    private String customerName;
    private String customerPhone;

    private List<JobItemsDTO> jobItems;
}