package org.ontario.goldendelicious.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PatientCommand {

    private Long id;
    private String firstName;
    private String lastName;
    private Long birthDate;
    private Integer age;
    private Long createdAt;
    private Long updatedAt;
    private String address;
    private PatientCardCommand card;
}
