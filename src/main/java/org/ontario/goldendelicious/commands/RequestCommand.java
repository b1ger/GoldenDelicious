package org.ontario.goldendelicious.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ontario.goldendelicious.domain.enums.RequestStatus;
import org.ontario.goldendelicious.domain.enums.ServiceType;

@Getter
@Setter
@NoArgsConstructor
public class RequestCommand {

    private String firstName;
    private String lastName;
    private String phone;
    private ServiceType serviceType;
    private Long doctorId;
    private String date;
    private String time;
    private RequestStatus status;
}
