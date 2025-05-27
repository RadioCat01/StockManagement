package com.synapse.StockMGT.CustomFields;

import com.synapse.StockMGT.Models.TenantAwareSupperClass;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "CustomFields_item")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomFields_item extends TenantAwareSupperClass {
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
