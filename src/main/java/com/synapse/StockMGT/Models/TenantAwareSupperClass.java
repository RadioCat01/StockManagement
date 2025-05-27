package com.synapse.StockMGT.Models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Data
@EqualsAndHashCode(callSuper = false)
public abstract class TenantAwareSupperClass {
    @Column(name = "companyId")
    private String companyId;
}
