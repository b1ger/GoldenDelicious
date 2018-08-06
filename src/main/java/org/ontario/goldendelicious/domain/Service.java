package org.ontario.goldendelicious.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ontario.goldendelicious.domain.enums.ServiceType;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long doctorId;

    @Enumerated(value = EnumType.STRING)
    private ServiceType type;

    private Long roomId;

    private Long chairId;

    private Long date;

    @ManyToMany
    @JoinTable(name = "service_material",
            joinColumns = @JoinColumn(name = "service_id"),
            inverseJoinColumns = @JoinColumn(name = "material_id"))
    private Set<Material> materials;

    @ManyToOne
    private Record record;
}
