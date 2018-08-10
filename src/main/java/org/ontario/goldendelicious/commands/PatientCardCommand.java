package org.ontario.goldendelicious.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PatientCardCommand {

    private Long id;
    private PatientCommand patient;
    private List<RecordCommand> records = new ArrayList<>();
}
