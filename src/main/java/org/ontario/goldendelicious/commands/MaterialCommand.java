package org.ontario.goldendelicious.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ontario.goldendelicious.domain.enums.MaterialType;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class MaterialCommand {

    private Long id;
    private MaterialType type;
    private String description;
    private Double price;
    private Integer quantity;
    private Set<ServiceCommand> services = new HashSet<>();
}
