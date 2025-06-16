package com.synapse.StockMGT.Models.CustomFields;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "ComCustom")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer comCustomerId;

    private String fieldName;
    private String fieldType;
    private String fieldValue;

    @ManyToOne
    @JoinColumn(name = "templateId")
    private Templates template;
}
