package com.synapse.StockMGT.CustomFields;

import com.synapse.StockMGT.Models.TenantAwareSupperClass;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "CustomFields_customer")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomFields_customer extends TenantAwareSupperClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String fieldName;
    private String fieldValue;

    @Enumerated(EnumType.STRING)
    private CustomFieldType fieldType;

    private boolean enabled;
    private boolean required;
}
