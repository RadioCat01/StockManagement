package com.synapse.StockMGT.Models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "subCom")
@Builder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubCompany extends TenantAwareSupperClass{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer subCompanyId;

    private String subCompanyName;

    @OneToMany(mappedBy = "subCompany",cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Store> stores;
}
