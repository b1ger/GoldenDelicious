package org.ontario.goldendelicious.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.ontario.goldendelicious.domain.enums.RequestStatus;
import org.ontario.goldendelicious.domain.enums.ServiceType;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class RequestCommand {

    private Long id;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    @Pattern(regexp = "^([\\d]{3}-[\\d]{3}-[\\d]{2}-[\\d]{2})$")
    private String phone;
    @NotBlank
    @Email
    private String email;
    @NotNull
    private ServiceType serviceType;
    @NotNull
    private Long doctorId;
    @NotBlank
    private String date;
    @NotBlank
    private String time;
    private RequestStatus status;
}
