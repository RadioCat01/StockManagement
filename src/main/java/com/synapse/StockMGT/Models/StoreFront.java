package com.synapse.StockMGT.Models;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "StoreFront")
@Builder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StoreFront extends TenantAwareSupperClass{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer storefrontId;


}
