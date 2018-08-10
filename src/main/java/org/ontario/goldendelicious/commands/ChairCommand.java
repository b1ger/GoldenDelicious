package org.ontario.goldendelicious.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChairCommand {

    private Long id;
    private Long roomId;
    private String description;
}
