package org.ontario.goldendelicious.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.ontario.goldendelicious.domain.enums.StaffType;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class StaffCommand {

    private Long id;
    private String firstName;
    private String lastName;
    private StaffType type;
    private Long createdAt;
    private Long updatedAt;
    private String username;
    private String password;
    private String passwordHash;
    private Byte[] image;
}
