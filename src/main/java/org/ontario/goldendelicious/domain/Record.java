package org.ontario.goldendelicious.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long doctorId;

    private String description;

    @ManyToOne
    private PatientCard card;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "record")
    private Set<Service> services = new HashSet<>();
}
