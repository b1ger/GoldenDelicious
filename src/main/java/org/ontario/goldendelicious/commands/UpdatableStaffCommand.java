package org.ontario.goldendelicious.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.ontario.goldendelicious.domain.Authority;
import org.ontario.goldendelicious.domain.enums.StaffType;

import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UpdatableStaffCommand {

    private Long id;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String birthDate;
    private StaffType type;
    @NotBlank
    private String userName;
    private Byte[] image;
    private String about;
    private String password;
    private Set<Authority> authorities = new HashSet<>();
}
