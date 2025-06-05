package com.synapse.StockMGT.DTOs;

import com.synapse.StockMGT.Enums.JobTypes;
import lombok.Builder;
import lombok.Data;

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

    private List<JobItemsDTO> jobItems;
}
