package org.ontario.goldendelicious.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class RecordCommand {

    private Long id;
    private Long doctorId;
    private String description;
    private PatientCardCommand card;
    private Set<ServiceCommand> services = new HashSet<>();
}
