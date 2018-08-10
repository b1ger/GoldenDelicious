package org.ontario.goldendelicious.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ontario.goldendelicious.domain.enums.RequestStatus;
import org.ontario.goldendelicious.domain.enums.ServiceType;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String phone;

    @Enumerated(value = EnumType.STRING)
    private ServiceType serviceType;

    private Long doctorId;

    private Date date;

    private String time;

    @Enumerated(value = EnumType.STRING)
    private RequestStatus status;
}
