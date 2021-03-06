package org.ontario.goldendelicious.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.ontario.goldendelicious.domain.Authority;
import org.ontario.goldendelicious.domain.enums.StaffType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class StaffCommand {

    private Long id;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String birthDate;
    private StaffType type;
    private Long createdAt;
    private Long updatedAt;
    @NotBlank
    private String userName;
    @NotBlank
    @Size(min = 3, max = 20)
    private String password;
    private Byte[] image;
    private Set<Authority> authorities = new HashSet<>();
    private String about;
}
