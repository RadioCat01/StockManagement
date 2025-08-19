package com.synapse.StockMGT.User;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Roles")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private Roles roleName;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;
}
