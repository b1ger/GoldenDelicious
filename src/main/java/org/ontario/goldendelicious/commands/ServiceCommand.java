package org.ontario.goldendelicious.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ontario.goldendelicious.domain.enums.ServiceType;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
public class ServiceCommand {

    private Long id;
    private Long doctorId;
    private Long patientId;
    private ServiceType type;
    private Long roomId;
    private Long chairId;
    private Long date;
    private Set<MaterialCommand> materials = new HashSet<>();
    private RecordCommand record;
}
